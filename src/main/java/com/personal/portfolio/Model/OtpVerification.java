package com.personal.portfolio.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    // Constructors, Getters, and Setter

    public OtpVerification(String otp, LocalDateTime expirationTime, Users user) {
        this.otp = otp;
        this.expirationTime = expirationTime;
        this.user = user;
        this.isVerified = false;
    }
}
