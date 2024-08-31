package com.personal.portfolio.Service.impl;

import com.personal.portfolio.Dto.UserDTO;
import com.personal.portfolio.Mapper.UserMapper;
import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Repository.UserRepository;
import com.personal.portfolio.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDTO getUserById(long id) {
        Users users = userRepository.findById(id).get();
        return UserMapper.toDTO(users);
    }

    @Override
    public String deleteUserById(long id) {
        userRepository.deleteById(id);
        return "User has been deleted";
    }
}
