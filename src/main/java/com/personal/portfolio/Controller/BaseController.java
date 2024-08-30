package com.personal.portfolio.Controller;

import com.personal.portfolio.Auth.Security.Jwt.JwtUtil;
import com.personal.portfolio.Auth.Service.RefreshTokenService;
import com.personal.portfolio.Auth.Service.UserDetailsServiceImpl;
import com.personal.portfolio.Response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Do not annotate this class with @Component or @Controller
public abstract class BaseController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected <T> ResponseEntity<BaseResponse<T>> withNewAccessToken(ResponseEntity<T> response, Authentication authentication, String message) {
        if (authentication == null) {
            logger.error("Authentication object is null");
            throw new IllegalStateException("Authentication object is null");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            logger.error("Principal is not an instance of UserDetails: {}", principal != null ? principal.getClass().getName() : "null");
            throw new IllegalStateException("Unexpected principal type: " + (principal != null ? principal.getClass().getName() : "null"));
        }

        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        if (userDetailsService == null || jwtUtil == null) {
            logger.error("UserDetailsService or JwtUtil is not initialized");
            throw new IllegalStateException("UserDetailsService or JwtUtil is not initialized");
        }

        String newAccessToken;
        try {
            newAccessToken = jwtUtil.generateAccessToken(userDetailsService.loadUserByUsername(username));
        } catch (Exception e) {
            logger.error("Error generating access token", e);
            throw new RuntimeException("Error generating access token", e);
        }

        BaseResponse<T> baseResponse = new BaseResponse<>(
                response.getStatusCode().is2xxSuccessful() ? "success" : "error",
                newAccessToken,
                message,
                response.getBody()
        );

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(baseResponse);
    }
}
