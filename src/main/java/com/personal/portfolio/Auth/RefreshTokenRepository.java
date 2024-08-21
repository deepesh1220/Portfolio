package com.personal.portfolio.Auth;

import com.personal.portfolio.Model.RefreshToken;
import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByToken(String token);


    void deleteByUser(Users user);

    Optional<RefreshToken> findByToken(String token);
}
