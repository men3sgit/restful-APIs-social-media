package com.rse.mobile.MobileWebservice.model.token;// PasswordResetToken.java

import com.rse.mobile.MobileWebservice.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ResetPasswordToken implements Token {
    private static final int EXPIRATION_TIME_IN_MINUTES = 15; // 15 minutes expired
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(
            nullable = false,
            unique = true
    )
    private String token;
    @OneToOne(
            targetEntity = User.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;
    @Column(
            nullable = false
    )
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public ResetPasswordToken(User user) {
        this.createdAt = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expiredAt = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_IN_MINUTES);
    }

    @Override
    public String generateToken() {
        return token;
    }
}
