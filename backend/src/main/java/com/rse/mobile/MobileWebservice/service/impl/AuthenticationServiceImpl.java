package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.dto.mapper.UserDTOMapper;
import com.rse.mobile.MobileWebservice.exception.request.ApiAuthenticationRequestException;
import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.entities.tokens.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.entities.tokens.PasswordResetToken;
import com.rse.mobile.MobileWebservice.model.requests.LoginRequest;
import com.rse.mobile.MobileWebservice.model.requests.PasswordResetRequest;
import com.rse.mobile.MobileWebservice.model.requests.RegistrationRequest;
import com.rse.mobile.MobileWebservice.model.entities.Role;
import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.dto.UserDTO;
import com.rse.mobile.MobileWebservice.repository.UserRepository;
import com.rse.mobile.MobileWebservice.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationService confirmationService;
    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;
    private final PasswordResetService passwordResetService;

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

        validateUserEmail(user.getEmail());
        saveUserToDatabaseWithPasswordDecoder(user);

        ConfirmationToken confirmationToken = confirmationService.generateConfirmationToken(user);
        confirmationService.sendConfirmationEmail(user.getFullName(), user.getEmail(), confirmationToken.getToken());
        LOGGER.info("Registration successful for user: {}", user.getEmail());

        return userDTOMapper.apply(user);

    }

    @Override
    public Boolean validateUserEmail(String email) {
        boolean userExists = userRepository.findByEmail(email).isPresent();
        if (userExists) {
            LOGGER.error("Email already taken: {}", email);
            throw new ApiRequestException("Email taken");
        }
        return true;
    }

    private void saveUserToDatabaseWithPasswordDecoder(User user) {
        try {
            // Decode the provided plain text password (this is not a recommended practice)
            String decodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(decodedPassword);
            // Save the user to the database
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error decoding password and saving user to the database: {}", e.getMessage(), e);
            throw new RuntimeException("Error saving user to the database");
        }
    }


    @Override
    public Boolean authenticated(LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        try {
            LOGGER.info("Authenticating user with email: {}", request.getEmail());
            Boolean isAuthenticated = authenticationManager.authenticate(authentication).isAuthenticated();
            LOGGER.info("User authenticated successfully: {}", request.getEmail());
            return isAuthenticated;
        } catch (RuntimeException e) {
            LOGGER.error("Authentication failed for user with email: {}", request.getEmail(), e);
            throw new ApiAuthenticationRequestException(e.getMessage());
        }
    }


    @Override
    public String processForgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        String passwordResetToken = passwordResetService.generatePasswordResetToken(user);
        passwordResetService.sendResetPasswordTokenEmail(user.getFullName(), email, passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public String resetPassword(PasswordResetRequest request) {
        PasswordResetToken passwordResetToken = passwordResetService.validatePasswordResetToken(request.token());
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);

        passwordResetToken.setResetAt(LocalDateTime.now());
        passwordResetService.savePasswordResetToken(passwordResetToken);
        return "Update password Successful";
    }

    @Override
    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // If you have a custom UserDetails implementation, you can cast the principal to it.
            // Here assuming UserDetails is implemented by your CustomUserDetails class.
            return (User) authentication.getPrincipal();

        }
        return null; // Return null if the user is not authenticated
    }

    @Override
    public Boolean verifyUserAccount(String token) {
        try {
            LOGGER.info("Verifying user token: {}", token);
            User enabledUser = confirmationService.updateConfirmedToken(token);
            LOGGER.info("User enabled: {}", enabledUser.getEmail());
        } catch (RuntimeException e) {
            LOGGER.error("Error verifying user token: {}", e.getMessage(), e);
            throw new ApiAuthenticationRequestException("User was active");
        }
        return Boolean.TRUE;
    }
}
