package com.fabienit.biblioapi.manager.impl.unit;

import com.fabienit.biblioapi.manager.BorrowManager;
import com.fabienit.biblioapi.manager.ReservationManager;
import com.fabienit.biblioapi.manager.impl.AvailableCopieManagerImpl;
import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import com.fabienit.biblioapi.model.beans.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AvailableCopieManagerImplTest {


    private AvailableCopieManagerImpl classUnderTest;

    private AvailableCopie copieToRefresh = new AvailableCopie(
            new AvailableCopieKey(1, 1),
            null,
            null,
            2,
            0,
            true, LocalDate.of(2020,05,12), 2);

    @Mock
    private List<Reservation> reservationList;

    @Mock
    private ReservationManager reservationManager;

    @Mock
    private BorrowManager borrowManager;


    @BeforeEach
    public void initBeforeEach() {
        classUnderTest = new AvailableCopieManagerImpl();
        classUnderTest.setReservationManager(reservationManager);
        classUnderTest.setBorrowManager(borrowManager);

        copieToRefresh = new AvailableCopie(
                new AvailableCopieKey(1, 1),
                null,
                null,
                2,
                0,
                true, LocalDate.of(2020,5,12) , 2);
    }

    @AfterEach
    public void undefAfterEach() {
        classUnderTest = null;
        copieToRefresh = null;
    }

    /* -------------------Test method updateStatusBookCanBeReserved------------------- */

    @Test
    public void Given_reservationCountIs2AndOwnedQuantityIs2_When_updateStatusBookCanBeReserved_Then_bookCanBeReservedIsTrue() {
        // GIVEN
        given(reservationManager.findAllByBookIdAndLibraryId(1,1)).willReturn(reservationList);
        given(reservationList.size()).willReturn(2);
        // WHEN
        final Boolean result = classUnderTest.updateStatusBookCanBeReserved(copieToRefresh);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    public void Given_reservationCountIs4AndOwnedQuantityIs2_When_updateStatusBookCanBeReserved_Then_bookCanBeReservedIsFalse() {
        // GIVEN
        given(reservationManager.findAllByBookIdAndLibraryId(1,1)).willReturn(reservationList);
        given(reservationList.size()).willReturn(4);
        // WHEN
        final Boolean result = classUnderTest.updateStatusBookCanBeReserved(copieToRefresh);
        // THEN
        assertThat(result).isFalse();
    }

    /* -------------------Test method updateAvailableQuantity------------------- */

    @Test
    public void Given_currentQuantityIs1AndOperationIsIn_When_updateAvailableQuantity_Then_resultEqual2() {
        // GIVEN

        // WHEN
        final int result = classUnderTest.updateAvailableQuantity(1, "in");
        // THEN
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void Given_currentQuantityIs1AndOperationIsOut_When_updateAvailableQuantity_Then_resultEqual0() {
        // GIVEN

        // WHEN
        final int result = classUnderTest.updateAvailableQuantity(1, "out");
        // THEN
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void Given_currentQuantityIs1AndOperationIsEmpty_When_updateAvailableQuantity_Then_resultEqual1() {
        // GIVEN

        // WHEN
        final int result = classUnderTest.updateAvailableQuantity(1, "");
        // THEN
        assertThat(result).isEqualTo(1);
    }
}
