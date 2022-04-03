package com.fabienit.biblioweb.service;

import com.fabienit.biblioweb.model.beans.BorrowBean;
import com.fabienit.biblioweb.model.dto.RegisteredUserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * WebappService
 */
public interface WebappService {

    ResponseEntity<Void> createUser(RegisteredUserDto accountDto);

    int getAuthenticatedRegisteredUserId();

    ResponseEntity<Void> extendBorrowDuration(int borrowId);

    List<BorrowBean> getActiveBorrowsByRegisteredUserId();
}