package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.dao.RegisteredUserDao;
import com.fabienit.flyingclub.dao.ReservationDao;
import com.fabienit.flyingclub.manager.ReservationManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import com.fabienit.flyingclub.web.exceptions.FunctionnalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class ReservationManagerImpl implements ReservationManager {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private AircraftDao aircraftDao;

    @Autowired
    private RegisteredUserDao registerDao;

    @PersistenceContext
    private EntityManager entityManager;

    public void setReservationDao(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     *
     */
    @Override
    public void relatedReservationUpdate(Integer aircraftId, Integer userId) {
        Optional<Reservation> relatedReservation = reservationDao.findByAircraftAndRegisteredUser(aircraftId, userId);

        if (relatedReservation.isPresent()) {
            deleteById(relatedReservation.get().getId());
        }
    }

    /**
     * @return
     */
    @Override
    @Transactional
    public Reservation deleteById(int id) {
        Reservation reservationToDelete = reservationDao.findById(id).get();
        reservationDao.deleteById(id);
        return reservationToDelete;
    }

    /**
     * After delete reservation action, decrease by one each reservations positions for the same copy
     */
/*
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
*/

    /**
     *
     */
/*    @Override
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
    }*/
    @Override
    @Transactional
    public Reservation save(Reservation reservation) throws FunctionnalException {

        return reservationDao.save(reservation);
    }

/*    private void checkReservation(Reservation reservation) throws FunctionnalException {

        int bookId = reservation.getAvailableCopie().getId().getBookId();
        int libraryId = reservation.getAvailableCopie().getId().getLibraryId();
        int userId = reservation.getRegistereduser().getId();

        checkIfReservationListIsFull(bookId, libraryId);
        checkIfBookIsAlreadyBorrowed(bookId, userId);
        checkIfBookIsAlreadyReserved(bookId, userId);

    }*/

    /**
     * Check if the reservation list is full, reservation list size must be equal to owned quantity x 2
     */
/*    public void checkIfReservationListIsFull(int bookId, int libraryId) throws FunctionnalException {
        AvailableCopie availableCopie = availableCopieManager.findById(new AvailableCopieKey(bookId, libraryId)).get();
        int reservationCountMax = availableCopie.getOwnedQuantity() * 2;
        if (availableCopie.getReservationCount() >= reservationCountMax) throw new FunctionnalException("La liste de reservation est pleine");
    }*/

    /**
     * Check if user already has an active borrow for this book
     */
/*    public void checkIfBookIsAlreadyBorrowed(int bookId, int userId) throws FunctionnalException {
        List<Reservation> borrowList = borrowManager.getAllBorrowsByRegistereduserId(userId);
        for (Reservation borrow: borrowList) {
            if (!borrow.getBookReturned() && borrow.getBook().getId() == bookId)
                throw new FunctionnalException("L'utilisateur a déjà un emprunt en cours concernant ce livre");
        }
    }*/

    /**
     * Check if user already has a reservation for this aircraft
     */
/*    public void checkIfAircraftIsAlreadyReserved(int aircraftId, int userId) throws FunctionnalException {
        List<Reservation> reservationList = findAllByRegisteredUser(userId);
        for (Reservation reservationBean: reservationList) {
            if (reservationBean.getId().getAircraftId() == aircraftId)
                throw new FunctionnalException("L'utilisateur a déjà une reservation en cours concernant ce livre");
        }
    }*/
    public List<Aircraft> checkIfAircraftIsAvailable() throws FunctionnalException {
        List<Aircraft> aircraftsIsAvailableList = aircraftDao.findAll();
        return aircraftsIsAvailableList;

    }


    /**
     *
     */
/*    public int setReservationPosition(int bookId, int libraryId,int reservationPosition) {
        if (reservationPosition == 0) {
            List<Reservation> reservationList =
                    reservationDao.findAllByBookIdAndLibraryId(bookId, libraryId);
            return reservationList.size() + 1;
        }
        return reservationPosition;
    }*/


    /**
     * Update bean reservation with NotificationIsSent parameter set to true and AvailabilityDate set to today's date
     *
     * @return
     */
    @Override
    public Reservation updateReservationAfterNotification(Reservation reservation) throws FunctionnalException {
        //TODO à sauver en base non ?
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

    @Override
    public List<Reservation> findAllByBorrowwingDate(Date borrowingDate) {
        return null;
    }

    @Override
    public List<Reservation> findAllByReturnDate(Date returnDate) {
        return null;
    }

    /**
     *
     */


    @Override
    public List<Reservation> findAllByRegisteredUser(int registeredUser) {
        return reservationDao.findAllByRegisteredUser(registeredUser);
    }
    @Override
    public boolean isAircraftAvailable(Date date, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            Date startDate = reservation.getBorrowingDate();
            Date endDate = reservation.getReturnDate();

            // Vérifiez si la date spécifiée est exactement égale à startDate ou endDate
            if (date.equals(startDate) || date.equals(endDate)) {
                return false; // Avion indisponible à la date spécifiée
            }

            // Vérifiez si la date spécifiée est entre startDate et endDate
            if (date.after(startDate) && date.before(endDate)) {
                return false; // Avion indisponible à la date spécifiée
            }
        }

        return true; // Avion disponible à la date spécifiée
    }

    @Override
    public List<Aircraft> getAvailableAircraftsBetweenDates(Date startDate, Date endDate) {
        List<Aircraft> availableAircrafts = new ArrayList<>();
        /*List<Reservation> reservations = reservationDao.findAll();*/
        List<Aircraft> allAircrafts = aircraftDao.findAll();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        Date currentDate = calendar.getTime();
        for (Aircraft aircraft:allAircrafts
             ) {
           if (isAircraftAvailable(currentDate, aircraft.getReservations())){
               availableAircrafts.add(aircraft);
            }
        }



       /* while (!calendar.getTime().after(endDate)) {


            // Vérifiez si l'avion est disponible à la date actuelle
            if (isAircraftAvailable(currentDate, reservations)) {
                // Si disponible, ajoutez-le à la liste des avions disponibles
                availableAircrafts.addAll(aircraftDao.findAll());
            }

            // Passez à la date suivante
            calendar.add(Calendar.DATE, 1);
        }*/

        return availableAircrafts;
    }


    @Override
    public List<Aircraft> getAvailableAircraftsToday() {
        List<Aircraft> availableAircrafts = new ArrayList<>();

        // Récupérez toutes les réservations de la base de données
        List<Reservation> reservations = reservationDao.findAll();

        // Obtenez la date du jour
        Date currentDate = new Date();
        System.out.println(currentDate);

        // Vérifiez si la liste des réservations est vide
        if (reservations.isEmpty()) {
            // Si la liste est vide, ajoutez tous les avions à la liste des avions disponibles
            availableAircrafts.addAll(aircraftDao.findAllByIsAvailableTrue());
        } else {

            // Parcourez les réservations
            for (Reservation reservation : reservations) {
                // Vérifiez si la date de retour est passée
                if (reservation.getReturnDate().before(currentDate) && !reservation.getReturnDate().equals(currentDate)) {
                    // Si la date de retour est passée, récupérez l'avion associé à cette réservation
                    Aircraft aircraft = aircraftDao.findById(reservation.getAircraft().getId()).orElse(null);

                    // Assurez-vous que l'avion existe et n'est pas déjà dans la liste
                    if (aircraft != null && !availableAircrafts.contains(aircraft)) {
                        availableAircrafts.add(aircraft);
                    }
                    System.out.println(availableAircrafts);
                }
            }
        }

            return availableAircrafts;
        }
    }





