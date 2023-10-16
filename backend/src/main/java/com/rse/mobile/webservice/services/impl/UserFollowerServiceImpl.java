package com.rse.mobile.webservice.services.impl;

import com.rse.mobile.webservice.dto.UserDTO;
import com.rse.mobile.webservice.dto.mapper.UserDTOMapper;
import com.rse.mobile.webservice.exceptions.request.ApiRequestException;
import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.entities.UserFollower;
import com.rse.mobile.webservice.repositories.UserFollowerRepository;
import com.rse.mobile.webservice.repositories.UserRepository;
import com.rse.mobile.webservice.services.UserFollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserFollowerServiceImpl implements UserFollowerService {
    private final UserFollowerRepository userFollowerRepository;
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Override
    public void followUser(Long followedId, Long followById) {
        Optional<User> userOptional = userRepository.findById(followedId);
        Optional<User> followerOptional = userRepository.findById(followById);

        if (userOptional.isPresent() && followerOptional.isPresent()) {
            User followed = userOptional.get();
            User followBy = followerOptional.get();

            // Check if the relationship already exists
            if (userFollowerRepository.findByUserAndFollower(followed, followBy).isEmpty()) {
                UserFollower userFollower = new UserFollower(followed, followBy);
                userFollowerRepository.save(userFollower);
            } else {
                // Handle case where the relationship already exists
                throw new ApiRequestException("User with ID " + followedId + " is already being followed by user with ID " + followById);
            }
        } else {
            // Handle case where user or follower is not found
            throw new ApiRequestException("User or follower not found.");
        }
    }

    @Override
    public List<UserDTO> getFollowers(Long userId) {
        return userFollowerRepository
                .findByUserId(userId)
                .stream()
                .map(follower -> userDTOMapper
                        .apply(follower.getUser())).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getFollowing(Long followerId) {
        return userFollowerRepository
                .findByFollowerId(followerId)
                .stream()
                .map(follower -> userDTOMapper
                        .apply(follower.getUser())).collect(Collectors.toList());
    }


    @Override
    public void unfollowUser(Long userId, Long followerId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<User> followerOptional = userRepository.findById(followerId);

        if (userOptional.isPresent() && followerOptional.isPresent()) {
            User followed = userOptional.get();
            User followBy = followerOptional.get();

            // Find the existing relationship in the database
            Optional<UserFollower> existingRelationship = userFollowerRepository.findByUserAndFollower(followed, followBy);

            if (existingRelationship.isPresent()) {
                // If the relationship exists, remove it from the database
                userFollowerRepository.delete(existingRelationship.get());
            } else {
                // Handle case where the relationship does not exist
                throw new ApiRequestException("User with ID " + userId + " is not being followed by user with ID " + followerId);
            }
        } else {
            // Handle case where user or follower is not found
            throw new ApiRequestException("User or follower not found.");
        }
    }
}
