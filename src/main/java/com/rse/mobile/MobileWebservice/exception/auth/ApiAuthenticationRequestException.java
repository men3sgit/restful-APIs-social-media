package com.rse.mobile.MobileWebservice.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class ApiAuthenticationRequestException extends AuthenticationException {

    public ApiAuthenticationRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ApiAuthenticationRequestException(String msg) {
        super(msg);
    }
}
