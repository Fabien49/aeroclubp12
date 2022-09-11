package com.fabienit.biblioapi.manager;

import com.fabienit.biblioapi.model.beans.Library;

import java.util.List;
import java.util.Optional;

public interface LibraryManager {

    List<Library> findAll();
    Optional<Library> findById(int id);
    Library save(Library library);
    Library deleteById(int id);
}

