package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.dao.FlyingClubDao;
import com.fabienit.flyingclub.manager.FlyingClubManager;
import com.fabienit.flyingclub.model.beans.FlyingClub;
import com.fabienit.flyingclub.manager.AircraftManager;
import com.fabienit.flyingclub.manager.UtilsManager;
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
import java.util.*;

/**
 * FlyingClubController
 * RestController, handle client request and provide entity FlyingClub data
 */
@RestController
@Validated
public class FlyingClubController {

    private final FlyingClubManager flyingClubManager;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public FlyingClubController(FlyingClubManager flyingClubManager) {
        this.flyingClubManager = flyingClubManager;
    }

    @GetMapping(value = "/flyingClub")
    public List<FlyingClub> getFlyingClub() {

        logger.info("Providing aircraft resource from database: all flyingClub list");

        return flyingClubManager.findAll();
    }


    @GetMapping(value = "/flyingClub/{id}")
    public Optional<FlyingClub> getFlyingClubById(@PathVariable @Min(value = 1) Integer id) {

        logger.info("Providing aircraft resource from database: flyingClub id: " + id);

        Optional<FlyingClub> flyingClub = flyingClubManager.findById(id);

        if(!flyingClub.isPresent()) throw new RessourceNotFoundException("the flyingClub entity doesn't exists, id: " + id);

        return flyingClub;
    }

    @PostMapping(value = "/flyingClub")
    public ResponseEntity<Void> addFlyingClub(@Valid @RequestBody FlyingClub flyingClub) {
     
        logger.info("Adding new flyingClub in database");
        
        if(flyingClubManager.existsFlyingClubById(flyingClub.getId()))
            throw new EntityAlreadyExistsException("The flyingClub entity already exists , id: " + flyingClub.getId());

        FlyingClub flyingClubAdded = flyingClubManager.save(flyingClub);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(flyingClubAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/flyingClub/{id}")
    public ResponseEntity<Void> updateFlyingClub(@PathVariable @Min(value = 1) Integer id, @Valid @RequestBody FlyingClub flyingClubDetails) {

        logger.info("Updating aircraft in database, id: " + id);

        try {
            flyingClubManager.findById(flyingClubDetails.getId());
        } catch (NoSuchElementException e) {
            logger.debug("The requested flyingClub entity doesn't exist, id: " + flyingClubDetails.getId());
            throw new RessourceNotFoundException("The requested flyingClub entity doesn't exist, id: " + flyingClubDetails.getId());
        }
        
        flyingClubManager.save(flyingClubDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/flyingClub/{id}")
    public void deleteFlyingClub(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting flyingClub from database: id: "+ id);

        try {
            flyingClubManager.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("the flyingClub entity doesn't exists, id: " + id);
            throw new RessourceNotFoundException("the flyingClub entity doesn't exists, id: " + id);
        }
    }    

}