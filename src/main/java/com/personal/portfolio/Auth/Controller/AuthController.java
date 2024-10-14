package com.personal.portfolio.Auth.Controller;


import com.personal.portfolio.Auth.Request.AuthRequest;
import com.personal.portfolio.Auth.Response.AuthResponse;
import com.personal.portfolio.Auth.Security.Jwt.JwtUtil;
import com.personal.portfolio.Model.RefreshToken;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Auth.Service.RefreshTokenService;
import com.personal.portfolio.Service.EmailService;
import com.personal.portfolio.Service.OtpService;
import com.personal.portfolio.Service.impl.OtpServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@RestController
@RequestMapping("/public/api")
public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final UserDetailsService userDetailsService;
    @Autowired
    private final JwtUtil jwtUtil;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    OtpService otpService;

    @Autowired
    EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }


    @Operation(summary = "Authenticate user", description = "Authenticate the user and return an access token and refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Incorrect username or password"),
            @ApiResponse(responseCode = "500", description = "An error occurred during authentication")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Check if the account is enabled, not locked, etc.
            if (!userDetails.isAccountNonLocked()) {
                return ResponseEntity.status(HttpStatus.LOCKED).body("Account is locked");
            }
            if (!userDetails.isAccountNonExpired()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account is expired");
            }
            if (!userDetails.isCredentialsNonExpired()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credentials are expired");
            }
            if (!userDetails.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account is not enabled");
            }

            // Generate Access And Refresh token if everything is fine
            final String accessToken = jwtUtil.generateAccessToken(userDetails);
            final RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));

        } catch (BadCredentialsException e) {
            logger.error("Authentication failed: Incorrect username or password", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during authentication");
        }
    }




    @Operation(summary = "Register new user", description = "Registers a new user in the system.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Registration successful"),
//            @ApiResponse(responseCode = "400", description = "Username already exists")
//    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        if (userRepository.findByUsername(user.getUsername()) != null)
            return ResponseEntity.badRequest().body("Username already exists");

        // You may want to hash the password before saving to the database
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        String body = "Dear " + user.getName() + ",\\n\\nYour Registration is successful.";
        String subject = "Portfolio website registration";
        String email = user.getEmail();
        emailService.sendEmail(email,subject,body);

        return ResponseEntity.ok("Registration successful");
    }


    @Operation(summary = "Refresh access token", description = "Generates a new access token using the provided refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "403", description = "Refresh token is expired")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody AuthRequest authRequest){
        String requestRefreshToken = authRequest.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshToken -> {
                    if(refreshToken.getExpiryDate().before(new Date())){
                        refreshTokenService.deleteByToken(refreshToken.getToken());
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token is expired");
                    }

                    // Load the user by username associated with the refresh token
                    Users user = refreshToken.getUser();
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

                    String accessToken = jwtUtil.generateAccessToken(userDetails);
                    return ResponseEntity.ok(new AuthResponse(accessToken, requestRefreshToken));
                })
                .orElseGet(()-> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token"));
    }

    @Operation(summary = "Logout user", description = "Logs out the user by invalidating the provided refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged out successfully")
    })
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody AuthRequest authRequest){
        refreshTokenService.deleteByToken(authRequest.getRefreshToken());
        return ResponseEntity.ok("Logged out successfully");
    }


    @Operation(summary = "Forgot password", description = "Generates an OTP and sends it to the user's registered contact information for password reset.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid username or user not found")
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String username) {
        try {
            String otp = otpService.generateOtp(username);
            System.out.println(otp);
            return ResponseEntity.ok("OTP sent to your registered contact.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Verify OTP", description = "Verifies the OTP provided by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OTP verified successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired OTP")
    })
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        boolean isVerified = otpService.verifyOtp(username, otp);
        if (isVerified) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
        }
    }

    @Operation(summary = "Reset password", description = "Resets the user's password after OTP verification.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successful"),
            @ApiResponse(responseCode = "400", description = "Invalid or unverified OTP"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username, @RequestParam String newPassword) {
        boolean isPasswordUpdated = otpService.updatePassword(username, newPassword);
        System.out.println("Reset Password response "+isPasswordUpdated);
        if (isPasswordUpdated) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP not verified or expired.");
        }
    }

}

