package com.personal.portfolio.Controller;

import com.personal.portfolio.Model.Users;
import com.personal.portfolio.Service.impl.UserServiceImpl;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceimpl;


}
