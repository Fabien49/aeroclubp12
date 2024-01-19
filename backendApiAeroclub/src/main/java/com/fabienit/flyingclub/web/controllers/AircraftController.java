package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.manager.UtilsManager;
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
 * 
 * RestController, handle client request and provide entity Aircraft data
 */
@RestController
@Validated
public class AircraftController {
    private final AircraftDao aircraftDao;
    private final AircraftManager aircraftManager;
    private final UtilsManager utilsManager;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public AircraftController(AircraftDao aircraftDao, AircraftManager aircraftManager, UtilsManager utilsManager) {
        this.aircraftDao = aircraftDao;
        this.aircraftManager = aircraftManager;
        this.utilsManager = utilsManager;
    }

    @GetMapping(value = "/aircrafts")
    public List<Aircraft> getAircrafts(@RequestParam(required = false) String query) {

        logger.info("Providing aircraft resource from database: all aircraft list");
        List<Aircraft> aircrafts = aircraftDao.findAll();
         
        if (query == null) return aircrafts;

        // Split query
        logger.debug("Splitting query, query: " + query);
        String[] splitedQueries = utilsManager.splitQueryString(query);

        List<Aircraft> searchResultAircrafts = new ArrayList<Aircraft>();

        //Match aircraft with queries
        for (String splitedQuery : splitedQueries) {
            for (Aircraft aircraft : aircrafts) {
                if (aircraft.getMark().toLowerCase().contains(splitedQuery.toLowerCase())
                        || aircraft.getPower().toLowerCase().contains(splitedQuery.toLowerCase())) {
                    searchResultAircrafts.add(aircraft);
                }
            }
        }

        // Create new list without duplicates aircraft
        List<Aircraft> searchResultAircraftsWithoutDuplicates = new ArrayList<>(new HashSet<>(searchResultAircrafts));

        return searchResultAircraftsWithoutDuplicates;
    }

    @GetMapping(value = "/aircraftsAvailable")
    public List<Aircraft> getAircraftsAvailable(/*@RequestParam(required = false) String query*/) {

        logger.info("Providing aircraft resource from database: all aircraft list");
        List<Aircraft> availableAircraft = aircraftDao.findAllByIsAvailableTrue();
        /*
        if (query == null) return aircrafts;

        // Split query
        logger.debug("Splitting query, query: " + query);
        String[] splitedQueries = utilsManager.splitQueryString(query);

        List<Aircraft> searchResultAircrafts = new ArrayList<Aircraft>();

        //Match aircraft with queries
        for (String splitedQuery : splitedQueries) {
            for (Aircraft aircraft : aircrafts) {
                if (aircraft.getMark().toLowerCase().contains(splitedQuery.toLowerCase())
                        || aircraft.getPower().toLowerCase().contains(splitedQuery.toLowerCase())) {
                    searchResultAircrafts.add(aircraft);
                }
            }
        }

        // Create new list without duplicates aircraft
        List<Aircraft> searchResultAircraftsWithoutDuplicates = new ArrayList<>(new HashSet<>(searchResultAircrafts));
        */
        return availableAircraft;
    }

    @GetMapping(value = "/aircrafts/{id}")
    public Optional<Aircraft> getAircraftById(@PathVariable int id) {

        logger.info("Providing aircraft resource from database: aircraft id: " + id);

        Optional<Aircraft> aircraft = aircraftDao.findById(id);

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
        
        if(aircraftDao.existsAircraftById(aircraft.getId()))
            throw new EntityAlreadyExistsException("The aircraft entity already exists , id: " + aircraft.getId());

        Aircraft aircraftAdded = aircraftDao.save(aircraft);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aircraftAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/aircrafts/{id}")
    public ResponseEntity<Void> updateAircraft(@PathVariable @Min(value = 1) Integer id, @Valid @RequestBody Aircraft aircraftDetails) {

        logger.info("Updating aircraft in database, id: " + id);

        try {
            aircraftDao.findById(aircraftDetails.getId());
        } catch (NoSuchElementException e) {
            logger.debug("The requested aircraft entity doesn't exist, id: " + aircraftDetails.getId());
            throw new RessourceNotFoundException("The requested aircraft entity doesn't exist, id: " + aircraftDetails.getId());
        }
        
        aircraftDao.save(aircraftDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/aircrafts/{id}")
    public void deleteAircraft(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting aircraft from database: id: "+ id);

        try {
            aircraftDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("the aircraft entity doesn't exists, id: " + id);
            throw new RessourceNotFoundException("the aircraft entity doesn't exists, id: " + id);
        }
    }

    @GetMapping(value = "/updateAircraftsAvailable")
    public List<Aircraft> getAvailableAircraftsBetweenDates(@RequestParam("borrowingDate") LocalDate borrowingDate,
                                                            @RequestParam("returnDate") LocalDate returnDate) {

        logger.info("Providing aircraft resource from database: available between dates aircraft list");
        System.out.println("les deux dates sont : " + borrowingDate + "ddddd" + returnDate);
        return aircraftManager.getAvailableAircraftsBetweenDates(borrowingDate, returnDate);
    }

}