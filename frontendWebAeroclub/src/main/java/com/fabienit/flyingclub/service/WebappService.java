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

    RegisteredUserBean updateRegisteredUser(int id, RegisteredUserBean updateRegisteredUserFormDto);

    ResponseEntity<Void> modifyUserHours(int id, String action, int hoursToAdd);

    int getUserTotalHours(int id);

    List<BorrowBean> getActiveBorrowsByRegisteredUserId();

    ReservationBean getReservationById(int id);

    List<ReservationBean> getReservationsByRegisteredUserId();

    ResponseEntity<Void> createReservation(ReservationDto reservationDto);

    ResponseEntity<Void> updateReservation(int id, ReservationBean reservationBean);

    ResponseEntity<Void> canceledReservation(int id);

    AircraftBean createAircraft(AircraftBean aircraftBean);

    AircraftBean getAircraftById (int id);

    List<Integer> getAircraftIdReservationsList(int authenticatedUserId);

    AircraftBean updateAircraft(int id, AircraftBean aircraftBean);

    List<Integer> getBookIdActiveBorrowList();

    List<AircraftBean> checkIfAircraftIsAvailable();

    List<AircraftDto> getAvailableAircrafts();

    List<AircraftDto> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate);


    List<RegisteredUserReservationDto> getAllRegisteredUsersDTO();


    WorkshopBean getWorkshopBeanById (int id);

    ResponseEntity<Void> createWorkshop(WorkshopBean workshopBean);
    WorkshopBean updateWorkshopBean (int id, WorkshopBean workshopBean);

}