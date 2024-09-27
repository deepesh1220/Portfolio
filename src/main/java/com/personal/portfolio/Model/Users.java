package com.personal.portfolio.Model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    private String username;
    private String name;
    private String email;
    private String password;
    private String bio;
    private String profilePicture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Education> educationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Experience> experienceList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Certification> certifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OtpVerification> otpVerificationList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}