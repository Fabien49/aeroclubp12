package com.fabienit.biblioapi.web.controllers;

import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import com.fabienit.biblioapi.dao.AvailableCopieDao;
import com.fabienit.biblioapi.web.exceptions.EntityAlreadyExistsException;
import com.fabienit.biblioapi.web.exceptions.ForeignKeyNotExistsException;
import com.fabienit.biblioapi.web.exceptions.RessourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
 * AvailableCopieController
 * 
 * RestController, handle client request and provide entity AvailableCopie data
 */
@RestController
@Validated
public class AvailableCopieController {

    @Autowired
    private AvailableCopieDao availableCopieDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping(value = "/availableCopies")
    public List<AvailableCopie> getAvailableCopies() {

        logger.info("Providing availablecopie resource from database: all availablecopie list");

        List<AvailableCopie> availableCopies = availableCopieDao.findAll();

        return availableCopies;
    }

    @GetMapping(value = "/availableCopies/{book_id}/{library_id}")
    public Optional<AvailableCopie> getAvailableCopieById(@PathVariable(value = "book_id") @Min(value = 1) int bookId,
            @PathVariable(value = "library_id") @Min(value = 1) int libraryId) {

        logger.info("Providing availablecopie resource from database: availableCopie book_id: " + bookId + ", library_id: " + libraryId);

        AvailableCopieKey key = new AvailableCopieKey();
        key.setBookId(bookId);
        key.setLibraryId(libraryId);

        Optional<AvailableCopie> availableCopie = availableCopieDao.findById(key);

        if (!availableCopie.isPresent())
            throw new RessourceNotFoundException(
                    "la copie n'existe pas, book_id: " + bookId + ", library_id: " + libraryId);

        return availableCopie;
    }

    @PostMapping(value = "/availableCopies")
    public ResponseEntity<Void> addAvalaibleCopie(@Valid @RequestBody AvailableCopie availableCopie) {

        logger.info("Adding new availableCopie in database");

         if (availableCopieDao.existsById(availableCopie.getId()))
            throw new EntityAlreadyExistsException("L'entité availableCopie existe déjà, book_id: "+ availableCopie.getId().getBookId() + ", library_id: " + availableCopie.getId().getLibraryId());

        AvailableCopie copieAdded;  
        try {
            copieAdded = availableCopieDao.save(availableCopie);
        } catch (DataIntegrityViolationException e) {
            logger.debug("La clé étrangère de l'entité book ou de l'entité library nexiste pas");
            throw new ForeignKeyNotExistsException("L'entité availableCopie n'existe pas, book_id: "+ availableCopie.getId().getBookId() + ", library_id: " + availableCopie.getId().getLibraryId());
        }
        

        Map<String, Integer> map =  new HashMap<String, Integer>();

        map.put("book_id", copieAdded.getId().getBookId());
        map.put("library_id", copieAdded.getId().getLibraryId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{book_id}/{library_id}")
        .buildAndExpand(map).toUri();

        return ResponseEntity.created(location).build(); 
    }

    @PutMapping(value = "/availableCopies/{book_id}/{library_id}")
    public ResponseEntity<Void> updateAvailableCopie(@PathVariable(value = "book_id") @Min(value = 1) int bookId,
            @PathVariable(value = "library_id") @Min(value = 1) int libraryId, @Valid @RequestBody AvailableCopie updatedAvailableCopie) {

        logger.info("Updating availableCopie in database, book_id: "  + bookId + ", library_id: " + libraryId);
        
        try {
            availableCopieDao.findById(updatedAvailableCopie.getId()).get();
        } catch (NoSuchElementException e) {
            logger.debug("La clé étrangère de l'entité book ou de l'entité library nexiste pas");
            throw new RessourceNotFoundException("L'entité availableCopie demandée n'existe pas, book_id: "+ updatedAvailableCopie.getId().getBookId() + ", library_id: " + updatedAvailableCopie.getId().getLibraryId());
        }

        availableCopieDao.save(updatedAvailableCopie);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/availableCopies/{book_id}/{library_id}")
    public void deleteAvailableCopie(@PathVariable(value = "book_id") @Min(value = 1) int bookId,
            @PathVariable(value = "library_id") @Min(value = 1) int libraryId) {

        logger.info("Deleting availableCopie from database: book_id: "  + bookId + ", library_id: " + libraryId);

        AvailableCopieKey availableCopieKey = new AvailableCopieKey();
        availableCopieKey.setBookId(bookId);
        availableCopieKey.setLibraryId(libraryId);

        try {
            availableCopieDao.deleteById(availableCopieKey);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("La clé étrangère de l'entité book ou de l'entité library nexiste pas");
            throw new RessourceNotFoundException(
                    "L'entité n'existe pas, book_id: " + bookId + ", library_id: " + libraryId);
        }

    }

}