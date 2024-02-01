
package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.dao.ReservationDao;
import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
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
    public List<Aircraft> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate) {

        List<Aircraft> availableAircrafts = aircraftDao.findAllByIsAvailableTrue();
        List<Reservation> existingReservations = reservationDao.findAllReservationForAircraftReservation(startDate, endDate);

        Set<Integer> reservedAircraftIds = existingReservations.stream()
                .map(reservation -> reservation.getAircraft().getId())
                .collect(Collectors.toSet());

        return availableAircrafts.stream()
                .filter(aircraft -> !reservedAircraftIds.contains(aircraft.getId()))
                .collect(Collectors.toList());

    }

    @Override
    public Aircraft getAircraftByReservationId(int id) {
        return aircraftDao.findAircraftByReservationId(id);
    }

    @Override
    public List<Aircraft> findAllByIsAvailableTrue() {
        return aircraftDao.findAllByIsAvailableTrue();
    }

    @Override
    public boolean existsAircraftById(int id) {
        return aircraftDao.existsAircraftById(id);
    }

    @Override
    public Aircraft deleteById(int id) {
        aircraftDao.deleteById(id);
        return null;
    }
}