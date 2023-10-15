package com.rse.mobile.MobileWebservice.controller;

import com.rse.mobile.MobileWebservice.model.reponses.ResponseHandler;
import com.rse.mobile.MobileWebservice.model.requests.ForgotPasswordRequest;
import com.rse.mobile.MobileWebservice.model.requests.LoginRequest;
import com.rse.mobile.MobileWebservice.model.requests.PasswordResetRequest;
import com.rse.mobile.MobileWebservice.model.requests.RegistrationRequest;
import com.rse.mobile.MobileWebservice.dto.UserDTO;
import com.rse.mobile.MobileWebservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;
    private final ResponseHandler responseHandler;

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        LOGGER.info("Received registration request for email: {}", request.getEmail());

        UserDTO userDTO = authenticationService.registerNewUser(request);
        String message = "User created successfully";
        LOGGER.info("Registration response: {}", message);
        return responseHandler.buildSuccessResponse(message, Map.of("user", userDTO),HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LOGGER.info("Received login request for email: {}", request.getEmail());
        authenticationService.authenticated(request);
        String msg = "User logged in successfully";
        LOGGER.info(msg);
        return responseHandler.buildSuccessResponse(msg, Map.of());
    }

    @GetMapping(path = "verify")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String token) {
        LOGGER.info("Received verification request for token: {}", token);

        Boolean isSuccess = authenticationService.verifyUserAccount(token);

        LOGGER.info("Verification result: {}", isSuccess);
        String msg = "Verify Successful";
        Map<String, Object> data = Map.of("token", token);
        return responseHandler.buildSuccessResponse(msg, data);
    }
    @PostMapping(path = "forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        LOGGER.info("Received forgot password request for email: {}", email);
        try {
            authenticationService.processForgotPassword(email);
            LOGGER.info("Forgot password email sent successfully for email: {}", email);
            return responseHandler.buildSuccessResponse("Password reset link sent successfully", Map.of());
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing forgot password request for email: {}", email, e);
            return responseHandler.buildInternalServerErrorResponse(e.getMessage());
        }
    }

    @PutMapping(path = "password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request) {
        authenticationService.resetPassword(request);
        return responseHandler.buildSuccessResponse("Your password reset successfully");

    }
}
