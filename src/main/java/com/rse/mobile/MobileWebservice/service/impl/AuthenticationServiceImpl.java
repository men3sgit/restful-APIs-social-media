package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.dto.UserDTOMapper;
import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.exception.auth.ApiAuthenticationRequestException;
import com.rse.mobile.MobileWebservice.model.user.Role;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;
import com.rse.mobile.MobileWebservice.service.EmailValidator;
import com.rse.mobile.MobileWebservice.controller.request.LoginRequest;
import com.rse.mobile.MobileWebservice.controller.request.RegistrationRequest;
import com.rse.mobile.MobileWebservice.service.template.AuthenticationService;
import com.rse.mobile.MobileWebservice.service.template.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;

    @Override
    public UserDTO registerNewUser(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            LOGGER.error("Invalid email provided during registration: {}", request.getEmail());
            throw new ApiRequestException("Email invalid");
        }

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getDob(),
                Role.USER
        );

        LOGGER.info("Registering a new user with email: {}", user.getEmail());

        return userService.registerNewUser(user);
    }

    @Override
    public void authenticated(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            LOGGER.info("Authenticating user with email: {}", request.getEmail());
            authenticationManager.authenticate(authentication);
            LOGGER.info("User authenticated successfully: {}", request.getEmail());
        } catch (RuntimeException e) {
            LOGGER.error("Authentication failed for user with email: {}", request.getEmail(), e);
            throw new ApiAuthenticationRequestException(e.getMessage());
        }
    }

    @Override
    public Boolean verifyToken(String token) {
        LOGGER.info("Verifying user token: {}", token);
        Boolean isSuccess = userService.verifyConfirmationToken(token);
        LOGGER.info("Token verification result: {}", isSuccess);
        return isSuccess;
    }
}
