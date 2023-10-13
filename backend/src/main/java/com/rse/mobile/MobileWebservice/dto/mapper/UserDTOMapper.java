package com.rse.mobile.MobileWebservice.dto.mapper;

import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.dto.UserDTO;

import java.util.function.Function;

public interface UserDTOMapper extends Function<User, UserDTO> {
    @Override
    UserDTO apply(User user);
}
