package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.dto.UserDTO;

import java.util.List;

public interface UserFollowerService {
    int FOLLOW = 1;
    int UNFOLLOW = 2;
    void followUser(Long userId, Long followerId);

    List<UserDTO> getFollowers(Long userId);

    List<UserDTO> getFollowing(Long userId);

    void unfollowUser(Long userId, Long followerId);
}
