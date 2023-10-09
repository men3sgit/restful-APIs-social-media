package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.user.User;

public interface ResetPasswordTokenService {
    String generateResetPasswordTokenWithUser(User user);
}
