package com.kitttus.supplychain.config;

import com.kitttus.supplychain.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/api/platform/dashboard").hasAnyRole("ORGANIZER_ADMIN", "SUPPLIER_MANAGER", "EXHIBITOR_OWNER")
                        .requestMatchers("/api/platform/orders/*/approve").hasRole("APPROVER")
                        .requestMatchers("/api/platform/orders/*/contract", "/api/platform/orders/*/settlement").hasAnyRole("ORGANIZER_ADMIN", "SUPPLIER_MANAGER")
                        .requestMatchers("/api/platform/orders", "/api/platform/orders/*/acceptance", "/api/platform/orders/*/risk").hasAnyRole("ORGANIZER_ADMIN", "EXHIBITOR_OWNER", "SUPPLIER_MANAGER")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
