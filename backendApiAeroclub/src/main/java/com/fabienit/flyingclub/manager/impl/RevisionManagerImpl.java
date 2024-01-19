
package com.fabienit.flyingclub.manager.impl;

import com.fabienit.flyingclub.manager.RevisionManager;
import com.fabienit.flyingclub.model.beans.Revision;
import com.fabienit.flyingclub.dao.RevisionDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RevisionManagerImpl implements RevisionManager {

    private final RevisionDao revisionDao;

    public RevisionManagerImpl(RevisionDao revisionDao) {
        this.revisionDao = revisionDao;
    }


    @Override
    public List<Revision> findAll() {
        return revisionDao.findAll();
    }

    @Override
    public Optional<Revision> findById(int id) {
        return revisionDao.findById(id);
    }

    @Override
    public Revision save(Revision revision) {
        return revisionDao.save(revision);
    }

    @Override
    public Revision deleteById(int id) {
        revisionDao.deleteById(id);
        return null;
    }
}