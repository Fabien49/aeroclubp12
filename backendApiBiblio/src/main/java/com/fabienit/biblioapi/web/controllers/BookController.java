package com.fabienit.biblioapi.web.controllers;

import com.fabienit.biblioapi.model.beans.Book;
import com.fabienit.biblioapi.dao.BookDao;
import com.fabienit.biblioapi.manager.UtilsManager;
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
import java.util.*;

/**
 * BookController
 * 
 * RestController, handle client request and provide entity Book data
 */
@RestController
@Validated
public class BookController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UtilsManager utilsManager;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/books")
    public List<Book> getBooks(@RequestParam(required = false) String query) {

        logger.info("Providing book resource from database: all book list");
        List<Book> books = bookDao.findAll();
         
        if (query == null) return books;   

        // Split query
        logger.debug("Splitting query, query: " + query);
        String[] splitedQueries = utilsManager.splitQueryString(query);

        List<Book> searchResultBooks = new ArrayList<Book>();

        //Match books with queries
        for (String splitedQuery : splitedQueries) {
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(splitedQuery.toLowerCase())
                        || book.getAuthorLastName().toLowerCase().contains(splitedQuery.toLowerCase())) {
                    searchResultBooks.add(book);
                }
            }
        }

        // Create new list without duplicates books
        List<Book> searchResultBooksWithoutDuplicates = new ArrayList<>(new HashSet<>(searchResultBooks));

        return searchResultBooksWithoutDuplicates;
    }

    @GetMapping(value = "/books/{id}")
    public Optional<Book> getBookById(@PathVariable @Min(value = 1) int id) {

        logger.info("Providing book resource from database: book id: " + id);

        Optional<Book> book = bookDao.findById(id);

        if(!book.isPresent()) throw new RessourceNotFoundException("L'entité livre n'existe pas, id: " + id);

        return book;
    }

    @PostMapping(value = "/books")
    public ResponseEntity<Void> addBook(@Valid @RequestBody Book book) {
     
        logger.info("Adding new book in database");
        
        if(bookDao.existsBookByTitleAndPublicationDate(book.getTitle(), book.getPublicationDate()))
            throw new EntityAlreadyExistsException("L'entité livre existe déjà, tilte: " + book.getTitle());

        Book bookAdded = bookDao.save(book);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/books/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable @Min(value = 1) int id, @Valid @RequestBody Book bookDetails) {

        logger.info("Updating book in database, id: " + id);

        try {
            bookDao.findById(bookDetails.getId()).get();
        } catch (NoSuchElementException e) {
            logger.debug("L'entité livre demandée n'existe pas, id: " + bookDetails.getId());
            throw new RessourceNotFoundException("L'entité livre demandée n'existe pas, id: " + bookDetails.getId());
        }
        
        bookDao.save(bookDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/books/{id}")
    public void deleteBook(@PathVariable @Min(value = 1) int id) {

        logger.info("Deleting book from database: id: "+ id);

        try {
            bookDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("L'entité livre n'existe pas, id: " + id);
            throw new RessourceNotFoundException("L'entité livre n'existe pas, id: " + id);
        }
    }    

}