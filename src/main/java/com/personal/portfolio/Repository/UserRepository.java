package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface

UserRepository extends JpaRepository<Users, Long> {
}
