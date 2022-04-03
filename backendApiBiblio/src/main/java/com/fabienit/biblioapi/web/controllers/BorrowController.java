package com.fabienit.biblioapi.web.controllers;

import com.fabienit.biblioapi.beans.AvailableCopie;
import com.fabienit.biblioapi.beans.Borrow;
import com.fabienit.biblioapi.dao.BorrowDao;
import com.fabienit.biblioapi.web.exceptions.ForeignKeyNotExistsException;
import com.fabienit.biblioapi.web.exceptions.RessourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
 * BorrowController
 * 
 * RestController, handle client request and provide entity Borrow data
 */
@RestController
@Validated
public class BorrowController {

    @Autowired
    private BorrowDao borrowDao;

    @Autowired
    private AvailableCopieController availableCopieController;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value="/borrows")
    public List<Borrow> getBorrows() {

        logger.info("Providing borrow resource from database: all borrow list");

        List<Borrow> borrows = borrowDao.findAll();

        return borrows;
    }

    @GetMapping(value="/borrows/{id}")
    public Borrow getBorrowById(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing borrow resource from database: borrow id: " + id);

        Optional<Borrow> borrow = borrowDao.findById(id);

        if(!borrow.isPresent()) throw new RessourceNotFoundException("Le prêt n'existe pas, id: "+ id);

        return borrow.get();
    }

    @PostMapping(value="/borrows")
    @Transactional
    public ResponseEntity<Void> addBorrow(@Valid @RequestBody Borrow borrow) {

        logger.info("Adding new borrow in database");
          
        Borrow borrowAdded;
        try {
            borrowAdded = borrowDao.save(borrow);
        } catch (Exception e) {
            logger.debug("Une ou plusieurs clé étrangères n'existent pas.");
            throw new ForeignKeyNotExistsException("Une ou plusieurs clé étrangères n'existent pas.");
        }

        // Init Ids
        int bookId = borrow.getBook().getId();
        int libraryId = borrow.getLibrary().getId();

        // Get available copie
        AvailableCopie availableCopie = availableCopieController.getAvailableCopieById(bookId, libraryId).get();

        // Remove one copie
        int availableQuantity = availableCopie.getAvailableQuantity();
        availableCopie.setAvailableQuantity(availableQuantity - 1);

        // Update available copie in datatbase
        availableCopieController.updateAvailableCopie(bookId, libraryId, availableCopie);
        

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(borrowAdded.getId())
                .toUri();
  
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value="/borrows/{id}")
    public ResponseEntity<Void> updateBorrow(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Borrow borrowDetails) {
        
        logger.info("Updating borrow in database, id: " + id);

        try {
            borrowDao.findById(borrowDetails.getId()).get(); 
        } catch (NoSuchElementException e) {
            logger.debug("L'entité prêt demandée n'existe pas, id: " + borrowDetails.getId());
            throw new RessourceNotFoundException("L'entité prêt demandée n'existe pas, id: " + borrowDetails.getId());
        }
        
        borrowDao.save(borrowDetails);

        return ResponseEntity.ok().build();        

    }

    @PutMapping(value = "/borrows/return/{id}")
    @Transactional
    public ResponseEntity<Void> returnBorrow(@PathVariable @Min(value = 1) int id) {

        logger.info("Return borrow, id: " + id);

        Borrow returnedBorrow;
        try {
            returnedBorrow = borrowDao.findById(id).get();
        } catch (NoSuchElementException e) {
            logger.debug("L'entité prêt demandée n'existe pas, id: " + id);
            throw new RessourceNotFoundException("L'entité prêt demandée n'existe pas, id: " + id);
        }

        returnedBorrow.setBookReturned(true);
        borrowDao.save(returnedBorrow);

        // Init Ids
        int bookId = returnedBorrow.getBook().getId();
        int libraryId = returnedBorrow.getLibrary().getId();

        // Get available copie
        AvailableCopie availableCopie = availableCopieController.getAvailableCopieById(bookId, libraryId).get();

        // Add one copie
        int availableQuantity = availableCopie.getAvailableQuantity();
        availableCopie.setAvailableQuantity(availableQuantity + 1);

        // Update available copie in datatbase
        availableCopieController.updateAvailableCopie(bookId, libraryId, availableCopie);

        return ResponseEntity.ok().build();

    }
    
    @DeleteMapping(value="/borrows/{id}")
    public void deleteBorrow(@PathVariable @Min(value = 1) int id) {
        
        logger.info("Deleting borrow from database: id: "+ id);

        try {
            borrowDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("L'entité prêt n'existe pas, id: "+ id);
            throw new RessourceNotFoundException("L'entité prêt n'existe pas, id: "+ id);
        }
        
        
    }

    @GetMapping(value="/users/{user_id}/borrows")
    public List<Borrow> getBorrowsByUserId(@PathVariable(value = "user_id") @Min(value = 1) int userId) {

        logger.info("Providing borrow resources from database by user id: " + userId);

        List<Borrow>  borrows = borrowDao.findByRegistereduserId(userId);

        return borrows;
    }  
    
}