package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.FlyingClubDao;
import com.fabienit.flyingclub.manager.FlyingClubManager;
import com.fabienit.flyingclub.model.beans.FlyingClub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlyingClubManagerImpl implements FlyingClubManager {

    @Autowired
    private FlyingClubDao flyingClubDao;

    @Override
    public List<FlyingClub> findAll() {
        return flyingClubDao.findAll();
    }

    @Override
    public Optional<FlyingClub> findById(int id) {
        return flyingClubDao.findById(id);
    }

    @Override
    public FlyingClub save(FlyingClub library) {
        return flyingClubDao.save(library);
    }

    @Override
    public FlyingClub deleteById(int id) {
        flyingClubDao.deleteById(id);
        return null;
    }
}
