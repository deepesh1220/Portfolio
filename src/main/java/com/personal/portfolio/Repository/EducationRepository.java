package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.Education;
import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findAllByUser(Users users);
}
