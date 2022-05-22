package com.fabienit.biblioapi.dao;


import com.fabienit.biblioapi.model.beans.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * BookDao
 */
@Repository
public interface BookDao extends JpaRepository<Book, Integer>{

    // Find book by title or by author last name ignoring case
    List<Book> findByTitleIgnoreCaseContainingOrAuthorLastNameIgnoreCaseContaining(String title, String authorLastName);
    boolean existsBookByTitleAndPublicationDate(String title, LocalDate parutionDate);
}