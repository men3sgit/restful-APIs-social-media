package com.rse.mobile.webservice.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "_user")
@NoArgsConstructor
@Data
public class User implements UserDetails {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            initialValue = 1000,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Id
    private Long id;
    @Column(
            nullable = false,
            unique = true
    )
    private String email;
    @Column(
            nullable = false
    )
    private String password;
    private String fullName;
    @Column(
            nullable = false
    )
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Boolean enabled;
    private Boolean locked;

    // Bio and other user details
    private String avatar;
    private String bio;

    // Followers
    @ManyToMany
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers = new HashSet<>();

    // Following
    @ManyToMany(mappedBy = "followers")
    private Set<User> following = new HashSet<>();



    public User(String email, String password, String fullName, LocalDate dob, Role role) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.dob = dob;
        this.role = role;
        this.enabled = false;
        this.locked = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority granted = new SimpleGrantedAuthority(role.toString());
        return Collections.singletonList(granted);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }



}
