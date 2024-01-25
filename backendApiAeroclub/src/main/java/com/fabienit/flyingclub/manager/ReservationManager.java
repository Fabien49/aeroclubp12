package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import com.fabienit.flyingclub.web.exceptions.FunctionnalException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationManager {


    @Transactional
    Reservation deleteById(int id);

    Reservation save(Reservation reservation) throws FunctionnalException;

    Reservation updateAircraftReservation(Reservation reservationBean) throws FunctionnalException;

    List<Reservation> findAll();

    Reservation findById(int id);

    List<Reservation> findAllByRegisteredUser(int registeredUser);

    boolean existsReservationByIdAndDate(int id, LocalDate startDate, LocalDate endDate);

}
