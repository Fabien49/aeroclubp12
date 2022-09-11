package com.fabienit.biblioapi.api.controller;


import com.fabienit.biblioapi.BiblioApiApplication;
import com.fabienit.biblioapi.model.beans.Book;
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
public class BookControllerTest {

      @Autowired
      TestRestTemplate testRestTemplate;

   @BeforeEach
    public void before() {
        // because .withBasicAuth() creates a new TestRestTemplate with the same
        // configuration as the autowired one.
        testRestTemplate = testRestTemplate.withBasicAuth("fabien", "123456");
    }

    @Test
    public void whenGetBookList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = testRestTemplate.getForEntity("/books", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void givenBookId_whenGetNonExistingBook_thenReturns200() {

        //given
        int bookId = 1;

        //when
        ResponseEntity<Book> responseEntity = testRestTemplate.getForEntity("/books/"+bookId, Book.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getTitle());
        assertNotNull(responseEntity.getBody().getAuthorFirstName());
        assertNotNull(responseEntity.getBody().getAuthorLastName());
        assertNotNull(responseEntity.getBody().getPublicationDate());
        assertNotNull(responseEntity.getBody().getPictureURL());
        assertNotNull(responseEntity.getBody().getSynopsis());
    }

    @Test
    public void givenNonExistentBookId_whenGetNonExistingBook_thenReturns404() {

        //given
        int bookId = 999;

        //when
        ResponseEntity<Book> responseEntity = testRestTemplate.getForEntity("/books/"+bookId, Book.class);

        //then
        assertEquals(NOT_FOUND, responseEntity.getStatusCode());
    }

   @Test
   public void givenNewBook_whenPostBook_thenReturns201() {

      Book book = new Book(1, "title", "authorFirstName", "authorLastName", LocalDate.of(2022,3,20),"synopsis", "pictureURL", null, null);

      //given
      HttpEntity<Book> request = new HttpEntity<>(book);

      //when
      ResponseEntity<Void> responseEntity = testRestTemplate
              .postForEntity("/books", request, Void.class);

      //then
      assertEquals(CREATED, responseEntity.getStatusCode());
      assertNotNull(responseEntity.getHeaders().getLocation());
   }

    @Test
    public void givenBookAndBookId_whenPutBook_thenReturns200() {

        //given
        int bookId = 1;
        ResponseEntity<Book> bookResponseEntity = testRestTemplate.getForEntity("/books/"+bookId, Book.class);
        Book bookToSave = bookResponseEntity.getBody();
        HttpEntity<Book> request = new HttpEntity<>(bookToSave);

        //when
        ResponseEntity<Book> responseEntity = testRestTemplate.exchange("/books/"+bookId, HttpMethod.PUT,request, Book.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
    }

    @Test
    public void givenExistingBookId_whenPostBook_thenReturns400() {

        //given
        Book  book = new  Book(1,"1984","George","Orwell",LocalDate.of(1949,6,8), "Année 1984 en Océanie. 1984 ? C''est en tout cas ce qu''il semble à Winston, qui ne saurait toutefois en jurer. Le passé a été oblitéré et réinventé, et les événements les plus récents sont susceptibles d''être modifiés. Winston est lui-même chargé de récrire les archives qui contredisent le présent et les promesses de Big Brother. Grâce à une technologie de pointe, ce dernier sait tout, voit tout. Il n''est pas une âme dont il ne puisse connaître les pensées. On ne peut se fier à personne et les enfants sont encore les meilleurs espions qui soient. Liberté est Servitude. Ignorance est Puissance. Telles sont les devises du régime de Big Brother. La plupart des Océaniens n''y voient guère à redire, surtout les plus jeunes qui n''ont pas connu l''époque de leurs grands-parents et le sens initial du mot \"libre\". Winston refuse cependant de perdre espoir. Il entame une liaison secrète et hautement dangereuse avec l''insoumise Julia et tous deux vont tenter d''intégrer la Fraternité, une organisation ayant pour but de renverser Big Brother.","/covers/1984.jpg",null, null);
        HttpEntity<Book> request = new HttpEntity<>(book);

        //when
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/books", request, Void.class);

        //then
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

}
