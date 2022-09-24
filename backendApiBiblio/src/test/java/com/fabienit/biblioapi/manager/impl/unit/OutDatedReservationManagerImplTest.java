package com.fabienit.biblioapi.manager.impl.unit;


import com.fabienit.biblioapi.manager.ReservationManager;
import com.fabienit.biblioapi.manager.impl.OutDatedReservationManagerImpl;
import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.model.beans.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OutDatedReservationManagerImplTest {

    private OutDatedReservationManagerImpl classUnderTest;
    @Mock
    private ReservationManager reservationManager;
    @Mock
    private AvailableCopie availableCopie;
    @Mock
    private List<Borrow> borrowList;
    @Mock
    private Borrow borrow;
    @Mock
    private List<Reservation> reservationList;
    @Mock
    private Reservation reservation;

    /**
     *
     */
    @BeforeEach
    public void init() {
        classUnderTest = new OutDatedReservationManagerImpl();
        classUnderTest.setReservationManager(reservationManager);
        /*availableCopie = new AvailableCopie();
        availableCopie.setOwnedQuantity(2);
        availableCopie.setReservationCount(4);
        borrowList = new ArrayList<>();
        borrow = new Borrow();
        borrow.setBookReturned(false);*/
        /*Book book = new Book();
        book.setId(1);
        borrow.setBook(book);
        borrowList.add(borrow);*/
        reservationList = new ArrayList<>();
        reservation = new Reservation();
        AvailableCopie availableCopie = new AvailableCopie();
        availableCopie.setId(new AvailableCopieKey(1,1));
        reservation.setAvailableCopie(availableCopie);
        reservationList.add(reservation);
    }

    /**
     *
     */
    @AfterEach
    public void undef() {
        classUnderTest = null;
        availableCopie = null;
        borrow = null;
        borrowList = null;
        reservationList = null;
        reservation = null;
    }

    @Test
    public void Given__When__Then_() {
        // GIVEN
        Reservation outDatedReservation = new Reservation();
        outDatedReservation.setAvailabilityDate(LocalDate.of(2022,8,30));
        outDatedReservation.setNotificationIsSent(true);
        reservationList.add(outDatedReservation);
        given(reservationManager.findAll()).willReturn(reservationList);
        doReturn(null).when(reservationManager).deleteById(1);
        // WHEN
        final List<Reservation> result = classUnderTest.deleteOutDatedReservations();
        // THEN
        assertThat(result.size()).isEqualTo(1);
    }
}
