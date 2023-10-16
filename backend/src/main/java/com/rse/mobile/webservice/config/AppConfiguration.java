package com.rse.mobile.webservice.config;

import com.rse.mobile.webservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {
    public static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = (username) -> userRepository
                .findByEmail(username)
                .orElseThrow(() -> {
                    LOGGER.error("User not found for username: {}", username);
                    return new UsernameNotFoundException("User not found");
                });

        LOGGER.info("Created bean for UserDetailsService: {}", userDetailsService.toString());
        return userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        LOGGER.info("Creating BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        LOGGER.info("Creating AuthenticationProvider bean");
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        LOGGER.info("Creating AuthenticationManager bean");
        return authConfig.getAuthenticationManager();
    }
}
