package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.OtpVerification;
import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findByOtpAndUser(String otp, Users user);


    void deleteByUser(Users user);

    // Native query to get the most recent verified OTP
    @Query(value = "SELECT * FROM otp_verification o WHERE o.user_id = :userId AND o.is_verified = true ORDER BY o.expiration_time DESC LIMIT 1", nativeQuery = true)
    Optional<OtpVerification> findTopByUserAndIsVerifiedTrue(@Param("userId") Long userId);

    String findByUserAndIsVerifiedTrue(Users users);


    List<OtpVerification> findByUser(Users user);
}
