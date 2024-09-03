package com.personal.portfolio.Repository;

import com.personal.portfolio.Model.Contact;
import com.personal.portfolio.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByUser(Users users);
}
