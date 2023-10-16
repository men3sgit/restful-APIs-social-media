package com.rse.mobile.webservice.services.impl;

import com.rse.mobile.webservice.exceptions.request.ApiRequestException;
import com.rse.mobile.webservice.entities.tokens.ConfirmationToken;
import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.repositories.ConfirmationRepository;
import com.rse.mobile.webservice.repositories.UserRepository;
import com.rse.mobile.webservice.services.ConfirmationService;
import com.rse.mobile.webservice.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ConfirmationServiceImpl implements ConfirmationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationServiceImpl.class);
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @Override
    public ConfirmationToken generateConfirmationToken(User user) {
        LOGGER.info("Generating confirmation token for user: {}", user.getEmail());

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        LOGGER.info("Generated confirmation token: {}", confirmationToken.getToken());

        return confirmationToken;
    }

    @Override
    public Boolean validateToken(String token) {
        ConfirmationToken confirmationToken = confirmationRepository.findByToken(token).orElseThrow(() -> new ApiRequestException("Token invalid"));
        return !isExpiredToken(confirmationToken);
    }


    @Override
    public User updateConfirmedToken(String token) {
        if (!validateToken(token)) {
            LOGGER.error("Expired confirmation token: {}", token);
            throw new ApiRequestException("Token expired");
        }

        LOGGER.info("Updating confirmation token: {}", token);
        ConfirmationToken updateToken = confirmationRepository
                .findByToken(token).get();

        LocalDateTime now = LocalDateTime.now();
        updateToken.setConfirmedAt(now);
        confirmationRepository.save(updateToken);

        LOGGER.info("Updated confirmation token at: {}", now);

        User updateUser = confirmationRepository.findUserByToken(token);
        updateUser.setEnabled(Boolean.TRUE);
        userRepository.save(updateUser);
        LOGGER.info("Updated user: {}", updateUser);
        return updateUser;
    }

    private Boolean isExpiredToken(ConfirmationToken confirmationToken) {
        boolean isExpired = confirmationToken.getExpiredAt().isBefore(LocalDateTime.now());
        if (isExpired) {
            LOGGER.error("Confirmation token expired: {}", confirmationToken.getToken());
            throw new ApiRequestException("Token was expired");
        }

        return false;
    }

    @Override
    public void sendConfirmationEmail(String fullName, String email, String token) {
        // TODO: Implement your email sending logic here
        LOGGER.info("Sending confirmation token email to user: {}", email);
        // Implement your email sending logic using the provided parameters and the generated token
        try {
            emailService.sendHtmlMailMessage(fullName, email, token);
            LOGGER.info("Verification: {}", email);
        } catch (Exception e) {
            LOGGER.error("Verification{}", email, e);
            throw new ApiRequestException("Email ");
        }
    }


}