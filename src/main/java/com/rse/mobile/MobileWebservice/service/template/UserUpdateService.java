package com.rse.mobile.MobileWebservice.service.template;

import com.rse.mobile.MobileWebservice.model.user.UserDTO;
import com.rse.mobile.MobileWebservice.controller.request.UpdateUserRequest;

// UserUpdateService.java
public interface UserUpdateService {
    UserDTO updateUser(Long userId,UpdateUserRequest updateUserRequest);

}