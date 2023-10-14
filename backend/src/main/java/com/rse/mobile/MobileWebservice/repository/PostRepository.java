package com.rse.mobile.MobileWebservice.repository;

import com.rse.mobile.MobileWebservice.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
}
