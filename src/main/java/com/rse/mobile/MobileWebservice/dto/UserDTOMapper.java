package com.rse.mobile.MobileWebservice.dto;

import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;

import java.util.function.Function;

public interface UserDTOMapper extends Function<User, UserDTO> {
    @Override
    UserDTO apply(User user);
}
