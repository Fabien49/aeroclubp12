package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.RegisteredUserDao;
import com.fabienit.flyingclub.model.beans.RegisteredUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RegisteredUserManagerImplTest {
    private RegisteredUserManagerImpl registeredUserManager;

    @Mock
    RegisteredUserDao registeredUserDao;

    @BeforeEach
    void setUp() {
        registeredUserManager = new RegisteredUserManagerImpl(registeredUserDao);
    }

    @Test
    void findAll() {
        List<RegisteredUser> expectedUsers = Arrays.asList(new RegisteredUser(), new RegisteredUser());
        when(registeredUserDao.findAll()).thenReturn(expectedUsers);

        List<RegisteredUser> actualUsers = registeredUserManager.findAll();

        assertEquals(expectedUsers, actualUsers);
        verify(registeredUserDao).findAll();
    }
    @Test
    void findById() {
        int id = 1;
        Optional<RegisteredUser> expectedUser = Optional.of(new RegisteredUser());
        when(registeredUserDao.findById(id)).thenReturn(expectedUser);

        Optional<RegisteredUser> actualUser = registeredUserManager.findById(id);

        assertEquals(expectedUser, actualUser);
        verify(registeredUserDao).findById(id);
    }
    @Test
    void save() {
        RegisteredUser user = new RegisteredUser();
        when(registeredUserDao.save(user)).thenReturn(user);

        RegisteredUser savedUser = registeredUserManager.save(user);

        assertEquals(user, savedUser);
        verify(registeredUserDao).save(user);
    }
    @Test
    void deleteById() {
        int id = 1;
        doNothing().when(registeredUserDao).deleteById(id);

        RegisteredUser deletedUser = registeredUserManager.deleteById(id);

        assertNull(deletedUser);
        verify(registeredUserDao).deleteById(id);
    }

    @Test
    void findByEmail() {
        String email = "test@example.com";
        RegisteredUser expectedUser = new RegisteredUser();
        when(registeredUserDao.findByEmail(email)).thenReturn(expectedUser);

        RegisteredUser actualUser = registeredUserManager.findByEmail(email);

        assertEquals(expectedUser, actualUser);
        verify(registeredUserDao).findByEmail(email);
    }
}