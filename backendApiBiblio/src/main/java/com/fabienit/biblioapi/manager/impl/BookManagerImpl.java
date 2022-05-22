package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.Book;
import com.fabienit.biblioapi.dao.BookDao;
import com.fabienit.biblioapi.manager.BookManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookManagerImpl  implements BookManager {

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Optional<Book> findById(int id) {
        return bookDao.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Boolean existsBookByTitleAndPublicationDate(String title, LocalDate parutionDate) {
        return bookDao.existsBookByTitleAndPublicationDate(title, parutionDate);
    }

    @Override
    public void deleteById(int id) {
        bookDao.deleteById(id);
    }
}
