package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.Experience;
import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience,Long> {
    List<Experience> findAllByUser(Users user);
}
