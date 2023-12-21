/*
package com.fabienit.biblioapi.model.beans;

import com.fabienit.biblioapi.manager.impl.BorrowManagerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BorrowTest {

    private Reservation borrow;

    @MockBean
    BorrowManagerImpl classUnderTest;

    @MockBean
    List<Aircraft> aircraftList;

    @Test
    public void testGetBorrow() {

        // GIVEN
        Reservation borrowToSave = new Reservation();
        borrowToSave.setId(1);
        borrowToSave.setBookReturned(false);
        borrowToSave.setBorrowDate(LocalDate.of(2022,3,20));
        borrowToSave.setExtendedDuration(false);
        borrowToSave.setReturnDate(null);
        borrowToSave.setLibrary(null);
        borrowToSave.setRegistereduser(null);
        borrowToSave.toString();

        // WHEN
        int id = borrowToSave.getId();
        boolean bookReturned = borrowToSave.getBookReturned();
        LocalDate borrowDate = borrowToSave.getBorrowDate();
        boolean extendedDuration = borrowToSave.getExtendedDuration();
        LocalDate returnDate = borrowToSave.getReturnDate();
        Library library = borrowToSave.getLibrary();
        RegisteredUser registeredUser = borrowToSave.getRegistereduser();
        String toString = borrowToSave.toString();

        // THEN
        assertEquals(1, id);
        assertEquals(false, bookReturned);
        assertEquals(LocalDate.of(2022,3,20), borrowDate);
        assertEquals(false, extendedDuration);
        assertEquals(null, returnDate);
        assertEquals(null, library);
        assertEquals(null, registeredUser);
        assertEquals(toString, borrowToSave.toString());

    }

    @Test
    public void testSetBorrow() {

        // GIVEN
        Reservation borrowToSave = new Reservation();


        // WHEN
        borrowToSave.setId(1);
        borrowToSave.setBookReturned(false);
        borrowToSave.setBorrowDate(LocalDate.of(2022,3,20));
        borrowToSave.setExtendedDuration(false);
        borrowToSave.setReturnDate(null);
        borrowToSave.setLibrary(null);
        borrowToSave.setRegistereduser(null);
        borrowToSave.toString();

        // THEN
        assertEquals(1, borrowToSave.getId());
        assertEquals(false, borrowToSave.getBookReturned());
        assertEquals(LocalDate.of(2022,3,20), borrowToSave.getBorrowDate());
        assertEquals(false, borrowToSave.getExtendedDuration());
        assertEquals(null, borrowToSave.getReturnDate());
        assertEquals(null, borrowToSave.getLibrary());
        assertEquals(null, borrowToSave.getRegistereduser());
        assertNotNull(borrowToSave.toString());

    }

    @Test
    public void testBorrowConstructor() {

        Reservation borrowToSave = new Reservation(1,null,null,null,LocalDate.of(2022,3,20),null,false,false);


        assertEquals(1, borrowToSave.getId());
        assertEquals(null, borrowToSave.getBook());
        assertEquals(null, borrowToSave.getRegistereduser());
        assertEquals(null, borrowToSave.getLibrary());
        assertEquals(LocalDate.of(2022,3,20), borrowToSave.getBorrowDate());
        assertEquals(null, borrowToSave.getReturnDate());
        assertEquals(false, borrowToSave.getExtendedDuration());
        assertEquals(false, borrowToSave.getBookReturned());
    }

    @Test
    public void testBorrowConstructorII() {

        Reservation borrow = new Reservation();

        borrow.setId(1);
        borrow.setBookReturned(false);
        borrow.setBorrowDate(LocalDate.of(2022,3,20));
        borrow.setExtendedDuration(false);
        borrow.setReturnDate(null);
        borrow.setLibrary(null);
        borrow.setRegistereduser(null);
        borrow.toString();

        Reservation borrowToSave = new Reservation(borrow);


        assertEquals(1, borrowToSave.getId());
        assertEquals(null, borrowToSave.getBook());
        assertEquals(null, borrowToSave.getRegistereduser());
        assertEquals(null, borrowToSave.getLibrary());
        assertEquals(LocalDate.of(2022,3,20), borrowToSave.getBorrowDate());
        assertEquals(null, borrowToSave.getReturnDate());
        assertEquals(false, borrowToSave.getExtendedDuration());
        assertEquals(false, borrowToSave.getBookReturned());
    }


}
*/
