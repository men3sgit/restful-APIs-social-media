package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.entities.tokens.PasswordResetToken;
import com.rse.mobile.webservice.entities.User;

public interface PasswordResetService {
    String generatePasswordResetToken(User user);

    PasswordResetToken validatePasswordResetToken(String token);

    void savePasswordResetToken(PasswordResetToken passwordResetToken);

    void sendResetPasswordTokenEmail(String fullName, String email, String resetToken);
}
