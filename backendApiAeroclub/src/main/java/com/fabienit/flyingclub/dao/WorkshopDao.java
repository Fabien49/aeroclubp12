package com.fabienit.flyingclub.dao;


import com.fabienit.flyingclub.model.beans.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * WorkshopDao
 */
@Repository
public interface WorkshopDao extends JpaRepository<Workshop, Integer>{

    boolean existsWorkshopById(Integer id);

}