package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.dao.RegisteredUserDao;
import com.fabienit.flyingclub.dao.ReservationDao;
import com.fabienit.flyingclub.manager.EmailService;
import com.fabienit.flyingclub.manager.ReservationManager;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import com.fabienit.flyingclub.web.exceptions.FunctionnalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.*;

@Service
public class ReservationManagerImpl implements ReservationManager {

    private final ReservationDao reservationDao;
    private final AircraftDao aircraftDao;
    private final EmailService emailService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    public ReservationManagerImpl(ReservationDao reservationDao, AircraftDao aircraftDao, EmailService emailService) {
        this.reservationDao = reservationDao;
        this.aircraftDao = aircraftDao;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public Reservation deleteById(int id) {
        Reservation reservationToDelete = reservationDao.findById(id);
        reservationDao.deleteById(id);
        return reservationToDelete;
    }

    @Override
    @Transactional
    public Reservation save(Reservation reservation) throws FunctionnalException {

           if(reservation.isCanceled()){
               Map<String, Object> templateModel = new HashMap<>();
               templateModel.put("name", reservation.getRegisteredUser().getFirstName());
               templateModel.put("aircraftName", reservation.getAircraft().getMark());
               templateModel.put("borrowingDate", reservation.getBorrowingDate().toString());
               templateModel.put("returnDate", reservation.getReturnDate().toString());

               emailService.sendHtmlMessage(reservation.getRegisteredUser().getEmail(),
                       "Annulation de Réservation",
                       "annulationReservation",
                       templateModel);
               reservationDao.save(reservation);
           } else if (reservation.isFinished()) {

               Map<String, Object> templateModel = new HashMap<>();
               templateModel.put("name", reservation.getRegisteredUser().getFirstName());
               templateModel.put("aircraftName", reservation.getAircraft().getMark());
               templateModel.put("borrowingDate", reservation.getBorrowingDate().toString());
               templateModel.put("returnDate", reservation.getReturnDate().toString());
               templateModel.put("hours", reservation.getRegisteredUser().getHours());

               emailService.sendHtmlMessage(reservation.getRegisteredUser().getEmail(),
                       "Confirmation de clôturation de réservation",
                       "cloturationReservation",
                       templateModel);
           } else{
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("name", reservation.getRegisteredUser().getFirstName());
            templateModel.put("aircraftName", reservation.getAircraft().getMark());
            templateModel.put("borrowingDate", reservation.getBorrowingDate().toString());
            templateModel.put("returnDate", reservation.getReturnDate().toString());

            emailService.sendHtmlMessage(reservation.getRegisteredUser().getEmail(),
                    "Confirmation de Réservation",
                    "confirmationReservation",
                    templateModel);
           }

        return reservationDao.save(reservation);
    }



    public List<Aircraft> checkIfAircraftIsAvailable() throws FunctionnalException {
        List<Aircraft> aircraftsIsAvailableList = aircraftDao.findAll();
        return aircraftsIsAvailableList;

    }

    @Override
    public Reservation updateAircraftReservation(Reservation reservationBean) throws FunctionnalException {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", reservationBean.getRegisteredUser().getFirstName());
        templateModel.put("aircraftName", reservationBean.getAircraft().getMark());
        templateModel.put("borrowingDate", reservationBean.getBorrowingDate().toString());
        templateModel.put("returnDate", reservationBean.getReturnDate().toString());

        emailService.sendHtmlMessage(reservationBean.getRegisteredUser().getEmail(),
                "Modification de Réservation",
                "modificationReservation",
                templateModel);
        return reservationDao.save(reservationBean);
    }


    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }


    @Override
    public Reservation findById(int id) {
        return reservationDao.findById(id);
    }

    @Override
    public List<Reservation> findAllByRegisteredUser(int registeredUser) {
        return reservationDao.findAllByRegisteredUser(registeredUser);
    }

    @Override
    public boolean existsReservationByIdAndDate(int id, LocalDate startDate, LocalDate endDate) {
        System.out.println( "TEST DAO : " + reservationDao.existsReservationByIdAndDate(id, startDate, endDate));
        return reservationDao.existsReservationByIdAndDate(id, startDate, endDate);
    }

    @Override
    public List<Aircraft> getAvailableAircraftsToday() {
        List<Aircraft> availableAircrafts = new ArrayList<>();

        List<Reservation> reservations = reservationDao.findAll();

        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);

        if (reservations.isEmpty()) {
            availableAircrafts.addAll(aircraftDao.findAllByIsAvailableTrue());
        } else {

            for (Reservation reservation : reservations) {
                if (reservation.getReturnDate().isBefore(currentDate) && !reservation.getReturnDate().equals(currentDate)) {
                    Aircraft aircraft = aircraftDao.findById(reservation.getAircraft().getId()).orElse(null);
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





