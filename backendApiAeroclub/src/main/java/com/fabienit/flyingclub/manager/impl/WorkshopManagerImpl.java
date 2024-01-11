
package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.model.beans.Workshop;
import com.fabienit.flyingclub.dao.WorkshopDao;
import com.fabienit.flyingclub.manager.WorkshopManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopManagerImpl implements WorkshopManager {

    @Autowired
    private WorkshopDao workshopDao;


    @Override
    public List<Workshop> findAll() {
        return workshopDao.findAll();
    }

    @Override
    public Optional<Workshop> findById(int id) {
        return workshopDao.findById(id);
    }

    @Override
    public Workshop save(Workshop workshop) {
        return workshopDao.save(workshop);
    }

    @Override
    public Workshop deleteById(int id) {
        workshopDao.deleteById(id);
        return null;
    }
}