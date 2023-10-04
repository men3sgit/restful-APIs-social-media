package com.rse.mobile.MobileWebservice.controller;

import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.controller.request.ForgotPasswordRequest;
import com.rse.mobile.MobileWebservice.controller.request.UpdateUserRequest;
import com.rse.mobile.MobileWebservice.controller.response.HttpResponse;
import com.rse.mobile.MobileWebservice.controller.response.HttpResponseFactory;
import com.rse.mobile.MobileWebservice.service.template.UserService;
import com.rse.mobile.MobileWebservice.service.template.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserUpdateService userUpdateService;
    private final UserService userService;

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable(value = "userId") Long userId,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        LOGGER.info("Received update user request for user ID: {}", userId);

        try {
            userUpdateService.updateUser(userId,updateUserRequest);
            HttpResponse response = HttpResponseFactory.buildSuccessResponse("User information updated successfully", "/api/v1/users/");

            return ResponseEntity.ok(response);
        } catch (ApiRequestException e) {
            LOGGER.error("User not found: {}", e.getMessage());
            HttpResponse response = HttpResponseFactory.buildNotFoundResponse("User not found", "/api/v1/users/");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            LOGGER.error("Error updating user: {}", e.getMessage());
            HttpResponse response = HttpResponseFactory.buildErrorResponse("Error updating user", "/api/v1/users/");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping(path = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        LOGGER.info("Received forgot password request for email: {}", email);
        try {
            userService.processForgotPassword(email);
            LOGGER.info("Forgot password email sent successfully for email: {}", email);
            return ResponseEntity.ok("Password reset link sent successfully");
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing forgot password request for email: {}", email, e);
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }



}
