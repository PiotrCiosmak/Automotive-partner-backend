package com.ciosmak.automotivepartner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserRegistrationSecurityConfig
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        //TODO zmienić bardzo dużo
        return http.
                cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("swagger-ui/html", "/api/users/**", "/api/cars/**", "/swagger-ui/**", "/v3/api-docs/**", "/api/emails/add")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/cars/find").hasAnyAuthority("admin").and().formLogin().loginPage("/api/cars/find").defaultSuccessUrl("/api/users/login").permitAll().and().build();
    }
}
