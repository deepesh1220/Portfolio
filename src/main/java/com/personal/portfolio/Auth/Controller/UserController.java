package com.personal.portfolio.Auth.Controller;

import com.personal.portfolio.Auth.Service.UserDetailsServiceImpl;
import com.personal.portfolio.Dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDTO userDTO = userDetailsService.getUserDto(userDetails);
        return ResponseEntity.ok(userDTO);
    }

}
