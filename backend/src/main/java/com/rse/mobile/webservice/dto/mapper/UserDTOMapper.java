package com.rse.mobile.webservice.dto.mapper;

import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserDTOMapper implements DTOMapper<User, UserDTO> {
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
