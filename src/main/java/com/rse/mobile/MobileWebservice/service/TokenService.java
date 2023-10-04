package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.token.Token;

public interface TokenService<T extends Token> {
    T generateToken();
    boolean isTokenValid(T token);
}