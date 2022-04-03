package com.fabienit.biblioapi.dao;


import com.fabienit.biblioapi.beans.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BorrowDao
 */
@Repository
public interface BorrowDao extends JpaRepository<Borrow, Integer>{

    List<Borrow> findByRegistereduserId(int userId);
}