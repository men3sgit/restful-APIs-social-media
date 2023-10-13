package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.dto.UserDTO;

import java.util.List;

public interface UserFollowerService {
    void followUser(Long userId, Long followerId);

    List<UserDTO> getFollowers(Long userId);

    List<UserDTO> getFollowing(Long userId);

    void unfollowUser(Long userId, Long followerId);
}
