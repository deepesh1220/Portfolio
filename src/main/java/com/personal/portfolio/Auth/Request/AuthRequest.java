package com.personal.portfolio.Auth.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String username;
    private String password;
    private String refreshToken; // Add this field for refresh token

}
