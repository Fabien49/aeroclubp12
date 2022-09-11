package com.fabienit.biblioapi.model.beans;

import com.fabienit.biblioapi.manager.impl.LibraryManagerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LibraryTest {

    private Library library;

    @MockBean
    LibraryManagerImpl classUnderTest;

    @MockBean
    List<Book> bookList;

    @Test
    public void testGetLibrary() {

        // GIVEN
        Library libraryToSave = new Library();
        libraryToSave.setId(1);
        libraryToSave.setName("biblio");
        libraryToSave.setBorrows(null);
        libraryToSave.setAvailableCopies(null);
        libraryToSave.toString();

        // WHEN
        int id = libraryToSave.getId();
        String name = libraryToSave.getName();
        String toString = libraryToSave.toString();
        Set<Borrow> borrow = libraryToSave.getBorrows();
        Set<AvailableCopie> availableCopie = libraryToSave.getAvailableCopies();

        // THEN
        assertEquals(1, id);
        assertEquals("biblio", name);
        assertEquals(null, borrow);
        assertEquals(null, availableCopie);
        assertEquals(libraryToSave.toString(), toString);

    }

    @Test
    public void testSetLibrary() {

        // GIVEN
        Library libraryToSave = new Library();


        // WHEN
        libraryToSave.setId(1);
        libraryToSave.setName("biblio");
        libraryToSave.setBorrows(null);
        libraryToSave.setAvailableCopies(null);
        String toString =  libraryToSave.toString();

        // THEN
        assertEquals(1, libraryToSave.getId());
        assertEquals("biblio", libraryToSave.getName());
        assertNull(libraryToSave.getBorrows());
        assertNull(libraryToSave.getAvailableCopies());
        assertNotNull(toString);

    }

}
