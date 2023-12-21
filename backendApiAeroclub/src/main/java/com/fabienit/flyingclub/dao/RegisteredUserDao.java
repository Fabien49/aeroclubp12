package com.fabienit.flyingclub.dao;

import com.fabienit.flyingclub.model.beans.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserDao
 */
@Repository
public interface RegisteredUserDao extends JpaRepository<RegisteredUser, Integer> {

    RegisteredUser findByEmail(String email);
}