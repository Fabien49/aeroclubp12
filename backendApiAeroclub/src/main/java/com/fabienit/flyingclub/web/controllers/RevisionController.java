package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.model.beans.Revision;
import com.fabienit.flyingclub.dao.RevisionDao;
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
 * RevisionController
 * 
 * RestController, handle client request and provide entity Aircraft data
 */
@RestController
@Validated
public class RevisionController {

    @Autowired
    private RevisionDao revisionDao;

    @Autowired
    private UtilsManager utilsManager;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/revisions")
    public List<Revision> getRevisions(@RequestParam(required = false) String query) {

        logger.info("Providing revision resource from database: all revisions list");
        List<Revision> revisions = revisionDao.findAll();
         
        if (query == null) return revisions;

        // Split query
        logger.debug("Splitting query, query: " + query);
        String[] splitedQueries = utilsManager.splitQueryString(query);

        List<Revision> searchResultRevisions = new ArrayList<Revision>();

        //Match revision with queries
        for (String splitedQuery : splitedQueries) {
            for (Revision revision : revisions) {
                if (revision.getLevels()
                        || revision.getPressure() || revision.getBodywork()) {
                    searchResultRevisions.add(revision);
                }
            }
        }

        // Create new list without duplicates aircraft
        List<Revision> searchResultRevisionsWithoutDuplicates = new ArrayList<>(new HashSet<>(searchResultRevisions));

        return searchResultRevisionsWithoutDuplicates;
    }



    @GetMapping(value = "/revisions/{id}")
    public Optional<Revision> getRevisionById(@PathVariable @Min(value = 1) Integer id) {

        logger.info("Providing revision resource from database: revision id: " + id);

        Optional<Revision> revision = revisionDao.findById(id);

        if(!revision.isPresent()) throw new RessourceNotFoundException("the revision entity doesn't exists, id: " + id);

        return revision;
    }

    @PostMapping(value = "/revisions")
    public ResponseEntity<Void> addRevision(@Valid @RequestBody Revision revision) {
     
        logger.info("Adding new revision in database");
        
        if(revisionDao.existsRevisionById(revision.getId()))
            throw new EntityAlreadyExistsException("The revision entity already exists , id: " + revision.getId());

        Revision revisionAdded = revisionDao.save(revision);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(revisionAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/revisions/{id}")
    public ResponseEntity<Void> updateRevision(@PathVariable @Min(value = 1) Integer id, @Valid @RequestBody Revision revisionDetails) {

        logger.info("Updating revision in database, id: " + id);

        try {
            revisionDao.findById(revisionDetails.getId());
        } catch (NoSuchElementException e) {
            logger.debug("The requested revision entity doesn't exist, id: " + revisionDetails.getId());
            throw new RessourceNotFoundException("The requested revision entity doesn't exist, id: " + revisionDetails.getId());
        }
        
        revisionDao.save(revisionDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/revisions/{id}")
    public void deleteRevision(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting revision from database: id: "+ id);

        try {
            revisionDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("the revision entity doesn't exists, id: " + id);
            throw new RessourceNotFoundException("the revision entity doesn't exists, id: " + id);
        }
    }    

}