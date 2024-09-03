package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.Certification;
import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {


    List<Certification> findAllByUser(Users user);
}
