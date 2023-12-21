
package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.Aircraft;

import java.util.List;
import java.util.Optional;

public interface AircraftManager {

    List<Aircraft> findAll();
    Optional<Aircraft> findById(int id);
    Aircraft save(Aircraft aircraft);
    List<Aircraft> checkIfAircraftIsAvailable();
    Aircraft deleteById(int id);
}

