package com.fabienit.biblioapi.web.controllers;

import com.fabienit.biblioapi.model.beans.Library;
import com.fabienit.biblioapi.dao.LibraryDao;
import com.fabienit.biblioapi.web.exceptions.EntityAlreadyExistsException;
import com.fabienit.biblioapi.web.exceptions.RessourceNotFoundException;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * LibraryController
 * 
 * RestController, handle client request and provide entity Library data
 */
@RestController
@Validated
public class LibraryController {

    @Autowired
    private LibraryDao libraryDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value="/libraries")
    public List<Library> getLibraries() {

        logger.info("Providing library resource from database: all library list");

        List<Library> libraries = libraryDao.findAll();

        return libraries;
    }

    @GetMapping(value="/libraries/{id}")
    public Optional<Library> getLibraryById(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing library resource from database: library id: " + id);

        Optional<Library> library = libraryDao.findById(id);

        if(!library.isPresent()) throw new RessourceNotFoundException("L'entité bibliothèque n'existe pas, id: " + id);

        return library;
    }

    @PostMapping(value="/libraries")
    public ResponseEntity<Void> addLibrary(@Valid @RequestBody Library library) {
        
        logger.info("Adding new library in database");

        Library libraryAdded;
        try {
            libraryAdded = libraryDao.save(library);
        } catch (Exception e) {
            logger.debug("L'entité bibliothèque existe déjà, nom :" + library.getName());
            throw new EntityAlreadyExistsException("L'entité bibliothèque existe déjà, nom :" + library.getName());
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(libraryAdded.getId())
                .toUri();
  
        return ResponseEntity.created(location).build();
    }
    
    @PutMapping(value="/libraries/{id}")
    public ResponseEntity<Void> updateLibrary(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Library libraryDetails) {
        
        logger.info("Updating library in database, id: " + id);

        try {
            libraryDao.findById(libraryDetails.getId()).get();
        } catch (NoSuchElementException e) {
            logger.debug("L'entité bibliothèque demandé n'existe pas, id: " + libraryDetails.getId());
            throw new RessourceNotFoundException("L'entité bibliothèque demandé n'existe pas, id: " + libraryDetails.getId());
        }
        
        libraryDao.save(libraryDetails);

        return ResponseEntity.ok().build();        

    }

    @DeleteMapping(value="/libraries/{id}")
    public void deleteBorrow(@PathVariable @Min(value = 1)int id) {

        logger.info("Deleting library from database: id: "+ id);
        
        try {
            libraryDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("L'entité bibliothèque n'existe pas, id: " + id);
            throw new RessourceNotFoundException("L'entité bibliothèque n'existe pas, id: " + id);
        }
        
        
    }
}