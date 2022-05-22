package com.fabienit.biblioapi.model.beans;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationTest {

    @Autowired
    RegisteredUser registeredUser;


    @Test
    void reservationTrue(){
        Reservation reservation = new Reservation();
        Assert.assertNotNull(reservation);

    }



   @Test
    void reservationCreateWithAvailableCopie(){
        AvailableCopie availableCopie = new AvailableCopie();
        availableCopie.setOwnedQuantity(5);
        availableCopie.setAvailableQuantity(3);
        Reservation reservation = new Reservation();
        reservation.setAvailableCopie(availableCopie);
        System.out.println(reservation);
    }


}
