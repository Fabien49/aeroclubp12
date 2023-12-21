package com.fabienit.flyingclub.dao;

import com.fabienit.flyingclub.model.beans.FlyingClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FlyingClubDao
 */
@Repository
public interface FlyingClubDao extends JpaRepository<FlyingClub, Integer> {

    boolean existsFlyingClubById(int id);

}