package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.token.PasswordResetToken;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.repository.PasswordResetRepository;
import com.rse.mobile.MobileWebservice.service.PasswordResetService;
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

    @Override
    public String generatePasswordResetToken(User user) {

        LOGGER.info("Generating confirmation token for user: {}", user.getEmail());
        PasswordResetToken token = new PasswordResetToken(user);
        LOGGER.info("Generated confirmation token: {}", token.getToken());
        passwordResetTokenRepository.save(token);
        return token.generateToken();
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
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

}