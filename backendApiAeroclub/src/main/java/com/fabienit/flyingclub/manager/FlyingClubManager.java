package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.FlyingClub;

import java.util.List;
import java.util.Optional;

public interface FlyingClubManager {

    List<FlyingClub> findAll();
    Optional<FlyingClub> findById(int id);
    FlyingClub save(FlyingClub library);
    FlyingClub deleteById(int id);
    boolean existsFlyingClubById(int id);
}

