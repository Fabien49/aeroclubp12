package com.fabienit.biblioapi.model.beans;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AvailableCopieTest {

    private AvailableCopie availableCopie;

    @Test
    public void testGetAvailableCopie() {

        // GIVEN
        AvailableCopie availableCopieToSave = new AvailableCopie();
        availableCopieToSave.setLibrary(null);
        availableCopieToSave.setAvailableQuantity(0);
        availableCopieToSave.setReservationCount(1);
        availableCopieToSave.setOwnedQuantity(3);
        availableCopieToSave.setBookCanBeReserved(true);
        availableCopieToSave.setBook(null);
        availableCopieToSave.setNearestReturnDate(LocalDate.of(2022,3,20));
        availableCopieToSave.toString();

        // WHEN

        Library library = availableCopieToSave.getLibrary();
        Book book = availableCopieToSave.getBook();
        int availableQuantity = availableCopieToSave.getAvailableQuantity();
        int reservationCount = availableCopieToSave.getReservationCount();
        int ownedQuantity = availableCopieToSave.getOwnedQuantity();
        boolean bookCanBeReserved = availableCopieToSave.getBookCanBeReserved();
        LocalDate nearestReturnDate = availableCopieToSave.getNearestReturnDate();
        String toString = availableCopieToSave.toString();

        // THEN
        assertNull(library);
        assertNull(book);
        assertEquals(0, availableQuantity);
        assertEquals(1, reservationCount);
        assertEquals(3, ownedQuantity);
        assertTrue(bookCanBeReserved);
        assertEquals(LocalDate.of(2022,3,20), nearestReturnDate);
        assertEquals(toString, availableCopieToSave.toString());

    }

    @Test
    public void testSetAvailableCopie() {

        // GIVEN
        AvailableCopie availableCopieToSave = new AvailableCopie();


        // WHEN
        availableCopieToSave.setLibrary(null);
        availableCopieToSave.setAvailableQuantity(0);
        availableCopieToSave.setReservationCount(1);
        availableCopieToSave.setOwnedQuantity(3);
        availableCopieToSave.setBookCanBeReserved(true);
        availableCopieToSave.setBook(null);
        availableCopieToSave.setNearestReturnDate(LocalDate.of(2022,3,20));
        availableCopieToSave.toString();

        // THEN
        assertNull(availableCopieToSave.getLibrary());
        assertNull(availableCopieToSave.getBook());
        assertEquals(0, availableCopieToSave.getAvailableQuantity());
        assertEquals(1, availableCopieToSave.getReservationCount());
        assertEquals(3, availableCopieToSave.getOwnedQuantity());
        assertTrue(availableCopieToSave.getBookCanBeReserved());
        assertEquals(LocalDate.of(2022,3,20), availableCopieToSave.getNearestReturnDate());
    }

    @Test
    public void testAvailableCopieConstructor() {

        AvailableCopie availableCopieToSave = new AvailableCopie(new AvailableCopieKey(), null, null, 3, 0, true, LocalDate.of(2022,3,20), 1);


        assertNull(availableCopieToSave.getBook());
        assertNull(availableCopieToSave.getLibrary());
        assertEquals(3, availableCopieToSave.getOwnedQuantity());
        assertEquals(LocalDate.of(2022,3,20), availableCopieToSave.getNearestReturnDate());
        assertEquals(0, availableCopieToSave.getAvailableQuantity());
        assertTrue(availableCopieToSave.getBookCanBeReserved());
        assertEquals(1,availableCopieToSave.getReservationCount());
    }

}
