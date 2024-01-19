package com.fabienit.flyingclub.dao;

import com.fabienit.flyingclub.model.beans.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    Reservation findById(int id);

    @Query("SELECT r FROM Reservation r WHERE r.borrowingDate <= :endDate AND r.returnDate >= :startDate")
    List<Reservation> findAllReservationBetweenTwoDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE r.borrowingDate <= :endDate AND r.returnDate >= :startDate AND r.canceled = false")
    List<Reservation> findAllReservationForAircraftReservation(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.registeredUser.id = :registeredUserId AND r.borrowingDate <= :endDate AND r.returnDate >= :startDate AND r.canceled = false")
    boolean existsReservationByIdAndDate(@Param("registeredUserId") int registeredUserId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE r.registeredUser.id = :registeredUserId AND r.borrowingDate <= :endDate AND r.returnDate >= :startDate AND r.canceled = false")
    boolean existsReservationByIdAndDateTest(@Param("registeredUserId") int registeredUserId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
