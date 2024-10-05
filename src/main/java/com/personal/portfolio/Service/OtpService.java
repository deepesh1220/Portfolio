package com.personal.portfolio.Service;

import com.personal.portfolio.Model.Users;
import org.springframework.stereotype.Service;

@Service
public interface OtpService {
    String generateOtp(String username);

    boolean verifyOtp(String username, String otp);


    boolean updatePassword(String username, String newPassword);
}
