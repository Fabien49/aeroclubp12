package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.dao.AircraftDao;
import com.fabienit.flyingclub.dao.ReservationDao;
import com.fabienit.flyingclub.manager.EmailService;
import com.fabienit.flyingclub.model.beans.Aircraft;
import com.fabienit.flyingclub.model.beans.RegisteredUser;
import com.fabienit.flyingclub.model.beans.Reservation;
import com.fabienit.flyingclub.web.exceptions.FunctionnalException;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReservationManagerImplTest {
    private ReservationManagerImpl reservationManager;
    @Mock
    ReservationDao reservationDao;
    @Mock
    AircraftDao aircraftDao;
    @Mock
    EmailService emailService;
    @Mock
    JavaMailSender mailSender;
    @Mock
    TemplateEngine templateEngine;

    @BeforeEach
    void setUp() {
        reservationManager = new ReservationManagerImpl(reservationDao, aircraftDao, emailService);
    }

    @Test
    void deleteById() {
        int id = 1;
        Reservation expectedReservation = new Reservation();
        when(reservationDao.findById(id)).thenReturn(expectedReservation);
        doNothing().when(reservationDao).deleteById(id);

        Reservation actualReservation = reservationManager.deleteById(id);

        assertEquals(expectedReservation, actualReservation);
        verify(reservationDao).findById(id);
        verify(reservationDao).deleteById(id);
    }

/*    @Test
    void saveCanceledReservation() throws FunctionnalException, MessagingException {
        Reservation reservation = new Reservation();
        RegisteredUser registeredUser = new RegisteredUser();
        Aircraft aircraft = new Aircraft();
        aircraft.setMark("cessna");
        registeredUser.setFirstName("test1");
        registeredUser.setEmail("test1@gmail.com");
        registeredUser.setHours(100);
        reservation.setRegisteredUser(registeredUser);
        reservation.setAircraft(aircraft);
        reservation.setBorrowingDate(LocalDate.of(2024,1,1));
        reservation.setReturnDate(LocalDate.of(2024,1,10));
        reservation.setCanceled(true);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("name", reservation.getRegisteredUser().getFirstName());
        templateModel.put("aircraftName", reservation.getAircraft().getMark());
        templateModel.put("borrowingDate", reservation.getBorrowingDate().toString());
        templateModel.put("returnDate", reservation.getReturnDate().toString());
        templateModel.put("hours", reservation.getRegisteredUser().getHours());

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = templateEngine.process("annulationReservation", thymeleafContext);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setText(htmlBody, true); // Set to true to enable HTML content
        helper.setTo("test1@gmail.com");
        helper.setSubject("Annulation de Réservation");
        helper.setFrom("aeroclub@localhost");
        when(templateEngine.process(eq("annulationReservation"), any(Context.class)))
                .thenReturn("Mocked HTML Body");

        EmailService emailService1 = new EmailService(mailSender, templateEngine);

        emailService1.sendHtmlMessage("test1@gmail.com", "Annulation de Réservation", "annulationReservation", templateModel);

        // Configurez le comportement attendu pour les appels de méthodes simulées
        doNothing().when(emailService).sendHtmlMessage(eq("test1@gmail.com"), eq("Annulation de Réservation"), eq("annulationReservation"),anyMap());

        // Appelez la méthode à tester
        Reservation savedReservation = reservationManager.save(reservation);

        // Vérifiez les résultats
        assertEquals(reservation, savedReservation);
        verify(emailService, times(1)).sendHtmlMessage(anyString(), eq("Annulation de Réservation"), eq("annulationReservation"), anyMap());
        verify(reservationDao, times(1)).save(reservation);
    }*/
    @Test
    void save() throws FunctionnalException {
        Reservation reservation = new Reservation();
        RegisteredUser registeredUser = new RegisteredUser();
        Aircraft aircraft = new Aircraft();
        aircraft.setMark("cessna");
        registeredUser.setFirstName("toto");
        reservation.setRegisteredUser(registeredUser);
        reservation.setAircraft(aircraft);
        reservation.setBorrowingDate(LocalDate.of(2024,1,1));
        reservation.setReturnDate(LocalDate.of(2024,1,10));
        when(reservationDao.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationManager.save(reservation);

        assertEquals(reservation, savedReservation);
        verify(reservationDao).save(reservation);
    }


    @Test
    void checkIfAircraftIsAvailable() throws FunctionnalException {
        List<Aircraft> expectedAircrafts = Collections.singletonList(new Aircraft());
        when(aircraftDao.findAll()).thenReturn(expectedAircrafts);

        List<Aircraft> actualAircrafts = reservationManager.checkIfAircraftIsAvailable();

        assertEquals(expectedAircrafts, actualAircrafts);
        verify(aircraftDao).findAll();
    }
    @Test
    void updateAircraftReservation() throws FunctionnalException {
        Reservation reservation = new Reservation();
        RegisteredUser registeredUser = new RegisteredUser();
        Aircraft aircraft = new Aircraft();
        aircraft.setMark("milou");
        registeredUser.setFirstName("tintin");
        reservation.setRegisteredUser(registeredUser);
        reservation.setAircraft(aircraft);
        reservation.setBorrowingDate(LocalDate.of(2024,1,1));
        reservation.setReturnDate(LocalDate.of(2024,1,10));
        when(reservationDao.save(reservation)).thenReturn(reservation);

        Reservation updatedReservation = reservationManager.updateAircraftReservation(reservation);

        assertEquals(reservation, updatedReservation);
        verify(reservationDao).save(reservation);
    }
    @Test
    void findAll() {
        Reservation reservation = new Reservation();
        List<Reservation> expectedReservations = Collections.singletonList(reservation);
        when(reservationDao.findAll()).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationManager.findAll();

        assertEquals(expectedReservations, actualReservations);
        verify(reservationDao).findAll();
    }
    @Test
    void findById() {
        int id = 1;
        Reservation expectedReservation = new Reservation();
        when(reservationDao.findById(id)).thenReturn(expectedReservation);

        Reservation actualReservation = reservationManager.findById(id);

        assertEquals(expectedReservation, actualReservation);
        verify(reservationDao).findById(id);
    }
    @Test
    void findAllByRegisteredUser() {
        int registeredUserId = 1;
        Reservation reservation = new Reservation();
        List<Reservation> expectedReservations = Collections.singletonList(reservation);
        when(reservationDao.findAllByRegisteredUser(registeredUserId)).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationManager.findAllByRegisteredUser(registeredUserId);

        assertEquals(expectedReservations, actualReservations);
        verify(reservationDao).findAllByRegisteredUser(registeredUserId);
    }
    @Test
    void existsReservationByIdAndDate() {
        int id = 1;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        when(reservationDao.existsReservationByIdAndDate(id, startDate, endDate)).thenReturn(true);

        boolean exists = reservationManager.existsReservationByIdAndDate(id, startDate, endDate);

        assertTrue(exists);
        verify(reservationDao).existsReservationByIdAndDate(id, startDate, endDate);
    }

}