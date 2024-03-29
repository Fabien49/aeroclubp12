package com.fabienit.flyingclub.service;

import com.fabienit.flyingclub.model.beans.*;
import com.fabienit.flyingclub.model.dto.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * WebappService
 */
public interface WebappService {

    ResponseEntity<Void> createUser(RegisteredUserDto accountDto);

    int getAuthenticatedRegisteredUserId();

    Boolean getIsAuthenticated();

    RegisteredUserBean getRegisteredUserById(int id);

    List<RegisteredUserBean> getAllRegisteredUsers();

    RegisteredUserBean updateRegisteredUser(int id, RegisteredUserBean updateRegisteredUserFormDto);

    List<ReservationBean> getAllReservation();

    ReservationBean getReservationById(int id);

    List<ReservationBean> getReservationsByRegisteredUserId();

    boolean getReservationByIdAndDate(int id, LocalDate startDate, LocalDate endDate);

    ResponseEntity<Void> createReservation(ReservationDto reservationDto);

    ResponseEntity<Void> updateReservation(int id, ReservationBean reservationBean);

    ResponseEntity<Void> updateAircraftReservation(int id, ReservationBean reservationBean);

    ResponseEntity<Void> canceledReservation(int id);

    AircraftBean createAircraft(AircraftBean aircraftBean);

    AircraftBean getAircraftById (int id);

    AircraftBean getAircraftByReservationId (int id);

    AircraftBean updateAircraft(int id, AircraftBean aircraftBean);

    List<AircraftDto> getAvailableAircrafts();

    List<AircraftDto> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate);

    WorkshopBean getWorkshopBeanById (int id);

    ResponseEntity<Void> createWorkshop(WorkshopBean workshopBean);

    WorkshopBean updateWorkshopBean (int id, WorkshopBean workshopBean);

    WorkshopBean saveIntervention (int id, WorkshopBean workshopBean);

    ResponseEntity<Void> canceledIntervention (int id);


}