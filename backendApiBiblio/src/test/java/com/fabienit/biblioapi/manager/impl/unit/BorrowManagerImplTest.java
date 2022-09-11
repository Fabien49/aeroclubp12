package com.fabienit.biblioapi.manager.impl.unit;


import com.fabienit.biblioapi.manager.impl.BorrowManagerImpl;
import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.web.exceptions.FunctionnalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BorrowManagerImplTest {

    private BorrowManagerImpl classUnderTest;

    /**
     *
     */
    @BeforeEach
    public void init() {
        classUnderTest  =  new BorrowManagerImpl();
    }

    @Test
    public void Given_borrowBeanWithOutDatedReturnDate_When_checkIfReturnDateIsOutDated_Then_shouldThrowFunctionnalException() throws FunctionnalException {
        // GIVEN
        Borrow borrow = new Borrow();
        borrow.setReturnDate(LocalDate.of(2020,1,1));
        // WHEN
        Exception exception = assertThrows(FunctionnalException.class, () -> {
            classUnderTest.checkIfReturnDateIsOutDated(borrow);
        });
    }

    @Test
    public void Given_borrowBeanAlreadyExtended_When_checkIfBorrowIsAlreadyExtended_Then_shouldThrowFunctionnalException() {
        // GIVEN
        Borrow borrow = new Borrow();
        borrow.setExtendedDuration(true);
        // WHEN
        Exception exception = assertThrows(FunctionnalException.class, () -> {
            classUnderTest.checkIfBorrowIsAlreadyExtended(borrow);
        });
        // THEN

    }
}
