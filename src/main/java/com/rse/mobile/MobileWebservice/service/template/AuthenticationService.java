package com.rse.mobile.MobileWebservice.service.template;

import com.rse.mobile.MobileWebservice.request.LoginRequest;
import com.rse.mobile.MobileWebservice.request.RegistrationRequest;

public interface AuthenticationService {
    String registerNewUser(RegistrationRequest request);

    void authenticated(LoginRequest request);

    Boolean verifyToken(String token);
}
