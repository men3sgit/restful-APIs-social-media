package com.rse.mobile.webservice.security;

import com.rse.mobile.webservice.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(
                        (authorized) ->
                                authorized.anyRequest().authenticated()
                )
                .sessionManagement(
                        (sessionManager) ->
                                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        LOGGER.info("Create SecurityFilterChain bean");
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        LOGGER.info("Create WebSecurityCustomizer bean");
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/api/v*/auth/login",
                        "/api/v*/auth/register",
                        "/api/v*/auth/verify",
                        "/api/v*/auth/forgot-password",
                        "/api/v*/unsecured/**",
                        "/api/v*/users/{userId}/followers",
                        "/api/v*/posts/{userId}"
                );
    }


}
