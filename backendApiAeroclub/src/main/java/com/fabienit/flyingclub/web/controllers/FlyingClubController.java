package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.dao.FlyingClubDao;
import com.fabienit.flyingclub.model.beans.FlyingClub;
import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.manager.UtilsManager;
import com.fabienit.flyingclub.web.exceptions.EntityAlreadyExistsException;
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

/**
 * FlyingClubController
 * 
 * RestController, handle client request and provide entity Aircraft data
 */
@RestController
@Validated
public class FlyingClubController {

    @Autowired
    private FlyingClubDao flyingClubDao;

    @Autowired
    private AircraftManager aircraftManager;

    @Autowired
    private UtilsManager utilsManager;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/flyingClub")
    public List<FlyingClub> getFlyingClub(@RequestParam(required = false) String query) {

        logger.info("Providing aircraft resource from database: all aircraft list");
        List<FlyingClub> flyingClubs = flyingClubDao.findAll();
        return flyingClubs;


         
/*        if (query == null) return flyingClubs;

        // Split query
        logger.debug("Splitting query, query: " + query);
        String[] splitedQueries = utilsManager.splitQueryString(query);

        List<FlyingClub> searchResultFlyingClubs = new ArrayList<FlyingClub>();

        //Match aircraft with queries
        for (String splitedQuery : splitedQueries) {
            for (FlyingClub flyingClub : flyingClubs) {
                if (flyingClub.getName().toLowerCase().contains(splitedQuery.toLowerCase())
                        || flyingClub.getCity().toLowerCase().contains(splitedQuery.toLowerCase())) {
                    searchResultFlyingClubs.add(flyingClub);
                }
            }
        }

        // Create new list without duplicates aircraft
        List<FlyingClub> searchResultFlyingClubsWithoutDuplicates = new ArrayList<>(new HashSet<>(searchResultFlyingClubs));

        return searchResultFlyingClubsWithoutDuplicates;*/
    }


    @GetMapping(value = "/flyingClub/{id}")
    public Optional<FlyingClub> getFlyingClubById(@PathVariable @Min(value = 1) Integer id) {

        logger.info("Providing aircraft resource from database: aircraft id: " + id);

        Optional<FlyingClub> flyingClub = flyingClubDao.findById(id);

        if(!flyingClub.isPresent()) throw new RessourceNotFoundException("the aircraft entity doesn't exists, id: " + id);

        return flyingClub;
    }

    @PostMapping(value = "/flyingClub")
    public ResponseEntity<Void> addFlyingClub(@Valid @RequestBody FlyingClub flyingClub) {
     
        logger.info("Adding new flyingClub in database");
        
        if(flyingClubDao.existsFlyingClubById(flyingClub.getId()))
            throw new EntityAlreadyExistsException("The flyingClub entity already exists , id: " + flyingClub.getId());

        FlyingClub flyingClubAdded = flyingClubDao.save(flyingClub);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(flyingClubAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/flyingClub/{id}")
    public ResponseEntity<Void> updateFlyingClub(@PathVariable @Min(value = 1) Integer id, @Valid @RequestBody FlyingClub flyingClubDetails) {

        logger.info("Updating aircraft in database, id: " + id);

        try {
            flyingClubDao.findById(flyingClubDetails.getId());
        } catch (NoSuchElementException e) {
            logger.debug("The requested flyingClub entity doesn't exist, id: " + flyingClubDetails.getId());
            throw new RessourceNotFoundException("The requested flyingClub entity doesn't exist, id: " + flyingClubDetails.getId());
        }
        
        flyingClubDao.save(flyingClubDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/flyingClub/{id}")
    public void deleteFlyingClub(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting flyingClub from database: id: "+ id);

        try {
            flyingClubDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("the flyingClub entity doesn't exists, id: " + id);
            throw new RessourceNotFoundException("the flyingClub entity doesn't exists, id: " + id);
        }
    }    

}