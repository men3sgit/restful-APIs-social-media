package com.rse.mobile.MobileWebservice.model.token;

import java.time.LocalDateTime;

public interface Token {
    boolean isExpired();
    String generateToken();
    LocalDateTime getExpiredAt();
    void setExpiredAt(LocalDateTime expiryDate);
}
