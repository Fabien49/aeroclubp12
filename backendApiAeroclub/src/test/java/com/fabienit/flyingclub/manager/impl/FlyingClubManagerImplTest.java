package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.FlyingClubDao;
import com.fabienit.flyingclub.model.beans.FlyingClub;
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
class FlyingClubManagerImplTest {
    private FlyingClubManagerImpl flyingClubManager;

    @Mock
    FlyingClubDao flyingClubDao;

    @BeforeEach
    void setUp() {
        flyingClubManager = new FlyingClubManagerImpl(flyingClubDao);
    }

    @Test
    void findAll() {
        List<FlyingClub> expectedFlyingClubs = Arrays.asList(new FlyingClub(), new FlyingClub());
        when(flyingClubDao.findAll()).thenReturn(expectedFlyingClubs);

        List<FlyingClub> actualFlyingClubs = flyingClubManager.findAll();

        assertEquals(expectedFlyingClubs, actualFlyingClubs);
        verify(flyingClubDao).findAll();
    }

    @Test
    void findById() {
        int id = 1;
        Optional<FlyingClub> expectedFlyingClub = Optional.of(new FlyingClub());
        when(flyingClubDao.findById(id)).thenReturn(expectedFlyingClub);

        Optional<FlyingClub> actualFlyingClub = flyingClubManager.findById(id);

        assertEquals(expectedFlyingClub, actualFlyingClub);
        verify(flyingClubDao).findById(id);
    }

    @Test
    void save() {
        FlyingClub flyingClub = new FlyingClub();
        when(flyingClubDao.save(flyingClub)).thenReturn(flyingClub);

        FlyingClub savedFlyingClub = flyingClubManager.save(flyingClub);

        assertEquals(flyingClub, savedFlyingClub);
        verify(flyingClubDao).save(flyingClub);
    }

    @Test
    void deleteById() {
        int id = 1;
        doNothing().when(flyingClubDao).deleteById(id);

        FlyingClub deletedFlyingClub = flyingClubManager.deleteById(id);

        assertNull(deletedFlyingClub);
        verify(flyingClubDao).deleteById(id);
    }

    @Test
    void existsFlyingClubById() {
        int id = 1;
        when(flyingClubDao.existsFlyingClubById(id)).thenReturn(true);

        boolean exists = flyingClubManager.existsFlyingClubById(id);

        assertTrue(exists);
        verify(flyingClubDao).existsFlyingClubById(id);
    }
}