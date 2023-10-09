package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.controller.request.UpdateUserRequest;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDTO registerNewUser(User user);

    Boolean verifyConfirmationToken(String token);


    String processForgotPassword(String email);
    UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest);
}
