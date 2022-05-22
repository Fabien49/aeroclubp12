package com.fabienit.biblioapi.manager;

import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.web.exceptions.FunctionnalException;

import java.util.List;
import java.util.Optional;

public interface BorrowManager {

    List<Borrow> getAllBorrows();
    Optional<Borrow> getById(int id);
    Borrow save(Borrow borrow, String operationType) throws FunctionnalException;
    void deleteById(int id);
    List<Borrow> getAllBorrowsByRegistereduserId(int userId);
    List<Borrow> getAllBorrowsByBookIdAndLibraryId(int book_id, int library_id);
}
