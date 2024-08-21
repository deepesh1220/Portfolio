package com.personal.portfolio.Controller;

import com.personal.portfolio.Auth.Service.UserDetailsServiceImpl;
import com.personal.portfolio.Dto.UserDTO;
import com.personal.portfolio.Auth.Service.RefreshTokenService;
import com.personal.portfolio.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsersService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDTO userDTO = userDetailsService.getUserDto(userDetails);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable long id){
        return new ResponseEntity(userService.getUserById(id), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUserById(@PathVariable long id){
        return new ResponseEntity(userService.deleteUserById(id), HttpStatus.ACCEPTED);
    }



}
