package com.ciosmak.automotivepartner.shared.security;

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
        return http.
                cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/**",//TODO (release) Usunąć podczas release
                        "/api/tokens/verify-email/**",
                        "/api/users/register/**",
                        "/api/users/logout",
                        "/api/users/login/**",
                        "/api/users/forgot-password/**",
                        "/api/users/change-password/**",
                        "/api/users/login-form",//TODO (front end communication) Umożliwia przekierowanie wszystkich do tego endpoint
                        "/swagger-ui/**",//TODO (release) Usunąć podczas release
                        "/v3/api-docs/**")//TODO (release) Usunąć podczas release
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
                        "/api/shifts/cancel",
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
                        "/api/users/make-driver/{id}",
                        "/api/users/find",
                        "/api/users/find/unblocked",
                        "/api/users/find/blocked",
                        "/api/users/find/admins",
                        "/api/users/find/super-admins",
                        "/api/users/find/drivers",
                        "/api/emails/**",
                        "/api/shifts/generate",
                        "/api/shifts/find",
                        "/api/shifts/info/{id}",
                        "/api/shifts/update-fuel",
                        "/api/shifts/find/car-unavailable",
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
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/users/block/{id}",
                        "/api/users/unblock/{id}",
                        "/api/users/make-admin/{id}",
                        "/api/users/make-super-admin/{id}",
                        "/api/users/make-driver/{id}",
                        "/api/users/find",
                        "/api/users/find/unblocked",
                        "/api/users/find/blocked",
                        "/api/users/find/admins",
                        "/api/users/find/super-admins",
                        "/api/users/find/drivers",
                        "/api/users/delete/{id}",
                        "/api/emails/**",
                        "/api/shifts/generate",
                        "/api/shifts/find",
                        "/api/shifts/info/{id}",
                        "/api/shifts/update-fuel",
                        "/api/cars/**",
                        "/api/accident/find",
                        "/api/accident/change-guilt/{id}",
                        "/api/accident/complete-unreported",
                        "/api/accidents/find/{id}",
                        "/api/availability/quantity",
                        "/api/settlements/complete",
                        "/api/settlements/update",
                        "/api/settlements/find",
                        "/api/statistics/find/{year}")
                .hasAnyAuthority("SUPER_ADMIN")
                .and()
                .formLogin()
                .loginPage("/api/users/login-form")//TODO (front end communication) Określa stronę logowania, przechodzi tam gdy chcemy wejść na adres do którego nie mamy uprawnień lub nie istnieje
                .defaultSuccessUrl("/")//TODO (front end communication) Określa stronę na którą przechodzimy po udanym logowaniu
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/users/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/api/users/login-form")//TODO (front end communication) określa stronę na którą przechodzimy po wylogowaniu
                .and()
                .build();
    }
}
