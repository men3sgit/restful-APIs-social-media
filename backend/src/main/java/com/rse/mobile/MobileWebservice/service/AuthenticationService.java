package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.model.requests.LoginRequest;
import com.rse.mobile.MobileWebservice.model.requests.PasswordResetRequest;
import com.rse.mobile.MobileWebservice.model.requests.RegistrationRequest;
import com.rse.mobile.MobileWebservice.dto.UserDTO;

public interface AuthenticationService {

    UserDTO registerNewUser(RegistrationRequest request);
    Boolean validateUserEmail(String email);
    Boolean authenticated(LoginRequest request);
    String processForgotPassword(String email);

    String resetPassword(PasswordResetRequest request);
    User getCurrentAuthenticatedUser();

    Boolean verifyUserAccount(String token);
}
