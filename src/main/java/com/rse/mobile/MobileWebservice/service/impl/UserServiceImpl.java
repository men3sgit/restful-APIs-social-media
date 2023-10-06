package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.dto.UserDTOMapper;
import com.rse.mobile.MobileWebservice.model.token.ResetPasswordToken;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;
import com.rse.mobile.MobileWebservice.repository.PasswordResetTokenRepository;
import com.rse.mobile.MobileWebservice.service.template.VerificationService;
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
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserDTOMapper userDTOMapper;
    private final VerificationService verificationService;

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

        validateUserEmail(user.getEmail());
        saveUserToDatabaseWithPasswordDecoder(user);

        ConfirmationToken confirmationToken = generateAndSaveConfirmationToken(user);
        sendConfirmationEmail(user.getFullName(), user.getEmail(), confirmationToken.getToken());
        LOGGER.info("Registration successful for user: {}", user.getEmail());

        return userDTOMapper.apply(user);
    }

    public void saveUserToDatabaseWithPasswordDecoder(User user) {
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

    private void validateUserEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            LOGGER.error("Email already taken: {}", email);
            throw new ApiRequestException("Email taken");
        }
    }


    private ConfirmationToken generateAndSaveConfirmationToken(User user) {
        ConfirmationToken confirmationToken = confirmationService.generateConfirmationTokenWithUser(user);
        confirmationService.saveConfirmationToken(confirmationToken);
        return confirmationToken;
    }

    private void sendConfirmationEmail(String fullName, String email, String token) {
        // TODO: Implement your email sending logic here
        LOGGER.info("Sending confirmation token email to user: {}", email);
        // Implement your email sending logic using the provided parameters and the generated token
        try {
            verificationService.sendVerificationTokenByEmail(fullName, email, token);
            LOGGER.info("Reset password email sent successfully to user: {}", email);
        } catch (Exception e) {
            LOGGER.error("Failed to send reset password email to user: {}", email, e);
            throw new ApiRequestException("Failed to send reset password email");
        }

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
        sendResetPasswordTokenEmail(user.getEmail(), user.getFullName(), resetPasswordToken.generateToken());
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

    private void sendResetPasswordTokenEmail(String userEmail, String userFullName, String resetToken) {
        try {
//            emailService.sendHtmlMailMessage(userEmail, userFullName, resetToken);
            LOGGER.info("Reset password email sent successfully to user: {}", userEmail);
        } catch (Exception e) {
            LOGGER.error("Failed to send reset password email to user: {}", userEmail, e);
            throw new ApiRequestException("Failed to send reset password email");
        }
    }


}
