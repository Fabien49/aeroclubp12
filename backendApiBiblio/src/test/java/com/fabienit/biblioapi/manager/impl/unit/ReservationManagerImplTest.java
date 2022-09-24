package com.fabienit.biblioapi.manager.impl.unit;

import com.fabienit.biblioapi.dao.ReservationDao;
import com.fabienit.biblioapi.manager.AvailableCopieManager;
import com.fabienit.biblioapi.manager.BorrowManager;
import com.fabienit.biblioapi.manager.impl.ReservationManagerImpl;
import com.fabienit.biblioapi.model.beans.*;
import com.fabienit.biblioapi.web.exceptions.FunctionnalException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationManagerImplTest {

    private ReservationManagerImpl classUnderTest;
    @Mock
    private AvailableCopieManager availableCopieManager;
    @Mock
    private BorrowManager borrowManager;
    @Mock
    private ReservationDao reservationDao;
    @Mock
    private AvailableCopie availableCopie;
    @Mock
    private List<Borrow> borrowList;
    @Mock
    private Borrow borrow;
    private List<Reservation> reservationList;
    private Reservation reservation;

    /**
     *
     */
    @BeforeEach
    public void init() {
        classUnderTest = new ReservationManagerImpl();
        classUnderTest.setAvailableCopieManager(availableCopieManager);
        classUnderTest.setBorrowManager(borrowManager);
        classUnderTest.setReservationDao(reservationDao);
        availableCopie = new AvailableCopie();
        availableCopie.setOwnedQuantity(2);
        availableCopie.setReservationCount(4);
        borrowList = new ArrayList<>();
        borrow = new Borrow();
        borrow.setBookReturned(false);
        Book book = new Book();
        book.setId(1);
        borrow.setBook(book);
        borrowList.add(borrow);
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
        availableCopieManager = null;
        availableCopie = null;
        borrow = null;
        borrowList = null;
        reservationList = null;
        reservation = null;
    }


    @Test
    public void Given_listOfReservationIsFull_When_checkIfReservationListIsFull_Then_throwsFunctionnalException() {
        // GIVEN
        given(availableCopieManager.findById(new AvailableCopieKey(1,1))).willReturn(Optional.ofNullable(availableCopie));
        // WHEN
        Exception exception = assertThrows(FunctionnalException.class, () -> {
        classUnderTest.checkIfReservationListIsFull(1,1);
        });
        // THEN

    }

    @Test
    public void Given_bookIsAlreadyBorrowed_When_checkIfBookIsAlreadyBorrowed_Then_throwsFunctionnalException() {
        // GIVEN
        given(borrowManager.getAllBorrowsByRegistereduserId(1)).willReturn(borrowList);
        // WHEN
        Exception exception = assertThrows(FunctionnalException.class, () -> {
            classUnderTest.checkIfBookIsAlreadyBorrowed(1,1);
        });
        // THEN

    }

    @Test
    public void Given_bookIsAlreadyReserved_When_checkIfBookIsAlreadyReserved_Then_throwsFunctionnalException() {
        // GIVEN
        given(reservationDao.findAllByRegisteredUser(1)).willReturn(reservationList);
        // WHEN
        Exception exception = assertThrows(FunctionnalException.class, () -> {
            classUnderTest.checkIfBookIsAlreadyReserved(1,1);
        });
        // THEN

    }

    @Test
    public void Given_reservationBean_When_updateReservationAfterNotification_Then_getNotificationIsSentShouldBeTrue() throws FunctionnalException {
        // GIVEN

        // WHEN
        final Reservation result = classUnderTest.updateReservationAfterNotification(reservation);
        // THEN
        assertThat(result.getNotificationIsSent()).isTrue();

    }

    @Test
    public void Given_reservationBean_When_updateReservationAfterNotification_Then_getAvailabilityDateShouldBeToday() throws FunctionnalException {
        // GIVEN
        LocalDate localDate = LocalDate.now();
        // WHEN
        final Reservation result = classUnderTest.updateReservationAfterNotification(reservation);
        // THEN
        assertThat(result.getAvailabilityDate()).isEqualTo(localDate);
    }


}
