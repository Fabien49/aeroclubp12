package com.fabienit.flyingclub.dao;

import com.fabienit.flyingclub.model.beans.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {

    @Query(value ="SELECT * FROM reservations WHERE aircraft_id = ?", nativeQuery = true)
    List<Reservation> findAllByAircraftId(Integer aircraft_id);

    @Query(value = "SELECT * FROM reservations WHERE aircraft_id = ? AND registered_user_id = ?", nativeQuery = true )
    Optional<Reservation> findByAircraftAndRegisteredUser(Integer aircraft_id, Integer registered_user_id);

    @Query(value = "SELECT * FROM reservations WHERE registered_user_id = ?", nativeQuery = true)
    List<Reservation> findAllByRegisteredUser(Integer registered_user_id);
}
