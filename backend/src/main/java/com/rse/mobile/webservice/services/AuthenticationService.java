package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.payload.requests.AuthenticationRequest;
import com.rse.mobile.webservice.payload.requests.PasswordResetRequest;
import com.rse.mobile.webservice.payload.requests.RegistrationRequest;
import com.rse.mobile.webservice.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    UserDTO registerNewUser(RegistrationRequest request);
    Boolean validateUserEmail(String email);
    String authenticated(AuthenticationRequest request);
    String processForgotPassword(String email);

    String resetPassword(PasswordResetRequest request);
    User getCurrentAuthenticatedUser();

    Boolean verifyUserAccount(String token);
    String logout();

}
