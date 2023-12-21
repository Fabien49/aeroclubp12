/*
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
public class AircraftTest {

    private Aircraft aircraft;

    @Test
    public void testGetBook() {

        // GIVEN
        Aircraft aircraftToSave = new Aircraft();
        aircraftToSave.setAuthorFirstName("Fabien");
        aircraftToSave.setAuthorLastName("Chapeau");
        aircraftToSave.setPictureURL("toto");
        aircraftToSave.setPublicationDate(LocalDate.of(2022,3,20));
        aircraftToSave.setSynopsis("toto");
        aircraftToSave.setTitle("toto2041");
        aircraftToSave.setBorrows(null);
        aircraftToSave.setAvailableCopies(null);

        // WHEN
        String title = aircraftToSave.getTitle();
        String firstName = aircraftToSave.getAuthorFirstName();
        String lastName = aircraftToSave.getAuthorLastName();
        String pictureURL = aircraftToSave.getPictureURL();
        LocalDate publicationDate = aircraftToSave.getPublicationDate();
        String synopsis = aircraftToSave.getSynopsis();
        Set<Reservation> borrow = aircraftToSave.getBorrows();
        Set<AvailableCopie> availableCopie = aircraftToSave.getAvailableCopies();

        // THEN
        assertEquals("toto2041", title);
        assertEquals("Fabien", firstName);
        assertEquals("Chapeau", lastName);
        assertEquals("toto", pictureURL);
        assertEquals(LocalDate.of(2022,3,20), publicationDate);
        assertEquals("toto", synopsis);
        assertEquals(null, aircraftToSave.getBorrows());
        assertEquals(null, aircraftToSave.getAvailableCopies());

    }

    @Test
    public void testSetBook() {

        // GIVEN
        Aircraft aircraftToSave = new Aircraft();


        // WHEN
        aircraftToSave.setAuthorFirstName("Fabien");
        aircraftToSave.setAuthorLastName("Chapeau");
        aircraftToSave.setPictureURL("toto");
        aircraftToSave.setPublicationDate(LocalDate.of(2022,3,20));
        aircraftToSave.setSynopsis("toto");
        aircraftToSave.setTitle("toto2041");
        aircraftToSave.setBorrows(null);
        aircraftToSave.setAvailableCopies(null);

        // THEN
        assertEquals("toto2041", aircraftToSave.getTitle());
        assertEquals("Fabien", aircraftToSave.getAuthorFirstName());
        assertEquals("Chapeau", aircraftToSave.getAuthorLastName());
        assertEquals("toto", aircraftToSave.getPictureURL());
        assertEquals(LocalDate.of(2022,3,20), aircraftToSave.getPublicationDate());
        assertEquals("toto", aircraftToSave.getSynopsis());
        assertEquals(null, aircraftToSave.getBorrows());
        assertEquals(null, aircraftToSave.getAvailableCopies());
        assertNotNull(aircraftToSave.toString());

    }

    @Test
    public void testBookConstructor() {

        Aircraft aircraftToSave = new Aircraft(5, "title", "authorFirstName", "authorLastName", LocalDate.of(2022,3,20),"synopsis", "pictureURL", null, null);


        assertEquals(5, aircraftToSave.getId());
        assertEquals("title", aircraftToSave.getTitle());
        assertEquals("authorFirstName", aircraftToSave.getAuthorFirstName());
        assertEquals("authorLastName", aircraftToSave.getAuthorLastName());
        assertEquals(LocalDate.of(2022,3,20), aircraftToSave.getPublicationDate());
        assertEquals("pictureURL", aircraftToSave.getPictureURL());
        assertEquals("synopsis", aircraftToSave.getSynopsis());
        assertEquals(null, aircraftToSave.getBorrows());
        assertEquals(null, aircraftToSave.getAvailableCopies());
    }

}
*/
