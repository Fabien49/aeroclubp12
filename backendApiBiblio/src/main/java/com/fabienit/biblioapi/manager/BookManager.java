package com.fabienit.biblioapi.manager;

import com.fabienit.biblioapi.model.beans.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookManager {

    List<Book> findAll();
    Optional<Book> findById(int id);
    Book save(Book book);
    Boolean existsBookByTitleAndPublicationDate(String title, LocalDate parutionDate);
    void deleteById(int id);
}
