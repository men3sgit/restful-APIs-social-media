package com.rse.mobile.MobileWebservice.service.template;

import com.rse.mobile.MobileWebservice.dto.UserDTO;
import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.request.UpdateUserRequest;

// UserUpdateService.java
public interface UserUpdateService {
    UserDTO updateUser(Long userId,UpdateUserRequest updateUserRequest);

}