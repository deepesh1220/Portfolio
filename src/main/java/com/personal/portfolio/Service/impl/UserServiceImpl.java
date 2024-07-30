package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.inter.UserService;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    public List getUsers() {
        return userRepository.findAll()   }
}
