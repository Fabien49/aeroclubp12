package com.fabienit.biblioapi.manager;

import com.fabienit.biblioapi.model.beans.Reservation;

import java.util.List;

public interface OutDatedReservationManager {
    List<Reservation> deleteOutDatedReservations();
}
