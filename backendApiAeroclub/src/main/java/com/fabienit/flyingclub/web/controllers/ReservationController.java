package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.manager.ReservationManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import com.fabienit.flyingclub.web.exceptions.FunctionnalException;
import com.fabienit.flyingclub.web.exceptions.RessourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.*;

@RestController
@Validated
public class ReservationController {

    @Autowired
    private ReservationManager reservationManager;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/reservations")
    public List<Reservation> getReservations() {

        logger.info("Providing reservation resource from database: all reservation list");
        List<Reservation> reservationList = reservationManager.findAll();

        return reservationList;
    }

    @GetMapping(value = "/reservations/{id}")
    public Optional<Reservation> getReservationById(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing reservation resource from database: reservation id: " + id);
        Optional<Reservation> reservation = reservationManager.findById(id);

        if (!reservation.isPresent())
            throw new RessourceNotFoundException("The reservation entity doesn't exist, id: " + id);

        return reservation;
    }

    @GetMapping(value = "/reservations/user/{id}")
    public List<Reservation> getReservationByRegisteredUser(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing reservation list for registered user from database: user id: " + id);
        List<Reservation> reservationList = reservationManager.findAllByRegisteredUser(id);
        return reservationList;
    }

    @PostMapping(value = "/reservations")
    public ResponseEntity<Void> addReservation(@RequestBody Reservation reservation) {

        logger.info("Adding new reservation in database");
        Reservation reservationAdded;
        try {
            reservationAdded = reservationManager.save(reservation);
        } catch (FunctionnalException e) {
            logger.info(e.getLocalizedMessage());
            return ResponseEntity.badRequest().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservation.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/reservations/{id}")
    public ResponseEntity<Void> updateReservation(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Reservation reservationDetails) throws FunctionnalException {

        logger.info("Updating reservation in database, id: " + id);

        try {
            reservationManager.findById(reservationDetails.getId()).get();
        }catch (NoSuchElementException e){
            logger.debug("The requested reservation entity doesn't exist, id: " + reservationDetails.getId());
            throw new RessourceNotFoundException("The requested reservation entity doesn't exist, id: " + reservationDetails.getId());
        }

        reservationManager.save(reservationDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/reservations/{id}")
    public void deleteReservation(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting reservation from database: id: "+ id);

        try {
            reservationManager.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            logger.debug("The reservation entity doesn't exist, id: " + id);
            throw new RessourceNotFoundException("The reservation entity doesn't exist, id: " + id);
        }

    }


    @PostMapping(value= "reservations/notifications/update")
    public ResponseEntity<Void> updateReservationAfterNotification(@Valid @RequestBody Reservation reservation) throws FunctionnalException {
        logger.info("Update reservation after notfications is sent, reservation id: " + reservation.getId());
        Reservation reservationToUpdate = reservationManager.updateReservationAfterNotification(reservation);
        reservationManager.save(reservationToUpdate);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/aircraftsAvailableToday")
    public List<Aircraft> getAvailableAircraftsToday() {

        logger.info("Providing aircraft resource from database: available today aircraft list");
        return reservationManager.getAvailableAircraftsToday();
    }

    @GetMapping(value = "/updateAircraftsAvailable")
    public List<Aircraft> getAvailableAircraftsBetweenDates(Date startDate, Date endDate) {

        logger.info("Providing aircraft resource from database: available between dates aircraft list");
        return reservationManager.getAvailableAircraftsBetweenDates(startDate, endDate);
    }

}