package com.fabienit.biblioapi.model.beans;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookTest {

    private Book book;

    @Test
    public void testGetBook() {

        // GIVEN
        Book bookToSave = new Book();
        bookToSave.setAuthorFirstName("Fabien");
        bookToSave.setAuthorLastName("Chapeau");
        bookToSave.setPictureURL("toto");
        bookToSave.setPublicationDate(LocalDate.of(2022,3,20));
        bookToSave.setSynopsis("toto");
        bookToSave.setTitle("toto2041");
        bookToSave.setBorrows(null);
        bookToSave.setAvailableCopies(null);

        // WHEN
        String title = bookToSave.getTitle();
        String firstName = bookToSave.getAuthorFirstName();
        String lastName = bookToSave.getAuthorLastName();
        String pictureURL = bookToSave.getPictureURL();
        LocalDate publicationDate = bookToSave.getPublicationDate();
        String synopsis = bookToSave.getSynopsis();
        Set<Borrow> borrow = bookToSave.getBorrows();
        Set<AvailableCopie> availableCopie = bookToSave.getAvailableCopies();

        // THEN
        assertEquals("toto2041", title);
        assertEquals("Fabien", firstName);
        assertEquals("Chapeau", lastName);
        assertEquals("toto", pictureURL);
        assertEquals(LocalDate.of(2022,3,20), publicationDate);
        assertEquals("toto", synopsis);
        assertEquals(null, bookToSave.getBorrows());
        assertEquals(null, bookToSave.getAvailableCopies());

    }

    @Test
    public void testSetBook() {

        // GIVEN
        Book bookToSave = new Book();


        // WHEN
        bookToSave.setAuthorFirstName("Fabien");
        bookToSave.setAuthorLastName("Chapeau");
        bookToSave.setPictureURL("toto");
        bookToSave.setPublicationDate(LocalDate.of(2022,3,20));
        bookToSave.setSynopsis("toto");
        bookToSave.setTitle("toto2041");
        bookToSave.setBorrows(null);
        bookToSave.setAvailableCopies(null);

        // THEN
        assertEquals("toto2041", bookToSave.getTitle());
        assertEquals("Fabien", bookToSave.getAuthorFirstName());
        assertEquals("Chapeau", bookToSave.getAuthorLastName());
        assertEquals("toto", bookToSave.getPictureURL());
        assertEquals(LocalDate.of(2022,3,20), bookToSave.getPublicationDate());
        assertEquals("toto", bookToSave.getSynopsis());
        assertEquals(null, bookToSave.getBorrows());
        assertEquals(null, bookToSave.getAvailableCopies());
        assertNotNull(bookToSave.toString());

    }

    @Test
    public void testBookConstructor() {

        Book bookToSave = new Book(5, "title", "authorFirstName", "authorLastName", LocalDate.of(2022,3,20),"synopsis", "pictureURL", null, null);


        assertEquals(5, bookToSave.getId());
        assertEquals("title", bookToSave.getTitle());
        assertEquals("authorFirstName", bookToSave.getAuthorFirstName());
        assertEquals("authorLastName", bookToSave.getAuthorLastName());
        assertEquals(LocalDate.of(2022,3,20), bookToSave.getPublicationDate());
        assertEquals("pictureURL", bookToSave.getPictureURL());
        assertEquals("synopsis", bookToSave.getSynopsis());
        assertEquals(null, bookToSave.getBorrows());
        assertEquals(null, bookToSave.getAvailableCopies());
    }

}
