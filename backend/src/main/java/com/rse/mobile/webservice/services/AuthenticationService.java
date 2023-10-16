package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.payload.requests.LoginRequest;
import com.rse.mobile.webservice.payload.requests.PasswordResetRequest;
import com.rse.mobile.webservice.payload.requests.RegistrationRequest;
import com.rse.mobile.webservice.dto.UserDTO;

public interface AuthenticationService {

    String registerNewUser(RegistrationRequest request);
    Boolean validateUserEmail(String email);
    Boolean authenticated(LoginRequest request);
    String processForgotPassword(String email);

    String resetPassword(PasswordResetRequest request);
    User getCurrentAuthenticatedUser();

    Boolean verifyUserAccount(String token);

}
