package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.RegisteredUser;
import com.fabienit.biblioapi.dao.RegisteredUserDao;
import com.fabienit.biblioapi.manager.RegisteredUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserManagerImpl implements RegisteredUserManager {

    @Autowired
    private RegisteredUserDao registeredUserDao;

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
    public void deleteById(int id) {
        registeredUserDao.deleteById(id);
    }

    @Override
    public RegisteredUser findByEmail(String email) {
        return registeredUserDao.findByEmail(email);
    }
}
