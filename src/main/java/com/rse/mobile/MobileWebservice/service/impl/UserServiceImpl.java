package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.dto.UserDTOMapper;
import com.rse.mobile.MobileWebservice.model.token.ResetPasswordToken;
import com.rse.mobile.MobileWebservice.model.token.Token;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;
import com.rse.mobile.MobileWebservice.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.repository.UserRepository;
import com.rse.mobile.MobileWebservice.service.template.ConfirmationService;
import com.rse.mobile.MobileWebservice.service.template.EmailService;
import com.rse.mobile.MobileWebservice.service.template.UserService;
import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.exception.auth.ApiAuthenticationRequestException;
import com.rse.mobile.MobileWebservice.model.token.ConfirmationToken;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String USER_NOT_FOUND_MSG = "user with email %s not found.";
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ConfirmationService confirmationService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserDTOMapper userDTOMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Loading user by username: {}", username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    String errorMsg = String.format(USER_NOT_FOUND_MSG, username);
                    LOGGER.error(errorMsg);
                    return new UsernameNotFoundException(errorMsg);
                });
    }

    @Override
    public UserDTO registerNewUser(User user) {
        LOGGER.info("Registering a new user with email: {}", user.getEmail());

        boolean isUserExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (isUserExists) {
            LOGGER.error("Email already taken: {}", user.getEmail());
            throw new ApiRequestException("Email taken");
        }

        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        } catch (RuntimeException e) {
            LOGGER.error("Error saving user: {}", e.getMessage(), e);
            throw new ApiRequestException("Your request parameters are not valid.");
        }

        // TODO send token confirm
        ConfirmationToken generateConfirmToken = confirmationService.generateConfirmationTokenWithUser(user);
        confirmationService.saveConfirmationToken(generateConfirmToken);
        String token = generateConfirmToken.getToken();

        // TODO Email sender send token to email
        LOGGER.info("Sending confirmation token email to user: {}", user.getEmail());
        sendTokenEmail(user.getFullName(), user.getEmail(), token);

        LOGGER.info("Registration successful for user: {}", user.getEmail());

        return userDTOMapper.apply(user);
    }

    @Override
    public Boolean verifyConfirmationToken(String token) {
        try {
            LOGGER.info("Verifying user token: {}", token);
            User enabledUser = confirmationService.updateConfirmedToken(token);
            userRepository.save(enabledUser);
            LOGGER.info("User enabled: {}", enabledUser.getEmail());
        } catch (RuntimeException e) {
            LOGGER.error("Error verifying user token: {}", e.getMessage(), e);
            throw new ApiAuthenticationRequestException("User was active");
        }
        return Boolean.TRUE;
    }

    @Override
    public String processForgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        ResetPasswordToken resetPasswordToken = createAndSaveResetPasswordToken(user);
        sendTokenEmail(user.getEmail(), user.getFullName(), resetPasswordToken.generateToken());
        return "Successful";
    }


    private ResetPasswordToken createAndSaveResetPasswordToken(User user) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);
        try {
            return passwordResetTokenRepository.save(resetPasswordToken);
        } catch (Exception e) {
            LOGGER.error("Failed to save reset password token for user: {}", user.getEmail(), e);
            throw new ApiRequestException("Failed to initiate password reset process");
        }
    }

    private void sendTokenEmail(String userEmail, String userFullName, String resetToken) {
        try {
            emailService.sendHtmlMailMessage(userEmail, userFullName, resetToken);
            LOGGER.info("Reset password email sent successfully to user: {}", userEmail);
        } catch (Exception e) {
            LOGGER.error("Failed to send reset password email to user: {}", userEmail, e);
            throw new ApiRequestException("Failed to send reset password email");
        }
    }


}
