
package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AircraftManagerImpl implements AircraftManager {

    @Autowired
    private AircraftDao aircraftDao;




    @Override
    public List<Aircraft> findAll() {
        return aircraftDao.findAll();
    }

    @Override
    public Optional<Aircraft> findById(int id) {
        return aircraftDao.findById(id);
    }

    @Override
    public Aircraft save(Aircraft aircraft) {
        return aircraftDao.save(aircraft);
    }

    @Override
    public List<Aircraft> checkIfAircraftIsAvailable() {

       List<Aircraft> aircraftAvailableList = new ArrayList<>();
       List<Aircraft> aircraftList = aircraftDao.findAll();
        System.out.println("La première liste d'avions est là : " + aircraftList);
        for (Aircraft aircraft: aircraftList
             ) {
            if (aircraft.getAvailable()){
               aircraftAvailableList.add(aircraft);
            }
        }
        System.out.println("La deuxième liste d'avions est là : " + aircraftAvailableList);

        return aircraftAvailableList;
    }

    @Override
    public Aircraft deleteById(int id) {
        aircraftDao.deleteById(id);
        return null;
    }
}