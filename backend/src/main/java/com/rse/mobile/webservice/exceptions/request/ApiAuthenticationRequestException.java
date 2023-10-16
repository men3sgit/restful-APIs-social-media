package com.rse.mobile.webservice.exceptions.request;

import org.springframework.security.core.AuthenticationException;

public class ApiAuthenticationRequestException extends AuthenticationException {

    public ApiAuthenticationRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ApiAuthenticationRequestException(String msg) {
        super(msg);
    }
}
