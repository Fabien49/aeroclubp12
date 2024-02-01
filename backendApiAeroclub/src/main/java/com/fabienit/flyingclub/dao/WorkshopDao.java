package com.fabienit.flyingclub.dao;


import com.fabienit.flyingclub.model.beans.Workshop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * WorkshopDao
 */
@Repository
public interface WorkshopDao extends JpaRepository<Workshop, Integer>{

    List<Workshop> findAllByAircraftMarkContaining(String mark);

    List<Workshop> findAllByAircraftId(Integer aircraftId);
}