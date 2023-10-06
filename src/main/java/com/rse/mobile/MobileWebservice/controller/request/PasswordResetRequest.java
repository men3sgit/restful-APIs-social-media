package com.rse.mobile.MobileWebservice.controller.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

public record PasswordResetRequest(
        String password,
        String token
) {
}
