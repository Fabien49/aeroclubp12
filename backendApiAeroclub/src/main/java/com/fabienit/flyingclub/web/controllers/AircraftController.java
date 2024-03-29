package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.web.exceptions.EntityAlreadyExistsException;
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

/**
 * AircraftController
 * RestController, handle client request and provide entity Aircraft data
 */
@RestController
@Validated
public class AircraftController {
    private final AircraftManager aircraftManager;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AircraftController(AircraftManager aircraftManager) {
        this.aircraftManager = aircraftManager;
    }

    @GetMapping(value = "/aircrafts")
    public List<Aircraft> getAircrafts() {

        logger.info("Providing aircraft resource from database: all aircraft list");

        return aircraftManager.findAll();
    }


    @GetMapping(value = "/aircraftsAvailable")
    public List<Aircraft> getAircraftsAvailable() {

        logger.info("Providing aircraft resource from database: all aircraft list");

        return aircraftManager.findAllByIsAvailableTrue();
    }

    @GetMapping(value = "/aircrafts/{id}")
    public Optional<Aircraft> getAircraftById(@PathVariable int id) {

        logger.info("Providing aircraft resource from database: aircraft id: " + id);

        Optional<Aircraft> aircraft = aircraftManager.findById(id);

        if(!aircraft.isPresent()) throw new RessourceNotFoundException("the aircraft entity doesn't exists, id: " + id);

        return aircraft;
    }

    @GetMapping(value = "/aircrafts/reservation/{id}")
    public Aircraft getAircraftByReservationId(@PathVariable int id){

        return aircraftManager.getAircraftByReservationId(id);
    }

    @PostMapping(value = "/aircrafts")
    public ResponseEntity<Void> addAircraft(@Valid @RequestBody Aircraft aircraft) {
     
        logger.info("Adding new aircraft in database");
        
        if(aircraftManager.existsAircraftById(aircraft.getId()))
            throw new EntityAlreadyExistsException("The aircraft entity already exists , id: " + aircraft.getId());

        Aircraft aircraftAdded = aircraftManager.save(aircraft);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aircraftAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/aircrafts/{id}")
    public ResponseEntity<Void> updateAircraft(@PathVariable @Min(value = 1) Integer id, @Valid @RequestBody Aircraft aircraftDetails) {

        logger.info("Updating aircraft in database, id: " + id);

        try {
            aircraftManager.findById(aircraftDetails.getId());
        } catch (NoSuchElementException e) {
            logger.debug("The requested aircraft entity doesn't exist, id: " + aircraftDetails.getId());
            throw new RessourceNotFoundException("The requested aircraft entity doesn't exist, id: " + aircraftDetails.getId());
        }
        
        aircraftManager.save(aircraftDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/aircrafts/{id}")
    public void deleteAircraft(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting aircraft from database: id : " + id);

        try {
            aircraftManager.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("the aircraft entity doesn't exists, id : " + id);
            throw new RessourceNotFoundException("the aircraft entity doesn't exists, id: " + id);
        }
    }

    @GetMapping(value = "/updateAircraftsAvailable")
    public List<Aircraft> getAvailableAircraftsBetweenDates(@RequestParam("borrowingDate") LocalDate borrowingDate,
                                                            @RequestParam("returnDate") LocalDate returnDate) {

        logger.info("Providing aircraft resource from database: available between dates aircraft list");

        return aircraftManager.getAvailableAircraftsBetweenDates(borrowingDate, returnDate);
    }

}