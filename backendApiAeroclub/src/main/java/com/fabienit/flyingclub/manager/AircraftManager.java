
package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.Aircraft;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AircraftManager {

    List<Aircraft> findAll();
    Optional<Aircraft> findById(int id);
    Aircraft save(Aircraft aircraft);
    Aircraft deleteById(int id);
    List<Aircraft> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate);
    Aircraft getAircraftByReservationId(int id);
    List<Aircraft> findAllByIsAvailableTrue();

    boolean existsAircraftById(int id);
}

