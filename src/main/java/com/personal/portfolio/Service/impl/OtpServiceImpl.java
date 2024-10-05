package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.UserDTO;
import com.personal.portfolio.Mapper.UserMapper;
import com.personal.portfolio.Model.OtpVerification;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.OtpVerificationRepository;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.OtpService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    OtpVerificationRepository otpVerificationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public String generateOtp(String username) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Generate random 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Set expiration time for the OTP (e.g., 1 minutes)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Create OtpVerification entity
        OtpVerification otpVerification = new OtpVerification(otp, expirationTime, user);
        otpVerificationRepository.save(otpVerification);

        // Here, you can add logic to send the OTP to the user via email or SMS
        System.out.println(otp);

        return otp;
    }

    public boolean verifyOtp(String username, String otp) {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<OtpVerification> otpVerificationOptional = otpVerificationRepository.findByOtpAndUser(otp, user);

        if (otpVerificationOptional.isEmpty()) {
            return false; // OTP not found or invalid
        }

        OtpVerification otpVerification = otpVerificationOptional.get();

        if (otpVerification.getIsVerified() || otpVerification.getExpirationTime().isBefore(LocalDateTime.now())) {
            return false; // OTP already used or expired
        }

        // Mark the OTP as verified
        otpVerification.setIsVerified(true);
        otpVerificationRepository.save(otpVerification);

        return true;
    }



//    public void clearOtpsForUser(Users user) {
//        otpVerificationRepository.deleteByUser(user);
//    }



    // Method to update the user's password after OTP verification
    @Transactional
    public boolean updatePassword(String username, String newPassword) {
        // Fetch the user entity from the database using the username
        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Verify the user has a verified OTP before allowing the password reset
        Optional<OtpVerification> otpVerificationOptional = otpVerificationRepository.findTopByUserAndIsVerifiedTrue(user.getUserId());

        // Check if OTP is found and is verified
        if (otpVerificationOptional.isEmpty()) {
            return false; // No verified OTP found
        }
        System.out.println("otp verified");

        // Encrypt and update the new password
        user.setPassword(passwordEncoder.encode(newPassword));
        System.out.println("Password is Update");
        userRepository.save(user); // Save the updated user entity
        System.out.println("New Password is updated");
        // Clear OTPs for the user once the password is updated
        otpVerificationRepository.deleteByUser(user);
        System.out.println("Deleted otp");
//        remove OTPs for user

        return true;
    }
}
