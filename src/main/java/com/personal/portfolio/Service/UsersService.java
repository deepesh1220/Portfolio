package com.personal.portfolio.Service;

import com.personal.portfolio.Dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UsersService {
    UserDTO getUserById(long id);

    String deleteUserById(long id);
}
