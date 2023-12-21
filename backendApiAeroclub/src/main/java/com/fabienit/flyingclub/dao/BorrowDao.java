/*
package com.fabienit.biblioapi.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

*/
/**
 * BorrowDao
 *//*

@Repository
public interface BorrowDao extends JpaRepository<Reservation, Integer>{

    List<Reservation> findByRegistereduserId(int userId);

    @Query(value = "SELECT * from borrow WHERE book_id = ? AND library_id = ?;", nativeQuery = true)
    List<Reservation> findAllByBookIdAndLibraryId(int book_id, int library_id);


}*/
