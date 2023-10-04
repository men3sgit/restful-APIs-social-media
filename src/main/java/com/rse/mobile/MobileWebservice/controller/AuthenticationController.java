package com.rse.mobile.MobileWebservice.controller;

import com.rse.mobile.MobileWebservice.controller.request.LoginRequest;
import com.rse.mobile.MobileWebservice.controller.request.RegistrationRequest;
import com.rse.mobile.MobileWebservice.controller.response.RegistrationResponse;
import com.rse.mobile.MobileWebservice.service.template.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService authenticationService;

    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        LOGGER.info("Received registration request for email: {}", request.getEmail());

        String message = authenticationService.registerNewUser(request);

        RegistrationResponse response = RegistrationResponse.builder()
                .message(message)
                .build();

        LOGGER.info("Registration response: {}", response.message());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LOGGER.info("Received login request for email: {}", request.getEmail());

        authenticationService.authenticated(request);

        LOGGER.info("User logged in successfully");

        return new ResponseEntity<>("Log in successful!", HttpStatus.OK);
    }

    @GetMapping(path = "verify")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String token) {
        LOGGER.info("Received verification request for token: {}", token);

        Boolean isSuccess = authenticationService.verifyToken(token);

        LOGGER.info("Verification result: {}", isSuccess);

        return ResponseEntity.ok().body("Verify Successful");
    }


}
