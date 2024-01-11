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


    // Find aircraft by mark ignoring case
    List<Aircraft> findByMarkIgnoreCaseContaining(String name);
     boolean existsAircraftById(int id);

     List<Aircraft> findAllByIsAvailableTrue();


    @Query("SELECT a FROM Aircraft a WHERE a.isAvailable = true AND a.id NOT IN (" +
            "SELECT r.aircraft.id FROM Reservation r WHERE r.canceled = false AND (" +
            "r.borrowingDate < :endDate AND r.returnDate > :startDate))")
    List<Aircraft> findAvailableAircrafts(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}