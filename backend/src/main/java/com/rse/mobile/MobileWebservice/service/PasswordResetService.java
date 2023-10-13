package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.entities.tokens.PasswordResetToken;
import com.rse.mobile.MobileWebservice.model.entities.User;

public interface PasswordResetService {
    String generatePasswordResetToken(User user);
    PasswordResetToken getPasswordResetToken(String token);
    void savePasswordResetToken(PasswordResetToken passwordResetToken);

}
