package com.rse.mobile.MobileWebservice.model.requests;

public record PasswordResetRequest(
        String newPassword,
        String token
) {
}
