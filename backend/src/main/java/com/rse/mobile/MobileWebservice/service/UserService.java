package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.model.requests.PasswordResetRequest;
import com.rse.mobile.MobileWebservice.model.requests.UpdateUserRequest;
import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserDTO registerNewUser(User user);

    Boolean verifyConfirmationToken(String token);


    String processForgotPassword(String email);

    UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest);

    String resetPassword(PasswordResetRequest request);

    // 13/10/2023
    void followUser(Long followedId, Long followById);
    void unfollowUser(Long followedId, Long followById);

    List<UserDTO> getFollowers(Long userId);
}
