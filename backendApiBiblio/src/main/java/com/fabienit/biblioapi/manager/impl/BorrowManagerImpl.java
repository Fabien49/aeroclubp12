package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.dao.BorrowDao;
import com.fabienit.biblioapi.manager.AvailableCopieManager;
import com.fabienit.biblioapi.manager.BorrowManager;
import com.fabienit.biblioapi.manager.ReservationManager;
import com.fabienit.biblioapi.web.exceptions.FunctionnalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowManagerImpl implements BorrowManager {

    @Autowired
    private BorrowDao borrowDao;

    @Autowired
    private AvailableCopieManager availableCopieManager;

    @Autowired
    private ReservationManager reservationManager;

    @Override
    public List<Borrow> getAllBorrows() {
        return borrowDao.findAll();
    }

    @Override
    public Optional<Borrow> getById(int id) {
        return borrowDao.findById(id);
    }

    @Override
    @Transactional
    public Borrow save(Borrow borrow, String operationType) throws FunctionnalException {

        int bookId = borrow.getBook().getId();
        int libraryId = borrow.getLibrary().getId();
        int userId = borrow.getRegistereduser().getId();

        Borrow BorrowToSave = new Borrow(borrow);

        if (operationType.equals("extend")){

            //check operations
            checkIfBorrowIsAlreadyExtended(borrow);
            checkIfReturnDateIsOutDated(borrow);

            // Add 4 weeks to return date
            BorrowToSave.setReturnDate(BorrowToSave.getReturnDate().plusWeeks(4));

            // Set true extended duration attribute
            BorrowToSave.setExtendedDuration(true);
        }

        // Save or update borrow
        Borrow savedBorrow = borrowDao.save(BorrowToSave);

        // Update related availableCopie, triggered by borrow action(add or return)
        availableCopieManager.relatedAvailableCopieUpdate(bookId, libraryId, operationType);

        // If outing borrow need to check
        if(operationType.equals("out")){
            // Update related reservation
            reservationManager.relatedReservationUpdate(bookId, libraryId, userId);
        }

        return savedBorrow;
    }

    @Override
    public void deleteById(int id) {
        borrowDao.deleteById(id);
    }

    @Override
    public List<Borrow> getAllBorrowsByRegistereduserId(int userId) {
        return borrowDao.findByRegistereduserId(userId);
    }

    @Override
    public List<Borrow> getAllBorrowsByBookIdAndLibraryId(int book_id, int library_id) {
        return borrowDao.findAllByBookIdAndLibraryId(book_id, library_id);
    }

    /**
     * Check if return date is outdated
     */
    public void checkIfReturnDateIsOutDated(Borrow borrow) throws FunctionnalException {
        LocalDate today = LocalDate.now();
        if (borrow.getReturnDate().isBefore(today)) throw new FunctionnalException("Le prêt ne peut pas être prolongé car la date de retour est dépassée");
    }

    /**
     * Check if borrow has already been extended
     */
    public void checkIfBorrowIsAlreadyExtended(Borrow borrow) throws FunctionnalException {
        if (borrow.getExtendedDuration()) throw new FunctionnalException("Le prêt à déjà été prolongé");
    }

}
