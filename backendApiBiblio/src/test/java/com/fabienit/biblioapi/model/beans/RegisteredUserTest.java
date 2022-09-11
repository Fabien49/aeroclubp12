package com.fabienit.biblioapi.model.beans;

import com.fabienit.biblioapi.dao.RegisteredUserDao;
import com.fabienit.biblioapi.manager.impl.RegisteredUserManagerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RegisteredUserTest {

    private RegisteredUser registeredUserToSave;

    @Autowired
    private RegisteredUserManagerImpl classUnderTest;

    @Autowired
    RegisteredUserDao registeredUserDao;


    @Test
    public void testGetRegisteredUser() {

        // GIVEN
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setId(1);
        registeredUser.setRoles("USER");
        registeredUser.setEmail("fabienchapeau@gmail.com");
        registeredUser.setPassword("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a");
        registeredUser.setLastName("Chap");
        registeredUser.setFirstName("Fab");
        registeredUser.setBorrows(null);
        registeredUser.setReservations(null);

        // WHEN
        int id = registeredUser.getId();
        String firstName = registeredUser.getFirstName();
        String lastName = registeredUser.getLastName();
        String email = registeredUser.getEmail();
        String password = registeredUser.getPassword();
        String roles = registeredUser.getRoles();
        Set<Borrow> borrow = registeredUser.getBorrows();
        Set<Reservation> reservation = registeredUser.getReservations();
        String toString = registeredUser.toString();

        // THEN
        assertEquals(1, id);
        assertEquals("Chap", lastName);
        assertEquals("Fab", firstName);
        assertEquals("fabienchapeau@gmail.com", email);
        assertEquals("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a", password);
        assertEquals("USER", roles);
        assertEquals(null, borrow);
        assertEquals(null, reservation);
        assertNotNull(toString);
    }
    @Test
    public void testSetRegisteredUser() {

        // GIVEN
        RegisteredUser registeredUser = new RegisteredUser();


        // WHEN
        registeredUser.setId(1);
        registeredUser.setRoles("USER");
        registeredUser.setEmail("fabienchapeau@gmail.com");
        registeredUser.setPassword("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a");
        registeredUser.setLastName("Chap");
        registeredUser.setFirstName("Fab");
        registeredUser.setBorrows(null);
        registeredUser.setReservations(null);

        // THEN
        assertEquals(1, registeredUser.getId());
        assertEquals("Chap", registeredUser.getLastName());
        assertEquals("Fab", registeredUser.getFirstName());
        assertEquals("fabienchapeau@gmail.com", registeredUser.getEmail());
        assertEquals("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a", registeredUser.getPassword());
        assertEquals("USER", registeredUser.getRoles());
        assertEquals(null, registeredUser.getBorrows());
        assertEquals(null, registeredUser.getReservations());
        assertNotNull(registeredUser.toString());
    }

    @Test
    public void testRegisteredUserConstructor() {

        RegisteredUser registeredUser = new RegisteredUser(1, "Fab", "Chap", "fabienchapeau@gmail.com", "$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a", "USER", null, null);


        assertEquals(1, registeredUser.getId());
        assertEquals("Fab", registeredUser.getFirstName());
        assertEquals("Chap", registeredUser.getLastName());
        assertEquals("fabienchapeau@gmail.com", registeredUser.getEmail());
        assertEquals("$2a$10$jPUTDV6/9AkC7NRPPzmvXuLJlfnKoAYo.uJuZc0aeJHGum.LpxO0a", registeredUser.getPassword());
        assertEquals("USER", registeredUser.getRoles());
        assertEquals(null, registeredUser.getBorrows());
        assertEquals(null, registeredUser.getReservations());
    }
}
