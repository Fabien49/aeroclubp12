
package com.fabienit.flyingclub.manager;

import com.fabienit.flyingclub.model.beans.Revision;

import java.util.List;
import java.util.Optional;

public interface RevisionManager {

    List<Revision> findAll();
    Optional<Revision> findById(int id);
    Revision save(Revision revision);
    Revision deleteById(int id);
}

