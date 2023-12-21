package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.RegisteredUser;

import java.util.List;
import java.util.Optional;

public interface RegisteredUserManager {

    List<RegisteredUser> findAll();
    Optional<RegisteredUser> findById(int id);
    RegisteredUser save(RegisteredUser registeredUser);
    RegisteredUser deleteById(int id);
    RegisteredUser findByEmail(String email);

}
