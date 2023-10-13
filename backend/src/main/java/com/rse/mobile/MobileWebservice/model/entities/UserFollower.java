package com.rse.mobile.MobileWebservice.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_followers")
public class UserFollower {
    @SequenceGenerator(
            name = "user_followers_sequence",
            sequenceName = "user_followers_sequence",
            initialValue = 989,
            allocationSize = 3
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_followers_sequence"
    )
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    public UserFollower(User user, User follower) {
        this.user = user;
        this.follower = follower;
    }
}
