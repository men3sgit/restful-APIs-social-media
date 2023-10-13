package com.rse.mobile.MobileWebservice.repository;

import com.rse.mobile.MobileWebservice.model.entities.User;
import com.rse.mobile.MobileWebservice.model.entities.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {


    Optional<UserFollower> findByUserIdAndFollowerId(Long userId, Long followerId);

    Optional<UserFollower> findByUserAndFollower(User user, User follower);

    List<UserFollower> findByFollowerId(Long followedId);

    List<UserFollower> findByUserId(Long userId);

}
