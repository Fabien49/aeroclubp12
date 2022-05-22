package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.model.beans.Reservation;
import com.fabienit.biblioapi.dao.AvailableCopieDao;
import com.fabienit.biblioapi.manager.AvailableCopieManager;
import com.fabienit.biblioapi.manager.BorrowManager;
import com.fabienit.biblioapi.manager.ReservationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailableCopieManagerImpl implements AvailableCopieManager {

    @Autowired
    private AvailableCopieDao availableCopieDao;

    @Autowired
    private ReservationManager reservationManager;

    @Autowired
    private BorrowManager borrowManager;

    public void setAvailableCopieDao(AvailableCopieDao availableCopieDao) {
        this.availableCopieDao = availableCopieDao;
    }

    public void setReservationManager(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }

    public void setBorrowManager(BorrowManager borrowManager) {
        this.borrowManager = borrowManager;
    }

    /**
     *
     * @param
     * @return
     */
    @Override
    public Boolean updateStatusBookCanBeReserved(AvailableCopie availableCopie) {
        List<Reservation> reservationList = reservationManager.findAllByBookIdAndLibraryId(availableCopie.getId().getBookId(), availableCopie.getId().getLibraryId());

        int currentReservationCount = reservationList.size();

        int maxReservationCount = availableCopie.getOwnedQuantity() * 2;

        if(currentReservationCount < maxReservationCount){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public int updateAvailableQuantity(int currentQuantity, String operationType) {
        int availableQuantityToUpdate = currentQuantity;

        if (operationType.equals("in")){
            availableQuantityToUpdate = currentQuantity + 1;
        } else if (operationType.equals("out")){
            availableQuantityToUpdate = currentQuantity - 1;
        }

        return availableQuantityToUpdate;
    }


    /**
     *
     * @return
     */
    @Override
    public AvailableCopie relatedAvailableCopieUpdate(int bookId, int libraryId, String operationType) {
        // Get related availableCopie
        AvailableCopie copieToRefresh = findById(new AvailableCopieKey(bookId, libraryId)).get();

        // Get updated available quantity
        int availableQuantityUpdated = updateAvailableQuantity(copieToRefresh.getAvailableQuantity(), operationType);

        // Set updated available quantity on bean to update
        copieToRefresh.setAvailableQuantity(availableQuantityUpdated);

        // Get updated bookCanBeReserved status
        Boolean bookCanBeReservedUpdated = updateStatusBookCanBeReserved(copieToRefresh);

        // Set updated bookCanBeReserved status on bean to update
        copieToRefresh.setBookCanBeReserved(bookCanBeReservedUpdated);

        // Get nearest return date
        LocalDate nearestReturnDate = getNearestReturnDate(bookId, libraryId);

        // Set nearest return date
        copieToRefresh.setNearestReturnDate(nearestReturnDate);

        return save(copieToRefresh);
    }

    /**
     *
     */
    public LocalDate getNearestReturnDate(int book_id, int library_id) {
        LocalDate updatedNearestReturnDate = null;
        List<Borrow> borrowList = borrowManager.getAllBorrowsByBookIdAndLibraryId(book_id, library_id);

        for (int i=0 ; i<borrowList.size(); i++){
            if(i==0){
                updatedNearestReturnDate = borrowList.get(i).getReturnDate();
            }

            if (i>0){
                if (updatedNearestReturnDate.isBefore(borrowList.get(i).getReturnDate())){
                    updatedNearestReturnDate = borrowList.get(i).getReturnDate();
                }
            }
        }
        return updatedNearestReturnDate;

    }


    /**
     *
     * @return
     */
    @Override
    public AvailableCopie updateReservationCount(int bookId, int libraryId) {
        AvailableCopie copieToUpdate = availableCopieDao.findById(new AvailableCopieKey(bookId, libraryId)).get();

        int updatedReservationCount = reservationManager.findAllByBookIdAndLibraryId(bookId, libraryId).size();

        copieToUpdate.setReservationCount(updatedReservationCount);

        return save(copieToUpdate);

    }

    @Override
    public Optional<AvailableCopie> findById(AvailableCopieKey key) {
        return availableCopieDao.findById(key);
    }

    @Override
    public AvailableCopie save(AvailableCopie availableCopie) {
        return availableCopieDao.save(availableCopie);
    }

    @Override
    public List<AvailableCopie> findAll() {
        return availableCopieDao.findAll();
    }

    @Override
    public Boolean existsById(AvailableCopieKey availableCopieKey) {
        return availableCopieDao.existsById(availableCopieKey);
    }

    @Override
    public void deleteById(AvailableCopieKey availableCopieKey) {
        availableCopieDao.deleteById(availableCopieKey);
    }
}
