package com.rse.mobile.MobileWebservice.dto.mapper;

import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapperImpl implements UserDTOMapper {
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
