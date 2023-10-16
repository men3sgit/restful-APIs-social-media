package com.rse.mobile.webservice.repositories;

import com.rse.mobile.webservice.entities.tokens.ConfirmationToken;
import com.rse.mobile.webservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationRepository extends JpaRepository<ConfirmationToken,Long> {
    Optional<ConfirmationToken> findByToken(String token);
    @Query(
            "SELECT c.user FROM ConfirmationToken c WHERE c.token = ?1"
    )
    User findUserByToken(String token);
}
