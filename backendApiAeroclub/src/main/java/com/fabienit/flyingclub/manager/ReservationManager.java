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


    void relatedReservationUpdate(Integer aircraftId, Integer userId);

    @Transactional
    Reservation deleteById(int id);

    Reservation save(Reservation reservation) throws FunctionnalException;

    Reservation updateReservationAfterNotification(Reservation reservationBean) throws FunctionnalException;

    List<Reservation> findAll();

    Optional<Reservation> findById(int id);

    List<Reservation> findAllByBorrowwingDate(LocalDate borrowingDate);

    List<Reservation> findAllByReturnDate(LocalDate returnDate);

    List<Reservation> findAllByRegisteredUser(int registeredUser);

    boolean isAircraftAvailable(LocalDate date, List<Reservation> reservations);

    List<Aircraft> getAvailableAircraftsToday();

    Reservation canceledReservationAfterNotification(Reservation reservation) throws FunctionnalException;
}
