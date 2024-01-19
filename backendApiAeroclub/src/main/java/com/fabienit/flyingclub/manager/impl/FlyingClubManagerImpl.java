package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.FlyingClubDao;
import com.fabienit.flyingclub.manager.FlyingClubManager;
import com.fabienit.flyingclub.model.beans.FlyingClub;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlyingClubManagerImpl implements FlyingClubManager {

    private final FlyingClubDao flyingClubDao;

    public FlyingClubManagerImpl(FlyingClubDao flyingClubDao) {
        this.flyingClubDao = flyingClubDao;
    }

    @Override
    public List<FlyingClub> findAll() {
        return flyingClubDao.findAll();
    }

    @Override
    public Optional<FlyingClub> findById(int id) {
        return flyingClubDao.findById(id);
    }

    @Override
    public FlyingClub save(FlyingClub flyingClub) {
        return flyingClubDao.save(flyingClub);
    }

    @Override
    public FlyingClub deleteById(int id) {
        flyingClubDao.deleteById(id);
        return null;
    }
}
