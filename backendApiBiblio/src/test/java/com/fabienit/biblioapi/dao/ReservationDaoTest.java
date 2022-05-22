package com.fabienit.biblioapi.dao;

import com.fabienit.biblioapi.model.beans.RegisteredUser;
import com.fabienit.biblioapi.model.beans.Reservation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationDaoTest {

    @Autowired
    ReservationDao reservationDao;



    @Test
    void createReservation(){
        Reservation reservation = new Reservation();
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setFirstName("Fabien");
        registeredUser.setLastName("Chapeau");
        registeredUser.setEmail("fabien@gmail.com");
        registeredUser.setPassword("123456");
        System.out.println(reservation);
        Reservation reservation1 = new Reservation();
/*        Reservation reservationResponse1 = reservationDao.save(reservation1);
        System.out.println(reservationResponse1.getId());*/
    }

/*    @Test
    void afficherReservation(){
        Reservation reservation = new Reservation();
        Reservation reservationResponse = reservationDao.save(reservation);
        Reservation reservation1 = new Reservation();
        Reservation reservationResponse1 = reservationDao.save(reservation1);
        List <Reservation> reservationResponse2 =this.reservationDao.findAll();
        System.out.println("La liste comporte " + reservationResponse2.size() + " éléments");
    }*/

}
