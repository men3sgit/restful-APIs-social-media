package com.rse.mobile.MobileWebservice.model.token;

import java.time.LocalDateTime;

public interface Token {
    String generateToken();
    LocalDateTime getExpiredAt();
    void setExpiredAt(LocalDateTime expiryDate);
}
