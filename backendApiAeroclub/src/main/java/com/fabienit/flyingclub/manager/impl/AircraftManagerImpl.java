
package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.dao.ReservationDao;
import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AircraftManagerImpl implements AircraftManager {

    private final AircraftDao aircraftDao;
    private final ReservationDao reservationDao;

    public AircraftManagerImpl(AircraftDao aircraftDao, ReservationDao reservationDao) {
        this.aircraftDao = aircraftDao;
        this.reservationDao = reservationDao;
    }



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
    public List<Aircraft> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate) {


        System.out.println(startDate);
        System.out.println(endDate);
        List<Aircraft> availableAircrafts = aircraftDao.findAllByIsAvailableTrue();
        System.out.println("Available Aircrafts:");
        availableAircrafts.forEach(aircraft -> System.out.println(aircraft.toString()));
        List<Reservation> testList = reservationDao.findAll();
        System.out.println("\ntoutes les Reservations:");
        testList.forEach(reservation -> System.out.println(reservation.toString()));
        List<Reservation> existingReservations = reservationDao.findAllReservationBetweenTwoDates(startDate, endDate);
        System.out.println("\nExisting Reservations:");
        existingReservations.forEach(reservation -> System.out.println(reservation.toString()));

        Set<Integer> reservedAircraftIds = existingReservations.stream()
                .map(reservation -> reservation.getAircraft().getId())
                .collect(Collectors.toSet());



        return availableAircrafts.stream()
                .filter(aircraft -> !reservedAircraftIds.contains(aircraft.getId()))
                .collect(Collectors.toList());


    }

    @Override
    public Aircraft deleteById(int id) {
        aircraftDao.deleteById(id);
        return null;
    }
}