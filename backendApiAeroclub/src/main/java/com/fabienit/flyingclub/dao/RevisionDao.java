package com.fabienit.flyingclub.dao;


import com.fabienit.flyingclub.model.beans.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RevisionDao
 */
@Repository
public interface RevisionDao extends JpaRepository<Revision, Integer>{

    boolean existsRevisionById(Integer id);

}