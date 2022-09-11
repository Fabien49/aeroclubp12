package com.fabienit.biblioapi.api.controller;


import com.fabienit.biblioapi.BiblioApiApplication;
import com.fabienit.biblioapi.model.beans.RegisteredUser;
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
public class RegisteredUserControllerTest {

      @Autowired
      TestRestTemplate testRestTemplate;

   @BeforeEach
    public void beforeEach() {
        // because .withBasicAuth() creates a new TestRestTemplate with the same
        // configuration as the autowired one.
        testRestTemplate = testRestTemplate.withBasicAuth("fabien", "123456");
    }

    @Test
    public void whenGetRegisteredUserList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/users", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void givenUserId_whenGetNonExistingRegisteredUser_thenReturns200() {

        //given
        int userId = 1;

        //when
        ResponseEntity<RegisteredUser> responseEntity = testRestTemplate.getForEntity("/users/"+userId, RegisteredUser.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getFirstName());
    }

    @Test
    public void givenNonExistentUserId_whenGetNonExistingRegisteredUser_thenReturns404() {

        //given
        int userId = 999;

        //when
        ResponseEntity<RegisteredUser> responseEntity = testRestTemplate.getForEntity("/users/"+userId, RegisteredUser.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }

   @Test
   public void givenNewRegisteredUser_whenPostRegisteredUser_thenReturns201() {

       RegisteredUser registeredUser = new RegisteredUser();
       registeredUser.setId(1);
       registeredUser.setRoles("USER");
       registeredUser.setEmail("fabienchapeau@gmail.com");
       registeredUser.setPassword("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a");
       registeredUser.setLastName("Chap");
       registeredUser.setFirstName("Fab");
       registeredUser.setBorrows(null);
       registeredUser.setReservations(null);

      //given
      HttpEntity<RegisteredUser> request = new HttpEntity<>(registeredUser);

      //when
      ResponseEntity<Void> responseEntity = testRestTemplate
              .postForEntity("/users", request, Void.class);

      //then
      assertEquals(CREATED, responseEntity.getStatusCode());
      assertNotNull(responseEntity.getHeaders().getLocation());
   }

    @Test
    public void givenRegisteredUserAndRegisteredUserId_whenPutRegisteredUser_thenReturns200() {

        //given
        int userId = 1;
        ResponseEntity<RegisteredUser> userResponseEntity = testRestTemplate.getForEntity("/users/"+userId, RegisteredUser.class);
        RegisteredUser userToSave = userResponseEntity.getBody();
        HttpEntity<RegisteredUser> request = new HttpEntity<>(userToSave);

        //when
        ResponseEntity<RegisteredUser> responseEntity = testRestTemplate.exchange("/users/"+userId, HttpMethod.PUT,request, RegisteredUser.class);

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
