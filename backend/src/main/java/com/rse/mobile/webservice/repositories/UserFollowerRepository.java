package com.rse.mobile.webservice.repositories;

import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.entities.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {


    Optional<UserFollower> findByUserIdAndFollowerId(Long userId, Long followerId);

    Optional<UserFollower> findByUserAndFollower(User user, User follower);

    List<UserFollower> findByFollowerId(Long followedId);

    List<UserFollower> findByUserId(Long userId);

}
