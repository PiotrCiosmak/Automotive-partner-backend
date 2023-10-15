package com.ciosmak.automotivepartner.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        return http.
                cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/**",//TODO dopóki nie zrobie endpontów z formularzami
                        "/api/tokens/verify-email/**",
                        "/api/users/register/**",
                        "/api/users/login/**",
                        "/api/users/forgot-password/**",
                        "/api/users/restart-password/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**")//TODO usunać swagger i v3 podczas releasu apki
                .anonymous()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/availability/type/{user_id}",
                        "/api/settlements/info/{user_id}",
                        "/api/statistics/find/overall/{user_id}",
                        "/api/statistics/find/detail/{user_id}")
                .authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/shifts/generate",
                        "/api/shifts/start",
                        "/api/shifts/check/{id}",
                        "/api/shifts/end",
                        "/api/accidents/report",
                        "/api/availability/submit",
                        "/api/availability/check/{user_id}",
                        "/api/settlements/report-bug/{id}",
                        "/api/settlements/check{id}")
                .hasAnyAuthority("DRIVER")
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/users/block/{id}",
                        "/api/users/unblock/{id}",
                        "/api/users/make-admin/{id}",
                        "/api/users/make-driver/{id}",
                        "/api/users/find",
                        "/api/users/find/unblocked",
                        "/api/users/find/blocked",
                        "/api/users/find/admins",
                        "/api/users/find/drivers",
                        "/api/users/delete/{id}",
                        "/api/emails/**",
                        "/api/shifts/find",
                        "/api/shifts/info/{id}",
                        "/api/shifts/update-fuel",
                        "/api/cars/**",
                        "/api/accident/find",
                        "/api/accidents/find/{id}",
                        "/api/availability/quantity",
                        "/api/settlements/complete",
                        "/api/settlements/update",
                        "/api/settlements/find",
                        "/api/statistics/find/{year}")
                .hasAnyAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/api/users/login")//TODO OKREŚLA STRONE LOGOWANIA
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and()
                .build();
    }
}
