package com.fabienit.biblioapi.manager.impl;

import com.fabienit.biblioapi.model.beans.Reservation;
import com.fabienit.biblioapi.manager.OutDatedReservationManager;
import com.fabienit.biblioapi.manager.ReservationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutDatedReservationManagerImpl implements OutDatedReservationManager {

    @Autowired
    private ReservationManager reservationManager;

    @Value( "${biblio.mode}" )
    private String biblioMode;

    public void setReservationManager(ReservationManager reservationManager) {
        this.reservationManager = reservationManager;
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Delete outdated reservation, 2 days old reservations are deleted.
     * @return
     */
    @Override
    public List<Reservation> deleteOutDatedReservations() {

        logger.debug("Deleting outdated reservations");
        logger.debug("The mode is : ***************" + biblioMode);

        // Get reservation list
        List<Reservation> reservations = reservationManager.findAll();

        // Init today date
        LocalDate today = LocalDate.now();

        List<Reservation> reservationListWithoutOutDatedReservation = new ArrayList<>();

        for (Reservation reservationBean: reservations) {
            if(reservationBean.getAvailabilityDate() != null && reservationBean.getNotificationIsSent()){
                deleteWithBiblioMode(today, reservationListWithoutOutDatedReservation, reservationBean);
            }else {
                reservationListWithoutOutDatedReservation.add(reservationBean);
            }
        }
        return reservationListWithoutOutDatedReservation;
    }

    private void deleteWithBiblioMode(LocalDate today, List<Reservation> reservationListWithoutOutDatedReservation, Reservation reservationBean) {
        if ("devmode".equals(biblioMode)){
            //TODO
        }else {
            if (reservationBean.getAvailabilityDate().isBefore(today.minusDays(2)) || reservationBean.getAvailabilityDate().isEqual(today.minusDays(2))){
                reservationManager.deleteById(reservationBean.getId());
            }else {
                reservationListWithoutOutDatedReservation.add(reservationBean);
            }
        }

    }

    /**
     * Delete outdated reservation, 2 days old reservations are deleted.
     * @return
     */

    public List<Reservation> deleteOutDatedReservationsDevMode() {

        logger.debug("Deleting outdated reservations");

        // Get reservation list
        List<Reservation> reservations = reservationManager.findAll();

        // Init today date
        LocalDate today = LocalDate.now();

        List<Reservation> reservationListWithoutOutDatedReservation = new ArrayList<>();

        for (Reservation reservationBean: reservations) {
            if(reservationBean.getAvailabilityDate() != null && reservationBean.getNotificationIsSent()){
                if (reservationBean.getAvailabilityDate().isBefore(today.minusDays(2)) || reservationBean.getAvailabilityDate().isEqual(today.minusDays(2))){
                    reservationManager.deleteById(reservationBean.getId());
                }else {
                    reservationListWithoutOutDatedReservation.add(reservationBean);
                }
            }else {
                reservationListWithoutOutDatedReservation.add(reservationBean);
            }
        }
        return reservationListWithoutOutDatedReservation;
    }

}
