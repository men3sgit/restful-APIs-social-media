package com.rse.mobile.MobileWebservice.model.request;

import lombok.*;

public record PasswordResetRequest(
        String token,
        String resetPassword

) {
}
