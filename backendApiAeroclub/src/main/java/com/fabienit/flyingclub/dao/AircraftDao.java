package com.fabienit.flyingclub.dao;


import com.fabienit.flyingclub.model.beans.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * AircraftDao
 */
@Repository
public interface AircraftDao extends JpaRepository<Aircraft, Integer>{

     boolean existsAircraftById(int id);
     List<Aircraft> findAllByIsAvailableTrue();
    @Query("SELECT a FROM Aircraft a INNER JOIN a.reservations r WHERE r.id = :reservationId")
    Aircraft findAircraftByReservationId(@Param("reservationId") int reservationId);

}