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
/**
 * Abstract base controller providing common functionalities for all controllers.
 * This class contains utility methods to handle common tasks such as generating
 * new access tokens and wrapping responses in a standardized format.
 *
 * Note: Do not annotate this class with @Component or @Controller.
 */
public abstract class BaseController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);


    /**
     * Wraps a given response in a standardized {@link BaseResponse} format and generates a new access token.
     *
     * @param <T> the type of the response body
     * @param response the original response to be wrapped
     * @param authentication the current user's authentication object
     * @param message a custom message to be included in the response
     * @return a {@link ResponseEntity} with the new access token and a standardized response format
     * @throws IllegalStateException if the authentication object is null or not an instance of {@link UserDetails},
     *                               or if required services are not initialized
     * @throws RuntimeException if there is an error generating the access token
     */
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
