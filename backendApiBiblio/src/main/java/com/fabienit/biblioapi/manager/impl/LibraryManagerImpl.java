package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.Library;
import com.fabienit.biblioapi.dao.LibraryDao;
import com.fabienit.biblioapi.manager.LibraryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryManagerImpl implements LibraryManager {

    @Autowired
    private LibraryDao libraryDao;

    @Override
    public List<Library> findAll() {
        return libraryDao.findAll();
    }

    @Override
    public Optional<Library> findById(int id) {
        return libraryDao.findById(id);
    }

    @Override
    public Library save(Library library) {
        return libraryDao.save(library);
    }

    @Override
    public Library deleteById(int id) {
        libraryDao.deleteById(id);
        return null;
    }
}
