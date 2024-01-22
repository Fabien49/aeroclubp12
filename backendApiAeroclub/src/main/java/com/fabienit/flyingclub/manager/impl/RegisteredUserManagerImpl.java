package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.RegisteredUserDao;
import com.fabienit.flyingclub.manager.RegisteredUserManager;
import com.fabienit.flyingclub.model.beans.RegisteredUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserManagerImpl implements RegisteredUserManager {

    private final RegisteredUserDao registeredUserDao;

    public RegisteredUserManagerImpl(RegisteredUserDao registeredUserDao) {
        this.registeredUserDao = registeredUserDao;
    }

    @Override
    public List<RegisteredUser> findAll() {
        return registeredUserDao.findAll();
    }

    @Override
    public Optional<RegisteredUser> findById(int id) {
        return registeredUserDao.findById(id);
    }

    @Override
    public RegisteredUser save(RegisteredUser registeredUser) {
        return registeredUserDao.save(registeredUser);
    }

    @Override
    public RegisteredUser deleteById(int id) {
        registeredUserDao.deleteById(id);
        return null;
    }

    @Override
    public RegisteredUser findByEmail(String email) {
        return registeredUserDao.findByEmail(email);
    }
}
