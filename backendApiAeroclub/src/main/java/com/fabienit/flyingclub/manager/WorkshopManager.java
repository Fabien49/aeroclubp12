
package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.Workshop;

import java.util.List;
import java.util.Optional;

public interface WorkshopManager {

    List<Workshop> findAll();
    Optional<Workshop> findById(int id);
    Workshop save(Workshop workshop);
    Workshop deleteById(int id);
}

