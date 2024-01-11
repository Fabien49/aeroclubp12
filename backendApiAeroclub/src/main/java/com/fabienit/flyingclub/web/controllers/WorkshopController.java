package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.model.beans.Workshop;
import com.fabienit.flyingclub.dao.WorkshopDao;
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
 * WorkshopController
 * 
 * RestController, handle client request and provide entity Aircraft data
 */
@RestController
@Validated
public class WorkshopController {

    @Autowired
    private WorkshopDao workshopDao;

    @Autowired
    private UtilsManager utilsManager;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/workshop")
    public List<Workshop> getWorkshops(@RequestParam(required = false) String query) {

        logger.info("Providing revision resource from database: all revisions list");
        List<Workshop> workshops = workshopDao.findAll();
         
        if (query == null) return workshops;

        // Split query
        logger.debug("Splitting query, query: " + query);
        String[] splitedQueries = utilsManager.splitQueryString(query);

        List<Workshop> searchResultWorkshops = new ArrayList<Workshop>();

        //Match revision with queries
        for (String splitedQuery : splitedQueries) {
            for (Workshop workshop : workshops) {
                if (workshop.getMotorChange()
                        || workshop.getHelixChange()) {
                    searchResultWorkshops.add(workshop);
                }
            }
        }

        // Create new list without duplicates aircraft
        List<Workshop> searchResultWorkshopsWithoutDuplicates = new ArrayList<>(new HashSet<>(searchResultWorkshops));

        return searchResultWorkshopsWithoutDuplicates;
    }



    @GetMapping(value = "/workshop/{id}")
    public Optional<Workshop> getWorkshopById(@PathVariable @Min(value = 1) Integer id) {

        logger.info("Providing workshop resource from database: revision id: " + id);

        Optional<Workshop> workshop = workshopDao.findById(id);

        if(!workshop.isPresent()) throw new RessourceNotFoundException("the workshop doesn't exists, id: " + id);

        return workshop;
    }

    @PostMapping(value = "/workshop")
    public ResponseEntity<Void> addWorkshop(@Valid @RequestBody Workshop workshop) {
     
        logger.info("Adding new workshop in database");
        
        if(workshopDao.existsWorkshopById(workshop.getId()))
            throw new EntityAlreadyExistsException("The workshop entity already exists , id: " + workshop.getId());

        Workshop workshopAdded = workshopDao.save(workshop);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(workshopAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/workshop/{id}")
    public ResponseEntity<Void> updateWorkshop(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Workshop workshopDetails) {

        logger.info("Updating workshop in database, id: " + id);

        try {
            workshopDao.findById(workshopDetails.getId());
        } catch (NoSuchElementException e) {
            logger.debug("The requested workshop entity doesn't exist, id: " + workshopDetails.getId());
            throw new RessourceNotFoundException("The requested workshop entity doesn't exist, id: " + workshopDetails.getId());
        }

        System.out.println(workshopDetails.getAircraft());
        workshopDao.save(workshopDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/workshop/{id}")
    public void deleteWorkshop(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting workshop from database: id: "+ id);

        try {
            workshopDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("the workshop entity doesn't exists, id: " + id);
            throw new RessourceNotFoundException("the workshop entity doesn't exists, id: " + id);
        }
    }    

}