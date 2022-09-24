package com.fabienit.biblioapi.api.controller;


import com.fabienit.biblioapi.BiblioApiApplication;
import com.fabienit.biblioapi.model.beans.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BiblioApiApplication.class)
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationControllerTest {

      @Autowired
      TestRestTemplate testRestTemplate;

   @BeforeEach
    public void beforeEach() {
        // because .withBasicAuth() creates a new TestRestTemplate with the same
        // configuration as the autowired one.
        testRestTemplate = testRestTemplate.withBasicAuth("fabien", "123456");
    }

    @Test
    public void whenGetReservationList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/reservations", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void givenReservationId_whenGetExistingReservation_thenReturns200() {

        //given
        int reservationId = 1;

        //when
        ResponseEntity<Reservation> responseEntity = testRestTemplate.getForEntity("/reservations/"+reservationId, Reservation.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getPosition());
    }

    @Test
    public void whenGetReservationByRegisteredUserList_thenReturns200() {

        //given
        int reservationId = 1;
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(1);
        registeredUser.setRoles("USER");
        registeredUser.setEmail("fabienchapeau@gmail.com");
        registeredUser.setPassword("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a");
        registeredUser.setLastName("Chap");
        registeredUser.setFirstName("Fab");
        registeredUser.setBorrows(null);
        registeredUser.setReservations(null);

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/reservations/user/" +reservationId, List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void givenNonExistentReservationId_whenGetNonExistingReservation_thenReturns404() {

        //given
        int reservationId = 999;

        //when
        ResponseEntity<Reservation> responseEntity = testRestTemplate.getForEntity("/reservations/"+reservationId, Reservation.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
   public void givenNewReservation_whenPostReservation_thenReturns201() {

       Reservation reservationToSave = new Reservation(1, new AvailableCopie(new AvailableCopieKey(1, 1), new Book(), new Library(), 2, 2, true, LocalDate.of(2022,3,20),0), new RegisteredUser(1, "Fab", "Chap", "fabienchapeau@gmail.com", "$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a", "USER", null, null), true, LocalDate.of(2022,3,20), 1);

      //given
      HttpEntity<Reservation> request = new HttpEntity<>(reservationToSave);

      //when
      ResponseEntity<Void> responseEntity = testRestTemplate
              .postForEntity("/reservations", request, Void.class);

      //then
      assertEquals(CREATED, responseEntity.getStatusCode());
      assertNotNull(responseEntity.getHeaders().getLocation());
   }

    @Test
    public void givenReservationAndReservationId_whenPutReservation_thenReturns200() {

        //given
        int reservationId = 1;
        ResponseEntity<Reservation> reservationResponseEntity = testRestTemplate.getForEntity("/reservations/"+reservationId, Reservation.class);
        Reservation reservationToSave = reservationResponseEntity.getBody();
        HttpEntity<Reservation> request = new HttpEntity<>(reservationToSave);

        //when
        ResponseEntity<Reservation> responseEntity = testRestTemplate.exchange("/reservations/"+reservationId, HttpMethod.PUT,request, Reservation.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void whenDeleteReservationById_thenReturns200() {

        //given
        int reservationId = 1;
        ResponseEntity<Reservation> reservationResponseEntity = testRestTemplate.getForEntity("/reservations/"+reservationId, Reservation.class);
        Reservation reservationToSave = reservationResponseEntity.getBody();
        HttpEntity<Reservation> request = new HttpEntity<>(reservationToSave);

        //when
        ResponseEntity<Reservation> responseEntity = testRestTemplate.exchange("/reservations/"+reservationId, HttpMethod.DELETE,request, Reservation.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

}
