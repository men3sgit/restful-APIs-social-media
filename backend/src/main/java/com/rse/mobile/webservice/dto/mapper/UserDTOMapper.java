package com.rse.mobile.webservice.dto.mapper;

import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.dto.UserDTO;

import java.util.function.Function;

public interface UserDTOMapper extends Function<User, UserDTO> {
    @Override
    UserDTO apply(User user);
}
