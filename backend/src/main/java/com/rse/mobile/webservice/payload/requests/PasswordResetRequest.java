package com.rse.mobile.webservice.payload.requests;

public record PasswordResetRequest(
        String newPassword,
        String token
) {
}
