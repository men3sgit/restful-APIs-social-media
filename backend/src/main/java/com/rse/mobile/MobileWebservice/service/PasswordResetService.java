package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.token.PasswordResetToken;
import com.rse.mobile.MobileWebservice.model.user.User;

public interface PasswordResetService {
    String generatePasswordResetToken(User user);
    PasswordResetToken getPasswordResetToken(String token);
    void savePasswordResetToken(PasswordResetToken passwordResetToken);

}
