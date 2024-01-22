package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.manager.ReservationManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import com.fabienit.flyingclub.web.exceptions.FunctionnalException;
import com.fabienit.flyingclub.web.exceptions.RessourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@RestController
@Validated
public class ReservationController {

    private final ReservationManager reservationManager;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public ReservationController(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }

    @GetMapping(value = "/reservations")
    public List<Reservation> getReservations() {

        logger.info("Providing reservation resource from database: all reservation list");

        return reservationManager.findAll();
    }

    @GetMapping(value = "/reservations/{id}")
    public Reservation getReservationById(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing reservation resource from database: reservation id: " + id);

        Reservation reservation = reservationManager.findById(id);

        if (reservation == null) {
            throw new RessourceNotFoundException("The reservation entity doesn't exist, id: " + id);
        }

        return reservation;
    }

    @GetMapping(value = "/reservations/user/{id}")
    public List<Reservation> getReservationByRegisteredUser(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing reservation list for registered user from database: user id: " + id);

        return reservationManager.findAllByRegisteredUser(id);
    }

    @PostMapping(value = "/reservations")
    public ResponseEntity<Void> addReservation(@RequestBody Reservation reservation) {

        logger.info("Adding new reservation in database");
        try {
        Reservation  reservationAdded = reservationManager.save(reservation);
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

    @PutMapping(value = "/canceledReservation/{id}")
    public ResponseEntity<Void> canceledReservation(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Reservation reservationDetails) throws FunctionnalException {

        logger.info("Canceling reservation in database, id: " + id);

        try {
            reservationManager.findById(reservationDetails.getId());
        }catch (NoSuchElementException e){
            logger.debug("The requested reservation entity doesn't exist, id: " + reservationDetails.getId());
            throw new RessourceNotFoundException("The requested reservation entity doesn't exist, id: " + reservationDetails.getId());
        }

        reservationManager.save(reservationDetails);

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/reservations/{id}")
    public ResponseEntity<Void> updateReservation(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Reservation reservationDetails) throws FunctionnalException {

        logger.info("Updating reservation in database, id: " + id);

        try {
            reservationManager.findById(reservationDetails.getId());
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


    @PutMapping(value= "saveUpdateAircraftReservation/{id}")
    public ResponseEntity<Void> updateAircraftReservation(@PathVariable int id, @Valid @RequestBody Reservation reservation) throws FunctionnalException {

        logger.info("Updating reservation in database, id: " + id);

        try {
            reservationManager.findById(id);
        } catch (NoSuchElementException e) {
            logger.debug("The requested reservation entity doesn't exist, id: " + id);
            throw new RessourceNotFoundException("The requested reservation entity doesn't exist, id: " + id);
        }


        System.out.println("L'avion que l'on veut mettre à jour dans la réservation avec l'id : " + reservation.getId() + "est : " + reservation.getAircraft());
        reservationManager.updateAircraftReservation(reservation);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/aircraftsAvailableToday")
    public List<Aircraft> getAvailableAircraftsToday() {

        logger.info("Providing aircraft resource from database: available today aircraft list");

        return reservationManager.getAvailableAircraftsToday();
    }

    @GetMapping(value = "/existingReservation")
    public boolean getReservationByIdAndDate(int id, LocalDate borrowingDate, LocalDate returnDate){

        logger.info("Providing reservation resource from database: existing reservation by id and dates");

        return reservationManager.existsReservationByIdAndDate(id, borrowingDate, returnDate);
    }



}