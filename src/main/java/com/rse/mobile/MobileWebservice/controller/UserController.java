package com.rse.mobile.MobileWebservice.controller;

import com.rse.mobile.MobileWebservice.controller.request.ForgotPasswordRequest;
import com.rse.mobile.MobileWebservice.controller.request.PasswordResetRequest;
import com.rse.mobile.MobileWebservice.controller.request.UpdateUserRequest;
import com.rse.mobile.MobileWebservice.controller.response.ResponseHandler;
import com.rse.mobile.MobileWebservice.exception.request.ApiRequestException;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;
import com.rse.mobile.MobileWebservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ResponseHandler responseHandler;

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable(value = "userId") Long userId,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        LOGGER.info("Received update user request for user ID: {}", userId);
        try {
            UserDTO userDTO = userService.updateUser(userId, updateUserRequest);
            return responseHandler.buildSuccessResponse("User information updated successfully", Map.of("user", userDTO));
        } catch (ApiRequestException e) {
            String msg = "User not found";
            LOGGER.error(msg + ": {}", e.getMessage());
            return responseHandler.buildErrorResponse(msg, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error("Error updating user: {}", e.getMessage());
            return responseHandler.buildInternalServerErrorResponse("Error updating user");
        }
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        LOGGER.info("Received forgot password request for email: {}", email);
        try {
            userService.processForgotPassword(email);
            LOGGER.info("Forgot password email sent successfully for email: {}", email);
            return responseHandler.buildSuccessResponse("Password reset link sent successfully", Map.of());
        } catch (Exception e) {
            LOGGER.error("Error occurred while processing forgot password request for email: {}", email, e);
            return responseHandler.buildInternalServerErrorResponse(e.getMessage());
        }
    }
    @PutMapping(path = "/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest request){
        return null;

    }
}


