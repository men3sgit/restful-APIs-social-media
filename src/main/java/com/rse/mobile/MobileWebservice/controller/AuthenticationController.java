package com.rse.mobile.MobileWebservice.controller;

import com.rse.mobile.MobileWebservice.controller.request.LoginRequest;
import com.rse.mobile.MobileWebservice.controller.request.RegistrationRequest;
import com.rse.mobile.MobileWebservice.controller.response.ResponseHandler;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;
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

        Boolean isSuccess = authenticationService.verifyToken(token);

        LOGGER.info("Verification result: {}", isSuccess);
        String msg = "Verify Successful";
        Map<String, Object> data = Map.of("token", token);
        return responseHandler.buildSuccessResponse(msg, data);
    }
}
