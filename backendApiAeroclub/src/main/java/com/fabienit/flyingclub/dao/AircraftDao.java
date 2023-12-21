package com.fabienit.flyingclub.dao;


import com.fabienit.flyingclub.model.beans.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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


}