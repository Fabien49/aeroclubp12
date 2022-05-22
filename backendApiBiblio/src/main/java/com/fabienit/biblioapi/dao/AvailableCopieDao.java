package com.fabienit.biblioapi.dao;


import com.fabienit.biblioapi.model.beans.AvailableCopie;
import com.fabienit.biblioapi.model.beans.AvailableCopieKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AvailableCopieDao
 */
@Repository
public interface AvailableCopieDao extends JpaRepository<AvailableCopie, AvailableCopieKey>{

    
}