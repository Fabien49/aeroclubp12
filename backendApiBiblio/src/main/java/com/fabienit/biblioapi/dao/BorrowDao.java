package com.fabienit.biblioapi.dao;


import com.fabienit.biblioapi.model.beans.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BorrowDao
 */
@Repository
public interface BorrowDao extends JpaRepository<Borrow, Integer>{

    List<Borrow> findByRegistereduserId(int userId);

    @Query(value = "SELECT * from borrow WHERE book_id = ? AND library_id = ?;", nativeQuery = true)
    List<Borrow> findAllByBookIdAndLibraryId(int book_id, int library_id);


}