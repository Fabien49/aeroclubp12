package com.fabienit.biblioweb.service;

import com.fabienit.biblioweb.model.beans.AvailableCopieBean;
import com.fabienit.biblioweb.model.beans.BorrowBean;
import com.fabienit.biblioweb.model.beans.ReservationBean;
import com.fabienit.biblioweb.model.dto.RegisteredUserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * WebappService
 */
public interface WebappService {

    ResponseEntity<Void> createUser(RegisteredUserDto accountDto);

    int getAuthenticatedRegisteredUserId();

    Boolean getIsAuthenticated();

//    ResponseEntity<Void> extendBorrow(int borrowId, BorrowBean borrowBean);

    List<BorrowBean> getActiveBorrowsByRegisteredUserId();

    List<ReservationBean> getReservationsByRegisteredUserId();

    ReservationBean createReservation(int bookId, int libraryId);

    List<Integer> getBookIdReservationList(int authenticatedUserId);

    List<Integer> getBookIdActiveBorrowList();

    List<AvailableCopieBean> getAvailableCopies();
}