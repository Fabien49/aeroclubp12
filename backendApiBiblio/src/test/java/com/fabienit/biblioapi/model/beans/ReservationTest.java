package com.fabienit.biblioapi.model.beans;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationTest {

    private Reservation reservation;

    @Test
    public void testGetReservation() {

        // GIVEN
        Reservation reservationToSave = new Reservation();
        reservationToSave.setId(1);
        reservationToSave.setAvailableCopie(null);
        reservationToSave.setRegistereduser(null);
        reservationToSave.setPosition(1);
        reservationToSave.setNotificationIsSent(true);
        reservationToSave.toString();

        // WHEN
        int id = reservationToSave.getId();
        AvailableCopie availableCopie = reservationToSave.getAvailableCopie();
        RegisteredUser registeredUser = reservationToSave.getRegistereduser();
        int position = reservationToSave.getPosition();
        boolean notificationIsSent = reservationToSave.getNotificationIsSent();
        String toString = reservationToSave.toString();

        // THEN
        assertEquals(1, id);
        assertEquals(null, availableCopie);
        assertEquals(null, registeredUser);
        assertEquals(1, position);
        assertEquals(true, notificationIsSent);
        assertEquals(toString, reservationToSave.toString());

    }

    @Test
    public void testSetReservation() {

        // GIVEN
        Reservation reservationToSave = new Reservation();


        // WHEN
        reservationToSave.setId(1);
        reservationToSave.setAvailableCopie(null);
        reservationToSave.setRegistereduser(null);
        reservationToSave.setPosition(1);
        reservationToSave.setNotificationIsSent(true);
        reservationToSave.toString();

        // THEN
        assertEquals(1, reservationToSave.getId());
        assertEquals(null, reservationToSave.getAvailableCopie());
        assertEquals(null, reservationToSave.getRegistereduser());
        assertEquals(1, reservationToSave.getPosition());
        assertEquals(true, reservationToSave.getNotificationIsSent());
        assertNotNull(reservationToSave.toString());
    }

}
