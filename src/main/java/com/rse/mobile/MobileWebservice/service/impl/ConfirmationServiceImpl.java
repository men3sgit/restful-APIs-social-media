package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.exception.ApiRequestException;
import com.rse.mobile.MobileWebservice.exception.auth.ApiAuthenticationRequestException;
import com.rse.mobile.MobileWebservice.model.token.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.repository.ConfirmationRepository;
import com.rse.mobile.MobileWebservice.service.template.ConfirmationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationServiceImpl.class);

    private final ConfirmationRepository confirmationRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        LOGGER.info("Saving confirmation token: {}", token.getToken());
        confirmationRepository.save(token);
    }

    @Override
    public ConfirmationToken generateConfirmationTokenWithUser(User user) {
        LOGGER.info("Generating confirmation token for user: {}", user.getEmail());

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        LOGGER.info("Generated confirmation token: {}", confirmationToken.getToken());

        return confirmationToken;
    }

    @Override
    public User updateConfirmedToken(String token) {
        if (isExpiredToken(token)) {
            LOGGER.error("Expired confirmation token: {}", token);
            throw new ApiRequestException("Token expired");
        }

        LocalDateTime now = LocalDateTime.now();

        LOGGER.info("Updating confirmation token: {}", token);

        ConfirmationToken updateToken = confirmationRepository.findByToken(token).orElseThrow(() -> new ApiAuthenticationRequestException("Token invalid"));

        updateToken.setConfirmedAt(now);
        confirmationRepository.save(updateToken);

        LOGGER.info("Updated confirmation token at: {}", now);

        User updateUser = confirmationRepository.findUserByToken(token);
        updateUser.setEnabled(Boolean.TRUE);

        LOGGER.info("Updated user: {}", updateUser);

        return updateUser;
    }

    private Boolean isExpiredToken(String token) {
        ConfirmationToken confirmationToken = confirmationRepository.findByToken(token).orElseThrow(() -> new ApiRequestException("Confirmation token invalid"));

        boolean isExpired = confirmationToken.getExpiredAt().isBefore(LocalDateTime.now());

        if (isExpired) {
            LOGGER.error("Confirmation token expired: {}", token);
        }

        return isExpired;
    }


}
