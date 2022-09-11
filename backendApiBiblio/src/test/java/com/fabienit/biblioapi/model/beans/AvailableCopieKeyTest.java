package com.fabienit.biblioapi.model.beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AvailableCopieKeyTest {

    private AvailableCopieKey availableCopieKey;

    @Test
    public void testGetAvailableCopie() {

        // GIVEN
        AvailableCopieKey availableCopieKeyToSave = new AvailableCopieKey();
        availableCopieKeyToSave.setBookId(1);
        availableCopieKeyToSave.setLibraryId(1);
        availableCopieKeyToSave.toString();
        availableCopieKeyToSave.hashCode();
        availableCopieKeyToSave.equals(availableCopieKey);

        // WHEN

        int bookId = availableCopieKeyToSave.getBookId();
        int libraryId = availableCopieKeyToSave.getLibraryId();
        String toString = availableCopieKeyToSave.toString();

        // THEN
        assertEquals(1, bookId);
        assertEquals(1, libraryId);
        assertEquals(toString, availableCopieKeyToSave.toString());

    }

    @Test
    public void testSetAvailableCopie() {

        // GIVEN
        AvailableCopieKey availableCopieKeyToSave = new AvailableCopieKey();


        // WHEN
        availableCopieKeyToSave.setBookId(1);
        availableCopieKeyToSave.setLibraryId(1);
        availableCopieKeyToSave.toString();

        // THEN
        assertEquals(1, availableCopieKeyToSave.getBookId());
        assertEquals(1, availableCopieKeyToSave.getLibraryId());
    }

    @Test
    public void testAvailableCopieKeyConstructor() {

        AvailableCopieKey availableCopieKeyToSave = new AvailableCopieKey(1,1);


        assertEquals(1, availableCopieKeyToSave.getBookId());
        assertEquals(1,availableCopieKeyToSave.getLibraryId());
    }



    @Test
    public void testAvailableCopieKeyNotEqualsBookId() {
        AvailableCopieKey x = new AvailableCopieKey(1, 1);  // equals and hashCode check id field value
        AvailableCopieKey y = new AvailableCopieKey(2, 2);
        Assertions.assertFalse(x.equals(y) && y.equals(x));
        assertNotEquals(x.hashCode(), y.hashCode());
    }

    @Test
    public void testAvailableCopieKeyNotEqualsLibraryId() {
        AvailableCopieKey x = new AvailableCopieKey(1, 1);  // equals and hashCode check id field value
        AvailableCopieKey y = new AvailableCopieKey(1, 2);
        Assertions.assertFalse(x.equals(y) && y.equals(x));
    }
    @Test
    public void testAvailableCopieKeyEquals_Symmetric() {
        AvailableCopieKey x = new AvailableCopieKey(1, 1);  // equals and hashCode check id field value
        AvailableCopieKey y = new AvailableCopieKey(1, 1);
        Assertions.assertTrue(x.equals(y) && y.equals(x));
    }

    @Test
    public void testAvailableCopieKeyOtherObject() {
        AvailableCopieKey x = new AvailableCopieKey(1, 1);  // equals and hashCode check id field value
        AvailableCopieKey y = new AvailableCopieKey(1, 1);

        Assertions.assertFalse(x.equals(new String("toto")));
    }

    @Test
    public void testAvailableCopieKeyObjectSameReference() {
        AvailableCopieKey x = new AvailableCopieKey(1, 1);  // equals and hashCode check id field value
        AvailableCopieKey y = x;

        Assertions.assertTrue(x.equals(y));
    }

}
