package com.rse.mobile.MobileWebservice.config.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers("/public/**").permitAll() // Allow public endpoints
                .anyRequest().authenticated().and(); // Require authentication for all other requests;
        // Set custom authentication entry point
        LOGGER.info("Create SecurityFilterChain bean");
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        LOGGER.info("Create WebSecurityCustomizer bean");
        return (web) -> web.ignoring().requestMatchers(
                "/api/v*/auth/login",
                "/api/v*/auth/register",
                "/api/v*/auth/verify",
                "/api/v*/unsecured/**",
                "/api/v*/users/**"
        );
    }


}
