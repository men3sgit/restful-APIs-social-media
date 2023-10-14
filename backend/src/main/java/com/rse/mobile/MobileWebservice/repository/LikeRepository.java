package com.rse.mobile.MobileWebservice.repository;


import com.rse.mobile.MobileWebservice.model.entities.Like;
import com.rse.mobile.MobileWebservice.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like,Long> {
}
