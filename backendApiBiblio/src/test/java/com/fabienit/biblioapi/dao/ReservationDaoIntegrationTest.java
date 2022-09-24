package com.fabienit.biblioapi.dao;


import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import com.fabienit.biblioapi.model.beans.RegisteredUser;
import com.fabienit.biblioapi.model.beans.Reservation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationDaoIntegrationTest {

    @Autowired
    private ReservationDao classUnderTest;

    @Autowired
    private RegisteredUserDao registeredUserDao;

    @Autowired
    private AvailableCopieDao availableCopieDao;

    Reservation reservation;
    RegisteredUser user;
    AvailableCopieKey availableCopieKey;
    AvailableCopie availableCopie;
    ObjectMapper mapper;

    @BeforeEach
    public void initBeforeEachTest() {
        mapper = new ObjectMapper();
        user = registeredUserDao.findById(1).get();
        availableCopieKey = new AvailableCopieKey(3,3);
        availableCopie = availableCopieDao.findById(availableCopieKey).get();

        reservation = new Reservation();
        reservation.setAvailabilityDate(null);
        reservation.setNotificationIsSent(false);
        reservation.setRegistereduser(user);
        reservation.setAvailableCopie(availableCopie);
    }

    @AfterEach
    public void undef() {
        reservation = null;
    }


    @Test
    public void Given_reservationId_When_findById_Then_shouldReturn1() {
        // GIVEN
        int reservationId = 1;
        // WHEN
        final Optional<Reservation> result = classUnderTest.findById(reservationId);
        // THEN
        assertThat(result.get().getId()).isEqualTo(1);
    }

    @Test
    public void Given__When_findAll_Then_shouldReturn2() {
        // GIVEN

        // WHEN
        final List<Reservation> result = classUnderTest.findAll();
        // THEN
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void Given__When_findAll_Then_shouldReturnListOfReservation() {
        // GIVEN

        // WHEN
        final List<Reservation> result = classUnderTest.findAll();
        // THEN
        assertThat(result.get(0)).isInstanceOf(Reservation.class);
    }

    @Test
    @Transactional
    public void Given_entryCountBeforeAdd_When_saveReservation_Then_shouldReturnEntryCountBeforeAddMore1() {
        // GIVEN
        int entryCountBeforeAdd = classUnderTest.findAll().size();
        // WHEN
        classUnderTest.save(reservation);
        // THEN
        int entryCountAfterAdd = classUnderTest.findAll().size();
        assertThat(entryCountAfterAdd).isEqualTo(entryCountBeforeAdd + 1);
    }

    @Test
    @Transactional
    public void Given_entryCountBeforeAdd_When_saveReservation_Then_shouldReturnAddedBeanToString() throws JsonProcessingException {
        // GIVEN
        int entryCountBeforeAdd = classUnderTest.findAll().size();
        // WHEN
        final Reservation result = classUnderTest.save(reservation);
        // THEN
        reservation.setId(result.getId());
        String expectedToJson = mapper.writeValueAsString(reservation);
        final String resultToJson = mapper.writeValueAsString(result);
        assertThat(resultToJson).isEqualTo(expectedToJson);
    }

    @Test
    @Transactional
    public void Given_reservationBeanUpdated_When_updateReservation_Then_shouldReturnUpdatedBeanToString() throws JsonProcessingException {
        // GIVEN
        reservation.setId(2);
        // WHEN
        classUnderTest.save(reservation);
        // THEN
        String expectedToJson = mapper.writeValueAsString(reservation);
        final Reservation result = classUnderTest.findById(2).get();
        final String resultToJson = mapper.writeValueAsString(result);
        assertThat(resultToJson).isEqualTo(expectedToJson);
    }

    @Test
    @Transactional
    public void Given_reservationBeanUpdated_When_updateReservation_Then_shouldReturnSameCountEntry() {
        // GIVEN
        reservation.setId(2);
        int entryCountBeforeUpdate = classUnderTest.findAll().size();
        // WHEN
        classUnderTest.save(reservation);
        // THEN
        int entryCountAfterUpdate = classUnderTest.findAll().size();
        assertThat(entryCountAfterUpdate).isEqualTo(entryCountBeforeUpdate);

    }

    @Test
    @Transactional
    public void Given_entryCountBeforeDelete_When_deleteReservation_Then_shouldReturnEntryCountBeforeDeleteLess1() {
        // GIVEN
        int entryCountBeforeDelete = classUnderTest.findAll().size();
        // WHEN
        classUnderTest.deleteById(1);
        // THEN
        int entryCountAfterDelete = classUnderTest.findAll().size();
        assertThat(entryCountAfterDelete).isEqualTo(entryCountBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void Given__When_deleteReservationId1_Then_shouldReturnFalse() {
        // GIVEN

        // WHEN
        classUnderTest.deleteById(1);
        // THEN
        final Optional<Reservation> result = classUnderTest.findById(1);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void Given_twoReservationRecordedInDatabase_When_findAllByBookIdAndLibraryId_Then_shoudReturn2() {
        // GIVEN

        // WHEN
        final List<Reservation> result = classUnderTest.findAllByBookIdAndLibraryId(1,1);
        // THEN
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void Given_ListReservation_When_findByUserId_Then_shouldReturn1() {
        // GIVEN
        int registeredUserId = 2;
        // WHEN
        final List<Reservation> result = classUnderTest.findAllByRegisteredUser(registeredUserId);
        // THEN
        assertThat(result.size()).isEqualTo(1);
    }

}
