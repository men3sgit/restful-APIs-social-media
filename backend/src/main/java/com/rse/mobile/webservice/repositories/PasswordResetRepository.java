package com.rse.mobile.webservice.repositories;

import com.rse.mobile.webservice.entities.tokens.PasswordResetToken;
import com.rse.mobile.webservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);

    User findUserByToken(String token);
}
