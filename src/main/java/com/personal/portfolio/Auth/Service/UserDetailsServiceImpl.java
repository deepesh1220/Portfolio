package com.personal.portfolio.Auth.Service;



import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails)userRepository.findByUsername(username);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

}
