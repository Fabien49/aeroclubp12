package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.dao.ReservationDao;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AircraftManagerImplTest {
    private AircraftManagerImpl aircraftManager;

    @Mock
    private AircraftDao aircraftDao;

    @Mock
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        aircraftManager = new AircraftManagerImpl(aircraftDao, reservationDao);
    }

    @Test
    void findAll() {
        List<Aircraft> expectedAircrafts = Arrays.asList(new Aircraft(), new Aircraft());
        when(aircraftDao.findAll()).thenReturn(expectedAircrafts);

        List<Aircraft> actualAircrafts = aircraftManager.findAll();

        assertEquals(expectedAircrafts, actualAircrafts);
        verify(aircraftDao).findAll();
    }

    @Test
    void findById() {
        int id = 1;
        Optional<Aircraft> expectedAircraft = Optional.of(new Aircraft());
        when(aircraftDao.findById(id)).thenReturn(expectedAircraft);

        Optional<Aircraft> actualAircraft = aircraftManager.findById(id);

        assertEquals(expectedAircraft, actualAircraft);
        verify(aircraftDao).findById(id);
    }

    @Test
    void save() {
        Aircraft aircraft = new Aircraft();
        when(aircraftDao.save(aircraft)).thenReturn(aircraft);

        Aircraft savedAircraft = aircraftManager.save(aircraft);

        assertEquals(aircraft, savedAircraft);
        verify(aircraftDao).save(aircraft);
    }

    @Test
    void getAvailableAircraftsBetweenDates() {
        Aircraft aircraft1 = new Aircraft(1);
        Aircraft aircraft2 = new Aircraft(2);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(10);
        List<Aircraft> allAircrafts = Arrays.asList(aircraft1, aircraft2);
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setAircraft(aircraft2);
        reservations.add(reservation);

        when(aircraftDao.findAllByIsAvailableTrue()).thenReturn(allAircrafts);
        when(reservationDao.findAllReservationForAircraftReservation(startDate, endDate)).thenReturn(reservations);

        List<Aircraft> availableAircrafts = aircraftManager.getAvailableAircraftsBetweenDates(startDate, endDate);
        System.out.println("la liste des avions disponibles : " + availableAircrafts);

        assertEquals(1, availableAircrafts.size());
        assertTrue(availableAircrafts.contains(aircraft1));
        verify(aircraftDao).findAllByIsAvailableTrue();
        verify(reservationDao).findAllReservationForAircraftReservation(startDate, endDate);
    }

    @Test
    void getAircraftByReservationId() {
        int id = 1;
        Aircraft expectedAircraft = new Aircraft();
        when(aircraftDao.findAircraftByReservationId(id)).thenReturn(expectedAircraft);

        Aircraft actualAircraft = aircraftManager.getAircraftByReservationId(id);

        assertEquals(expectedAircraft, actualAircraft);
        verify(aircraftDao).findAircraftByReservationId(id);
    }

    @Test
    void findAllByIsAvailableTrue() {
        List<Aircraft> expectedAircrafts = Arrays.asList(new Aircraft());
        when(aircraftDao.findAllByIsAvailableTrue()).thenReturn(expectedAircrafts);

        List<Aircraft> actualAircrafts = aircraftManager.findAllByIsAvailableTrue();

        assertEquals(expectedAircrafts, actualAircrafts);
        verify(aircraftDao).findAllByIsAvailableTrue();
    }

    @Test
    void existsAircraftById() {
        int id = 1;
        when(aircraftDao.existsAircraftById(id)).thenReturn(true);

        boolean exists = aircraftManager.existsAircraftById(id);

        assertTrue(exists);
        verify(aircraftDao).existsAircraftById(id);
    }

    @Test
    void deleteById() {
        int id = 1;
        doNothing().when(aircraftDao).deleteById(id);

        Aircraft deletedAircraft = aircraftManager.deleteById(id);

        assertNull(deletedAircraft);
        verify(aircraftDao).deleteById(id);
    }
}