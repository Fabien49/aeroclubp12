package com.fabienit.biblioapi.api.controller;


import com.fabienit.biblioapi.BiblioApiApplication;
import com.fabienit.biblioapi.model.beans.Book;
import com.fabienit.biblioapi.model.beans.Borrow;
import com.fabienit.biblioapi.model.beans.Library;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BiblioApiApplication.class)
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BorrowControllerTest {

      @Autowired
      TestRestTemplate testRestTemplate;

   @BeforeEach
    public void before() {
        // because .withBasicAuth() creates a new TestRestTemplate with the same
        // configuration as the autowired one.
        testRestTemplate = testRestTemplate.withBasicAuth("fabien", "123456");
    }

    @Test
    public void whenGetBorrowList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/borrows", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void whenGetBorrowByUserId_thenReturns200() {

       //Given
        int userID = 1;

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/users/" + userID + "/borrows", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void givenBorrowId_whenGetNonExistingBorrow_thenReturns200() {

        //given
        int borrowId = 1;

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getBorrowDate());
        assertNotNull(responseEntity.getBody().getReturnDate());
        assertNotNull(responseEntity.getBody().getExtendedDuration());
        assertNotNull(responseEntity.getBody().getBookReturned());
        assertNotNull(responseEntity.getBody().getRegistereduser());
        assertNotNull(responseEntity.getBody().getBook());
        assertNotNull(responseEntity.getBody().getLibrary());
    }

    @Test
    public void givenNonExistentBorrowId_whenGetNonExistingBorrow_thenReturns404() {

        //given
        int borrowId = 999;

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void givenNewBorrow_whenPostBorrow_thenReturns201() {

        Borrow borrowToSave = new Borrow(1, new Book(1, "title", "authorFirstName", "authorLastName", LocalDate.of(2022,3,20),"synopsis", "pictureURL", null, null), new RegisteredUser(1, "Fab", "Chap", "fabienchapeau@gmail.com", "$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a","USER", null, null), new Library(1, "Justice",null, null), LocalDate.of(2022,3,20),  LocalDate.of(2022,5,20), false, false);

        //given
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);

        //when
        ResponseEntity<Void> responseEntity = testRestTemplate
                .postForEntity("/borrows", request, Void.class);

        //then
        assertEquals(CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().getLocation());
    }

    @Test
    public void givenBorrowAndBorrowId_whenPutBorrow_thenReturns200() {

        //given
        int borrowId = 1;
        ResponseEntity<Borrow> borrowResponseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);
        Borrow borrowToSave = borrowResponseEntity.getBody();
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.exchange("/borrows/"+borrowId, HttpMethod.PUT,request, Borrow.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenNotExistingBorrowAndBorrowId_whenPutBorrow_thenReturns404() {

        //given
        int borrowId = 999;
        ResponseEntity<Borrow> borrowResponseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);
        Borrow borrowToSave = borrowResponseEntity.getBody();
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);
        assertEquals(NOT_FOUND, borrowResponseEntity.getStatusCode());


/*        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.exchange("/borrows/"+borrowId, HttpMethod.PUT,request, Borrow.class);

        //then
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());
        System.out.println(responseEntity.toString());
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());*/

    }

    @Test
    public void givenBorrowAndBorrowId_whenPutExtendBorrow_thenReturns200() {

        //given
        int borrowId = 1;
        ResponseEntity<Borrow> borrowResponseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);
        Borrow borrowToSave = borrowResponseEntity.getBody();
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.exchange("/borrows/extend/"+borrowId, HttpMethod.PUT,request, Borrow.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenBorrowAndBorrowId_whenPutExtendBorrow_thenReturns400() {

        //given
        int borrowId = 2;
        ResponseEntity<Borrow> borrowResponseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);
        Borrow borrowToSave = borrowResponseEntity.getBody();
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.exchange("/borrows/extend/"+borrowId, HttpMethod.PUT,request, Borrow.class);

        //then
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void givenBorrowAndBorrowId_whenPutReturnBorrow_thenReturns200() {

        //given
        int borrowId = 1;
        ResponseEntity<Borrow> borrowResponseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);
        Borrow borrowToSave = borrowResponseEntity.getBody();
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.exchange("/borrows/return/"+borrowId, HttpMethod.PUT,request, Borrow.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void whenDeleteBorrow_thenReturns200() {

        //given
        int borrowId = 1;
        ResponseEntity<Borrow> borrowResponseEntity = testRestTemplate.getForEntity("/borrows/"+borrowId, Borrow.class);
        Borrow borrowToSave = borrowResponseEntity.getBody();
        HttpEntity<Borrow> request = new HttpEntity<>(borrowToSave);

        //when
        ResponseEntity<Borrow> responseEntity = testRestTemplate.exchange("/borrows/"+borrowId, HttpMethod.DELETE,request, Borrow.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

/*    @Test
    public void givenExistingBookId_whenPostBook_thenReturns400() {

        //given
        Book  book = new  Book(1,"1984","George","Orwell",LocalDate.of(1949,6,8), "Année 1984 en Océanie. 1984 ? C''est en tout cas ce qu''il semble à Winston, qui ne saurait toutefois en jurer. Le passé a été oblitéré et réinventé, et les événements les plus récents sont susceptibles d''être modifiés. Winston est lui-même chargé de récrire les archives qui contredisent le présent et les promesses de Big Brother. Grâce à une technologie de pointe, ce dernier sait tout, voit tout. Il n''est pas une âme dont il ne puisse connaître les pensées. On ne peut se fier à personne et les enfants sont encore les meilleurs espions qui soient. Liberté est Servitude. Ignorance est Puissance. Telles sont les devises du régime de Big Brother. La plupart des Océaniens n''y voient guère à redire, surtout les plus jeunes qui n''ont pas connu l''époque de leurs grands-parents et le sens initial du mot \"libre\". Winston refuse cependant de perdre espoir. Il entame une liaison secrète et hautement dangereuse avec l''insoumise Julia et tous deux vont tenter d''intégrer la Fraternité, une organisation ayant pour but de renverser Big Brother.","/covers/1984.jpg",null, null);
        HttpEntity<Book> request = new HttpEntity<>(book);

        //when
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/books", request, Void.class);

        //then
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }*/

}
