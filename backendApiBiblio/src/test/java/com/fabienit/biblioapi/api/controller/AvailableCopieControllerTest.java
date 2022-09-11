package com.fabienit.biblioapi.api.controller;


import com.fabienit.biblioapi.BiblioApiApplication;
import com.fabienit.biblioapi.model.beans.AvailableCopie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BiblioApiApplication.class)
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AvailableCopieControllerTest {

      @Autowired
      TestRestTemplate testRestTemplate;

   @BeforeEach
    public void beforeEach() {
        // because .withBasicAuth() creates a new TestRestTemplate with the same
        // configuration as the autowired one.
        testRestTemplate = testRestTemplate.withBasicAuth("fabien", "123456");
    }

    @Test
    public void whenGetAvailableCopieList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/availableCopies", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void givenAvailableCopieId_whenGetNonExistingAvailableCopie_thenReturns200() {

        //given
        int bookId = 1;
        int libraryId = 1;

        //when
        ResponseEntity<AvailableCopie> responseEntity = testRestTemplate.getForEntity("/availableCopies/"+bookId +"/"+libraryId, AvailableCopie.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getNearestReturnDate());
    }

    @Test
    public void givenNonExistentAvailableCopieId_whenGetNonExistingAvailableCopie_thenReturns404() {

        //given
        int bookId = 999;
        int libraryId = 999;

        //when
        ResponseEntity<AvailableCopie> responseEntity = testRestTemplate.getForEntity("/availableCopies/"+bookId+"/"+libraryId, AvailableCopie.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }

/*    @Test
    public void givenAvailableCopieAndAvailableCopieId_whenPutAvailableCopie_thenReturns200() {

        //given
        int availableCopieId = 1;
        ResponseEntity<AvailableCopie> availableCopieResponseEntity = testRestTemplate.getForEntity("/availableCopies/"+availableCopieId, AvailableCopie.class);
        AvailableCopie availableCopieToSave = availableCopieResponseEntity.getBody();
        HttpEntity<AvailableCopie> request = new HttpEntity<>(availableCopieToSave);

        //when
        ResponseEntity<AvailableCopie> responseEntity = testRestTemplate.exchange("/availableCopies/"+availableCopieId, HttpMethod.PUT,request, AvailableCopie.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }*/

/*  @Test
   public void givenNewAvailableCopie_whenPostAvailableCopie_thenReturns201() {



      AvailableCopie availableCopieToSave = new AvailableCopie();
      AvailableCopieKey availableCopieKey = new AvailableCopieKey();
      availableCopieKey.setBookId(1);
      availableCopieKey.setLibraryId(1);
*//*      Book bookToSave = new Book(5, "title", "authorFirstName", "authorLastName", LocalDate.of(2022,3,20),"synopsis", "pictureURL", null, null);
      Library libraryToSave = new Library();*//*
      availableCopieToSave.setId(availableCopieKey);
      availableCopieToSave.setAvailableQuantity(2);
      availableCopieToSave.setNearestReturnDate(LocalDate.of(2022,3,20));
      availableCopieToSave.setOwnedQuantity(2);
      availableCopieToSave.setBookCanBeReserved(false);
      availableCopieToSave.setReservationCount(0);
      availableCopieToSave.setBook(null);
      availableCopieToSave.setLibrary(null);

      //given
      HttpEntity<AvailableCopie> request = new HttpEntity<>(availableCopieToSave);

      //when
      ResponseEntity<Void> responseEntity = testRestTemplate
              .postForEntity("/availableCopies", request, Void.class);

      //then
      assertEquals(CREATED, responseEntity.getStatusCode());
      assertNotNull(responseEntity.getHeaders().getLocation());
   }*/

/*    @Test
    public void givenExistingBookId_whenPostBook_thenReturns400() {

        //given
        Library  library = new  Library();
        library.setId(1);
        library.setName("Toussaint");
        library.setAvailableCopies(null);
        library.setBorrows(null);
        HttpEntity<Library> request = new HttpEntity<>(library);

        //when
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/libraries", request, Void.class);

        //then
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }*/

}
