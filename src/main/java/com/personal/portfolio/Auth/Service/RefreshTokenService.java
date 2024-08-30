package com.personal.portfolio.Auth.Service;


import com.personal.portfolio.Model.RefreshToken;
import com.personal.portfolio.Model.Users;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public interface RefreshTokenService {


    RefreshToken createRefreshToken(Users user);

    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);


    void deleteByUser(Users user);
}
