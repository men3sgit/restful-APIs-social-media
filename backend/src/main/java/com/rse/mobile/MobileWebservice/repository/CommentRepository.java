package com.rse.mobile.MobileWebservice.repository;

import com.rse.mobile.MobileWebservice.model.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}