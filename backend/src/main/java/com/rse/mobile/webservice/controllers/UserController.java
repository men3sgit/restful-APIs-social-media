package com.rse.mobile.webservice.controllers;

import com.rse.mobile.webservice.payload.reponses.ResponseHandler;
import com.rse.mobile.webservice.exceptions.request.ApiRequestException;
import com.rse.mobile.webservice.payload.requests.FollowRequest;
import com.rse.mobile.webservice.payload.requests.UpdateUserRequest;
import com.rse.mobile.webservice.dto.UserDTO;
import com.rse.mobile.webservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ResponseHandler responseHandler;

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "userId") Long userId, @RequestBody UpdateUserRequest updateUserRequest) {
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

    @GetMapping("{userId}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long userId) {
        List<UserDTO> followers = userService.getFollowers(userId);
        Map<String, ?> data = Map.of("followers info", followers.isEmpty() ? "Empty" : followers, "followed by", followers.size());
        return responseHandler.buildSuccessResponse(data);

    }

    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@RequestBody FollowRequest request) {
        try {
            userService.followUser(request.followedId(), request.followById());
            return responseHandler.buildSuccessResponse("User followed successfully.", request.followById());
        } catch (RuntimeException e) {
            return responseHandler.buildErrorResponse("User followed failure.", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestBody FollowRequest request) {
        try {
            userService.unfollowUser(request.followedId(), request.followById());
            return responseHandler.buildSuccessResponse("User unfollowed successfully.", request.followById());
        } catch (RuntimeException e) {
            return responseHandler.buildErrorResponse("User unfollowed failure.", e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}


