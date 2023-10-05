package com.rse.mobile.MobileWebservice.service.template;

import com.rse.mobile.MobileWebservice.controller.request.LoginRequest;
import com.rse.mobile.MobileWebservice.controller.request.RegistrationRequest;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;

public interface AuthenticationService {
    UserDTO registerNewUser(RegistrationRequest request);

    void authenticated(LoginRequest request);

    Boolean verifyToken(String token);
}
