package com.fabienit.biblioweb.web.proxies;

import com.fabienit.biblioweb.model.beans.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * ApiProxy
 */
@FeignClient(name = "${api.name}", url = "${api.url}")
public interface ApiProxy {

        // Book methods

        @GetMapping(value = "/books")
        List<BookBean> getBooks(@RequestParam(required = false) String query);

        @GetMapping(value = "/books/{id}")
        Optional<BookBean> getBookById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/books")
        ResponseEntity<Void> addBook(@Valid @RequestBody BookBean book);

        @PutMapping(value = "/books/{id}")
        ResponseEntity<Void> updateBook(@PathVariable
                                        @Min(value = 1) int id, @Valid @RequestBody BookBean bookDetails);

        @DeleteMapping(value = "/books/{id}")
        void deleteBook(@PathVariable @Min(value = 1) int id);

        // AvailableCopie methods

        @GetMapping(value = "/availableCopies")
        List<AvailableCopieBean> getAvailableCopies();

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

        // Library methods

        @GetMapping(value = "/libraries")
        List<LibraryBean> getLibraries();

        @GetMapping(value = "/libraries/{id}")
        Optional<LibraryBean> getLibraryById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/libraries")
        ResponseEntity<Void> addLibrary(@Valid @RequestBody LibraryBean library);

        @PutMapping(value = "/libraries/{id}")
        ResponseEntity<Void> updateLibrary(@PathVariable @Min(value = 1) int id,
                                           @Valid @RequestBody LibraryBean libraryDetails);

        @DeleteMapping(value = "/libraries/{id}")
        void deleteLibrary(@PathVariable @Min(value = 1) int id);

        // RegisteredUser methods

        @GetMapping(value = "/users")
        List<RegisteredUserBean> getUsers();

        @GetMapping(value = "/users/{id}")
        Optional<RegisteredUserBean> getRegisteredUserById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/users")
        ResponseEntity<Void> addRegisteredUser(@Valid @RequestBody RegisteredUserBean registeredUser);

        @PutMapping(value = "/users/{id}")
        ResponseEntity<Void> updateRegisteredUser(@PathVariable @Min(value = 1) int id,
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
        List<ReservationBean> getReservations();

        @GetMapping(value = "/reservations/{id}")
        Optional<ReservationBean> getReservationById(@PathVariable @Min(value = 1) int id);

        @PostMapping(value = "/reservations")
        ResponseEntity<Void> addReservation(@Valid @RequestBody ReservationBean reservation);

        @PutMapping(value = "/reservations/{id}")
        ResponseEntity<Void> updateReservation(@PathVariable @Min(value = 1) int id, @Valid @RequestBody ReservationBean reservationDetails);

        @DeleteMapping(value="/reservations/{id}")
        void deleteReservation(@PathVariable @Min(value = 1) int id);

        @GetMapping(value = "/reservations/user/{id}")
        List<ReservationBean> getReservationByRegisteredUser(@PathVariable @Min(value = 1) int id);
}