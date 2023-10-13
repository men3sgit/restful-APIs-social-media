package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.requests.LoginRequest;
import com.rse.mobile.MobileWebservice.model.requests.RegistrationRequest;
import com.rse.mobile.MobileWebservice.dto.UserDTO;

public interface AuthenticationService {
    UserDTO registerNewUser(RegistrationRequest request);
    void authenticated(LoginRequest request);
    Boolean verifyToken(String token);
}
