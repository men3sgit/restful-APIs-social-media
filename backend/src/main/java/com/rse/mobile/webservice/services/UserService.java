package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.payload.requests.UpdateUserRequest;
import com.rse.mobile.webservice.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDTO updateUser(Long userId, UpdateUserRequest updateUserRequest);

    // 13/10/2023

    void followOrUnfollow(Long followedId, int mode);

    List<UserDTO> getFollowers(Long userId);

    //  14.10.2023


}
