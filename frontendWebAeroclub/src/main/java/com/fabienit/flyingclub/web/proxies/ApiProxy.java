package com.fabienit.flyingclub.web.proxies;

import com.fabienit.flyingclub.model.beans.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ApiProxy
 */
@FeignClient(name = "${api.name}", url = "${api.url}")
public interface ApiProxy {

        // Aircraft methods

        @GetMapping(value = "/aircrafts")
        List<AircraftBean> getAircrafts();

        @GetMapping(value = "/aircraftsAvailable")
        List<AircraftBean> getAvailableAircrafts();

        @GetMapping(value = "/aircrafts/{id}")
        AircraftBean getAircraftById(@PathVariable int id);

        @GetMapping(value = "/aircrafts")
        AircraftBean getAircraftByMark(@RequestParam @NotNull String mark);

        @GetMapping(value = "/aircrafts/reservation/{id}")
        AircraftBean getAircraftByReservationId(@PathVariable int id);

        @PostMapping(value = "/aircrafts")
        ResponseEntity<Void> addAircraft(@Valid @RequestBody AircraftBean aircraft);

        @PutMapping(value = "/aircrafts/{id}")
        AircraftBean updateAircraft(@PathVariable
                                        @Min(value = 1) int id, @Valid @RequestBody AircraftBean aircraftDetails);

        @DeleteMapping(value = "/aircrafts/{id}")
        void deleteAircraft(@PathVariable @Min(value = 1) int id);

        // AvailableCopie methods

/*        @GetMapping(value = "/availableCopies")
        List<AvailableCopieBean> getAvailableCopies();*/

        @GetMapping(value = "/availableCopies/{book_id}/{library_id}")
        Optional<AvailableCopieBean> getAvailableCopieById(@PathVariable(value = "book_id") @Min(value = 1) int bookId,
                                                           @PathVariable(value = "library_id") @Min(value = 1) int libraryId);

        @PostMapping(value = "/availableCopies")
        public ResponseEntity<Void> addAvalaibleCopie(@Valid @RequestBody AvailableCopieBean availableCopie);

        @PutMapping(value = "/availableCopies/{book_id}/{library_id}")
        public ResponseEntity<Void> updateAvailableCopie(@PathVariable(value = "book_id") @Min(value = 1) int bookId,
                                                         @PathVariable(value = "library_id") @Min(value = 1) int libraryId,
                                                         @Valid @RequestBody AvailableCopieBean updatedAvailableCopie);

        @DeleteMapping("/availableCopies/{book_id}/{library_id}")
        public void deleteAvailableCopie(@PathVariable(value = "book_id") @Min(value = 1) int bookId,
                                         @PathVariable(value = "library_id") @Min(value = 1) int libraryId);

        // FlyingClub methods

        @GetMapping(value = "/flyingClub")
        List<FlyingClubBean> getFlyingClub();

        @GetMapping(value = "/flyingClub/{id}")
        Optional<FlyingClubBean> getFlyingClubById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/flyingClub")
        ResponseEntity<Void> addFlyingClub(@Valid @RequestBody FlyingClubBean flyingClub);

        @PutMapping(value = "/flyingClub/{id}")
        ResponseEntity<Void> updateFlyingClub(@PathVariable @Min(value = 1) int id,
                                           @Valid @RequestBody FlyingClubBean flyingClubDetails);

        @DeleteMapping(value = "/flyingClub/{id}")
        void deleteFlyingClub(@PathVariable @Min(value = 1) int id);

        // RegisteredUser methods

        @GetMapping(value = "/users")
        List<RegisteredUserBean> getUsers();

        @GetMapping(value = "/users/{id}")
        RegisteredUserBean getRegisteredUserById(@PathVariable int id);

        @PostMapping(value = "/users")
        ResponseEntity<Void> addRegisteredUser(@Valid @RequestBody RegisteredUserBean registeredUser);

        @PutMapping(value = "/modify-hours/{id}/{action}/{hoursToAdd}")
        ResponseEntity<Void> modifyRegisteredUserHours(@PathVariable int id,
                                                       @PathVariable String action,
                                                       @PathVariable int hoursToAdd);

        @PutMapping(value = "/users/{id}")
       RegisteredUserBean updateRegisteredUser(@PathVariable @Min(value = 1) int id,
                                                  @Valid @RequestBody RegisteredUserBean registeredUserDetails);

        @DeleteMapping(value = "/users/{id}")
        void deleteRegisteredUser(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/users/email")
        RegisteredUserBean findUserByEmail(@RequestBody String email);

        // Borrows methods

        @GetMapping(value = "/users/{user_id}/borrows")
        List<BorrowBean> getBorrowsByUserId(@PathVariable(value = "user_id") @Min(value = 1) int userId);

        @GetMapping(value = "/borrows/{id}")
        BorrowBean getBorrowById(@PathVariable @Min(value = 1) int id);

        @PutMapping(value = "/borrows/{id}")
        ResponseEntity<Void> updateBorrow(@PathVariable @Min(value = 1) int id,
                                          @Valid @RequestBody BorrowBean borrowDetails);

        @PutMapping(value="/borrows/extend/{id}")
        ResponseEntity<Void> extendBorrow(@PathVariable @Min(value = 1) int id, @Valid @RequestBody BorrowBean borrowDetails);




        // Reservation methods

        @GetMapping(value = "/reservations")
        List<ReservationBean> getReservations(@RequestBody List<AircraftBean> availableAircrafts );

        @GetMapping(value = "/aircraftsAvailableToday")
        List<AircraftBean> getAvailableAircraftsToday();

        @GetMapping(value = "/updateAircraftsAvailable")
        List<AircraftBean> getAvailableAircraftsBetweenDates( @RequestParam("borrowingDate") LocalDate borrowingDate,
                                                              @RequestParam("returnDate") LocalDate returnDate);

    @GetMapping(value = "/existingReservation")
    boolean getReservationByIdAndDate(@RequestParam int id,  @RequestParam("borrowingDate") LocalDate borrowingDate,
                                                          @RequestParam("returnDate") LocalDate returnDate);

        @GetMapping(value = "/reservations/{id}")
        ReservationBean getReservationById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/reservations")
        ResponseEntity<Void> addReservation(@RequestBody ReservationBean reservationBean);

        @PutMapping(value = "/canceledReservation/{id}")
        ResponseEntity<Void> canceledReservation(@PathVariable @Min(value = 1) int id);

        @PutMapping(value = "/saveUpdateAircraftReservation/{id}")
        ResponseEntity<Void> updateAircraftReservation(@PathVariable int id, @Valid @RequestBody ReservationBean reservation);

        @PutMapping(value = "/reservations/{id}")
        ResponseEntity<Void> updateReservation(@PathVariable @Min(value = 1) int id, @RequestBody ReservationBean reservationDetails);

        @DeleteMapping(value="/reservations/{id}")
        void deleteReservation(@PathVariable @Min(value = 1) int id);

        @GetMapping(value = "/reservations/user/{id}")
        List<ReservationBean> getReservationByRegisteredUser(@PathVariable @Min(value = 1) int id);


        // Workshop methods

        @GetMapping(value = "/workshop")
        List<WorkshopBean> getWorkshops();

        @GetMapping(value = "/workshop/{id}")
        WorkshopBean getWorkshopById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/workshop")
        ResponseEntity<Void> addWorkshop(@Valid @RequestBody WorkshopBean workshopBean);

        @PutMapping(value = "/workshop/{id}")
        WorkshopBean updateWorkshop(@PathVariable
                                            @Min(value = 1) int id, @Valid @RequestBody WorkshopBean workshopDetails);

@PutMapping(value = "workshop/{id}")
    WorkshopBean saveIntervention(@PathVariable
                                  @Min(value = 1) int id, @Valid @RequestBody WorkshopBean workshopDetails);

        @DeleteMapping(value = "/workshop/{id}")
        void deleteWorkshop(@PathVariable @Min(value = 1) int id);



}