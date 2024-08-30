package com.personal.portfolio.Controller;

import com.personal.portfolio.Auth.Security.Jwt.JwtUtil;
import com.personal.portfolio.Auth.Service.UserDetailsServiceImpl;
import com.personal.portfolio.Dto.UserDTO;
import com.personal.portfolio.Auth.Service.RefreshTokenService;
import com.personal.portfolio.Response.BaseResponse;
import com.personal.portfolio.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api")
public class UserController extends BaseController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsersService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user")
    public ResponseEntity<BaseResponse<UserDTO>> getUserDetails(Authentication authentication) {
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDTO userDTO = userDetailsService.getUserDto(userDetails);
            return withNewAccessToken(
                    ResponseEntity.ok(userDTO),
                    authentication,
                    "User details retrieved successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to retrieve user details: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Error occurred while retrieving user details"
            );
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<BaseResponse<UserDTO>> getUserById(@PathVariable long id, Authentication authentication) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(userDTO),
                    authentication,
                    "User retrieved successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to retrieve user by ID: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null),
                    authentication,
                    "Error occurred while retrieving user by ID"
            );
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<BaseResponse<String>> deleteUserById(@PathVariable long id, Authentication authentication) {
        try {
            String responseMessage = userService.deleteUserById(id);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.OK).body(responseMessage),
                    authentication,
                    "User deleted successfully"
            );
        } catch (Exception e) {
            logger.error("Failed to delete user: {}", e.getMessage(), e);
            return withNewAccessToken(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user"),
                    authentication,
                    "Error occurred while deleting user"
            );
        }
    }

}
