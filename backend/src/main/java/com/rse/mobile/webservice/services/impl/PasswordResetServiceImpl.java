package com.rse.mobile.webservice.services.impl;

import com.rse.mobile.webservice.exceptions.request.ApiRequestException;
import com.rse.mobile.webservice.entities.tokens.PasswordResetToken;
import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.repositories.PasswordResetRepository;
import com.rse.mobile.webservice.services.EmailService;
import com.rse.mobile.webservice.services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PasswordResetServiceImpl implements PasswordResetService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PasswordResetServiceImpl.class);
    private final PasswordResetRepository passwordResetTokenRepository;
    private final EmailService emailService;
    @Override
    public String generatePasswordResetToken(User user) {

        LOGGER.info("Generating confirmation token for user: {}", user.getEmail());
        PasswordResetToken token = new PasswordResetToken(user);
        LOGGER.info("Generated confirmation token: {}", token.getToken());
        passwordResetTokenRepository.save(token);
        return token.generateToken();
    }

    @Override
    public PasswordResetToken validatePasswordResetToken(String token) {
        if (isExpiredToken(token)) {
            LOGGER.error("Expired passwordResetToken token: {}", token);
            throw new ApiRequestException("Token expired");
        }
        LOGGER.info("Updating passwordResetToken token: {}", token);
        return passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new ApiRequestException("Token invalid"));
    }

    @Override
    public void savePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    private Boolean isExpiredToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new ApiRequestException("Reset Password token invalid"));
        boolean isExpired = passwordResetToken.getExpiredAt().isBefore(LocalDateTime.now());
        if (isExpired) {
            LOGGER.error("Password Reset Token expired: {}", token);
        }

        return isExpired;
    }
    @Override
    public void sendResetPasswordTokenEmail(String fullName, String email, String resetToken) {
        try {
            emailService.sendHtmlResetPasswordMailMessage(fullName, email, resetToken);
            LOGGER.info("Reset password email sent successfully to user: {}", fullName);
        } catch (Exception e) {
            LOGGER.error("Failed to send reset password email to user: {}", fullName, e);
            throw new ApiRequestException("Failed to send reset password email");
        }
    }

}
