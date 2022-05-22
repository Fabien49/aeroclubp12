package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.model.beans.Reservation;
import com.fabienit.biblioapi.dao.ReservationDao;
import com.fabienit.biblioapi.manager.AvailableCopieManager;
import com.fabienit.biblioapi.manager.BorrowManager;
import com.fabienit.biblioapi.manager.ReservationManager;
import com.fabienit.biblioapi.web.exceptions.FunctionnalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationManagerImpl implements ReservationManager {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private AvailableCopieManager availableCopieManager;

    @Autowired
    private BorrowManager borrowManager;

    public void setReservationDao(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public void setAvailableCopieManager(AvailableCopieManager availableCopieManager) {
        this.availableCopieManager = availableCopieManager;
    }

    public void setBorrowManager(BorrowManager borrowManager) {
        this.borrowManager = borrowManager;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     */
    @Override
    public void relatedReservationUpdate(int bookId, int libraryId, int userId) {
        Optional<Reservation> relatedReservation = reservationDao.findByBookAndLibraryAndRegisteredUser(bookId, libraryId, userId);

        if (relatedReservation.isPresent()) {
            deleteById(relatedReservation.get().getId());
        }
    }

    /**
     *
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        Reservation reservationToDelete = reservationDao.findById(id).get();
        int bookId = reservationToDelete.getAvailableCopie().getId().getBookId();
        int libraryid = reservationToDelete.getAvailableCopie().getId().getLibraryId();
        reservationDao.deleteById(id);
        offsetReservationsPositionAfterDelete(bookId, libraryid, reservationToDelete.getPosition());
        availableCopieManager.updateReservationCount(reservationToDelete.getAvailableCopie().getId().getBookId(), reservationToDelete.getAvailableCopie().getId().getLibraryId());
    }

    /**
     * After delete reservation action, decrease by one each reservations positions for the same copy
     */
    public void offsetReservationsPositionAfterDelete(int bookId, int libraryId, int deletedReservationPosition) {
        List<Reservation> reservationList =
                findAllByBookIdAndLibraryId(bookId, libraryId);

        for (Reservation reservation : reservationList) {
            if (reservation.getPosition()>deletedReservationPosition){
                int currentPosition = reservation.getPosition();
                reservation.setPosition(currentPosition - 1);
                reservationDao.save(reservation);
            }

        }
    }

    /**
     *
     */
    @Override
    @Transactional
    public Reservation save(Reservation reservation) throws FunctionnalException {
        // If new reservation check rules are respected
        if (reservation.getId() == 0){
            checkReservation(reservation);
        }

        int bookId = reservation.getAvailableCopie().getId().getBookId();
        int libraryId = reservation.getAvailableCopie().getId().getLibraryId();

        // Set reservation position
        int reservationPosition = setReservationPosition(bookId, libraryId, reservation.getPosition());
        reservation.setPosition(reservationPosition);

        // Save
        Reservation savedReservation = reservationDao.save(reservation);

        // After save update reservation count for related AvailableCopie bean
        availableCopieManager.updateReservationCount(reservation.getAvailableCopie().getId().getBookId(),
                reservation.getAvailableCopie().getId().getLibraryId());

        return savedReservation;
    }

    private void checkReservation(Reservation reservation) throws FunctionnalException {

        int bookId = reservation.getAvailableCopie().getId().getBookId();
        int libraryId = reservation.getAvailableCopie().getId().getLibraryId();
        int userId = reservation.getRegistereduser().getId();

        checkIfReservationListIsFull(bookId, libraryId);
        checkIfBookIsAlreadyBorrowed(bookId, userId);
        checkIfBookIsAlreadyReserved(bookId, userId);

    }

    /**
     * Check if the reservation list is full, reservation list size must be equal to owned quantity x 2
     */
    public void checkIfReservationListIsFull(int bookId, int libraryId) throws FunctionnalException {
        AvailableCopie availableCopie = availableCopieManager.findById(new AvailableCopieKey(bookId, libraryId)).get();
        int reservationCountMax = availableCopie.getOwnedQuantity() * 2;
        if (availableCopie.getReservationCount() >= reservationCountMax) throw new FunctionnalException("La liste de reservation est pleine");
    }

    /**
     * Check if user already has an active borrow for this book
     */
    public void checkIfBookIsAlreadyBorrowed(int bookId, int userId) throws FunctionnalException {
        List<Borrow> borrowList = borrowManager.getAllBorrowsByRegistereduserId(userId);
        for (Borrow borrow: borrowList) {
            if (!borrow.getBookReturned() && borrow.getBook().getId() == bookId)
                throw new FunctionnalException("L'utilisateur a déjà un emprunt en cours concernant ce livre");
        }
    }

    /**
     *Check if user already has a reservation for this book
     */
    public void checkIfBookIsAlreadyReserved(int bookId, int userId) throws FunctionnalException {
        List<Reservation> reservationList = findAllByRegisteredUser(userId);
        for (Reservation reservationBean: reservationList) {
            if (reservationBean.getAvailableCopie().getId().getBookId() == bookId)
                throw new FunctionnalException("L'utilisateur a déjà une reservation en cours concernant ce livre");
        }
    }


    /**
     *
     */
    public int setReservationPosition(int bookId, int libraryId,int reservationPosition) {
        if (reservationPosition == 0) {
            List<Reservation> reservationList =
                    reservationDao.findAllByBookIdAndLibraryId(bookId, libraryId);
            return reservationList.size() + 1;
        }
        return reservationPosition;
    }


    /**
     *Update bean reservation with NotificationIsSent parameter set to true and AvailabilityDate set to today's date
     * @return
     */
    @Override
    public Reservation updateReservationAfterNotification(Reservation reservation) throws FunctionnalException {
        reservation.setNotificationIsSent(true);
        reservation.setAvailabilityDate(LocalDate.now());
        return reservation;
    }

    /**
     *
     */
    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    /**
     *
     */
    @Override
    public Optional<Reservation> findById(int id) {
        return reservationDao.findById(id);
    }

    /**
     *
     */
    @Override
    public List<Reservation> findAllByBookIdAndLibraryId(int bookId, int libraryId) {
        return reservationDao.findAllByBookIdAndLibraryId(bookId, libraryId);
    }

    @Override
    public List<Reservation> findAllByRegisteredUser(int registeredUser) {
        return reservationDao.findAllByRegisteredUser(registeredUser);
    }


}
