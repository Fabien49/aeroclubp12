package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.WorkshopDao;
import com.fabienit.flyingclub.model.beans.Workshop;
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
class WorkshopManagerImplTest {
    private WorkshopManagerImpl workshopManager;

    @Mock
    WorkshopDao workshopDao;

    @BeforeEach
    void setUp() {
        workshopManager = new WorkshopManagerImpl(workshopDao);
    }

    @Test
    void findAll() {
        List<Workshop> expectedWorkshops = Arrays.asList(new Workshop(), new Workshop());
        when(workshopDao.findAll()).thenReturn(expectedWorkshops);

        List<Workshop> actualWorkshops = workshopManager.findAll();

        assertEquals(expectedWorkshops, actualWorkshops);
        verify(workshopDao).findAll();
    }
    @Test
    void findById() {
        int id = 1;
        Optional<Workshop> expectedWorkshop = Optional.of(new Workshop());
        when(workshopDao.findById(id)).thenReturn(expectedWorkshop);

        Optional<Workshop> actualWorkshop = workshopManager.findById(id);

        assertEquals(expectedWorkshop, actualWorkshop);
        verify(workshopDao).findById(id);
    }
    @Test
    void save() {
        Workshop workshop = new Workshop();
        when(workshopDao.save(workshop)).thenReturn(workshop);

        Workshop savedWorkshop = workshopManager.save(workshop);

        assertEquals(workshop, savedWorkshop);
        verify(workshopDao).save(workshop);
    }
    @Test
    void deleteById() {
        int id = 1;
        doNothing().when(workshopDao).deleteById(id);

        Workshop deletedWorkshop = workshopManager.deleteById(id);

        assertNull(deletedWorkshop);
        verify(workshopDao).deleteById(id);
    }
}