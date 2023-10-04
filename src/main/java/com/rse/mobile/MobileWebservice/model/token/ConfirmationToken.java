package com.rse.mobile.MobileWebservice.model.token;

import com.rse.mobile.MobileWebservice.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "_confirmation_token")
public class ConfirmationToken implements Token{
    private static final int EXPIRATION_TIME_IN_MINUTES = 15 ; // 15 minutes expired

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            initialValue = 100,
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "confirmation_token_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Column(
            nullable = false
    )
    private String token;
    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;
    @Column(
            nullable = false
    )
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "_user_id"
    )
    private User user;

    public ConfirmationToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiredAt = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES);
        this.user = user;
    }


    @Override
    public String generateToken() {
        return token;
    }
}
