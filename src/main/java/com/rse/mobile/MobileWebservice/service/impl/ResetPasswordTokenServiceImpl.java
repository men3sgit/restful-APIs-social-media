package com.rse.mobile.MobileWebservice.service.impl;

import com.rse.mobile.MobileWebservice.model.token.ConfirmationToken;
import com.rse.mobile.MobileWebservice.model.token.ResetPasswordToken;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.service.ResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ResetPasswordTokenServiceImpl.class);

    @Override
    public String generateResetPasswordTokenWithUser(User user) {

        LOGGER.info("Generating confirmation token for user: {}", user.getEmail());
        ResetPasswordToken token = new ResetPasswordToken(user);
        LOGGER.info("Generated confirmation token: {}", token.getToken());

        return token.generateToken();
    }

}
