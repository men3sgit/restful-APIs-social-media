package com.rse.mobile.MobileWebservice.repository;

import com.rse.mobile.MobileWebservice.model.token.PasswordResetToken;
import com.rse.mobile.MobileWebservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    User findUserByToken(String token);
}
