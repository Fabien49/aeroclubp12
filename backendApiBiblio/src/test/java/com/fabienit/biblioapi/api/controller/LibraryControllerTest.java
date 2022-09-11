package com.fabienit.biblioapi.api.controller;


import com.fabienit.biblioapi.BiblioApiApplication;
import com.fabienit.biblioapi.model.beans.Library;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BiblioApiApplication.class)
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class LibraryControllerTest {

      @Autowired
      TestRestTemplate testRestTemplate;

   @BeforeEach
    public void beforeEach() {
        // because .withBasicAuth() creates a new TestRestTemplate with the same
        // configuration as the autowired one.
        testRestTemplate = testRestTemplate.withBasicAuth("fabien", "123456");
    }

    @Test
    public void whenGetLibraryList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/libraries", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void givenLibraryId_whenGetNonExistingLibrary_thenReturns200() {

        //given
        int libraryId = 1;

        //when
        ResponseEntity<Library> responseEntity = testRestTemplate.getForEntity("/libraries/"+libraryId, Library.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getName());
    }

    @Test
    public void givenNonExistentLibraryId_whenGetNonExistingLibrary_thenReturns404() {

        //given
        int libraryId = 999;

        //when
        ResponseEntity<Library> responseEntity = testRestTemplate.getForEntity("/books/"+libraryId, Library.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }

   @Test
   public void givenNewLibrary_whenPostLibrary_thenReturns201() {

      Library library = new Library();
      library.setId(1);
      library.setName("library");
      library.setAvailableCopies(null);
      library.setBorrows(null);

      //given
      HttpEntity<Library> request = new HttpEntity<>(library);

      //when
      ResponseEntity<Void> responseEntity = testRestTemplate
              .postForEntity("/libraries", request, Void.class);

      //then
      assertEquals(CREATED, responseEntity.getStatusCode());
      assertNotNull(responseEntity.getHeaders().getLocation());
   }

    @Test
    public void givenLibraryAndLibraryId_whenPutLibrary_thenReturns200() {

        //given
        int libraryId = 1;
        ResponseEntity<Library> libraryResponseEntity = testRestTemplate.getForEntity("/libraries/"+libraryId, Library.class);
        Library libraryToSave = libraryResponseEntity.getBody();
        HttpEntity<Library> request = new HttpEntity<>(libraryToSave);

        //when
        ResponseEntity<Library> responseEntity = testRestTemplate.exchange("/libraries/"+libraryId, HttpMethod.PUT,request, Library.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenLibraryAndLibraryId_whenDeleteLibrary_thenReturns200() {

        //given
        int libraryId = 1;
        ResponseEntity<Library> libraryResponseEntity = testRestTemplate.getForEntity("/libraries/"+libraryId, Library.class);
        Library libraryToSave = libraryResponseEntity.getBody();
        HttpEntity<Library> request = new HttpEntity<>(libraryToSave);

        //when
        ResponseEntity<Library> responseEntity = testRestTemplate.exchange("/libraries/"+libraryId, HttpMethod.DELETE,request, Library.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

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
