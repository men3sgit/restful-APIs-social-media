package com.rse.mobile.MobileWebservice.dto;

import com.rse.mobile.MobileWebservice.model.user.User;
import com.rse.mobile.MobileWebservice.model.user.UserDTO;

public class UserDTOMapperImpl implements UserDTOMapper{
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getFullName(),
                user.getDob(),
                user.getRole()
        );
    }
}
