package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findByNameAndCategory(String name, String category);

    Optional<Skill> findByName(String name);
}
