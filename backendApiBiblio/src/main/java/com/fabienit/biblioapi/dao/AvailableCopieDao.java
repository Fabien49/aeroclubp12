package com.fabienit.biblioapi.dao;


import com.fabienit.biblioapi.beans.AvailableCopie;
import com.fabienit.biblioapi.beans.AvailableCopieKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AvailableCopieDao
 */
@Repository
public interface AvailableCopieDao extends JpaRepository<AvailableCopie, AvailableCopieKey>{

    
}