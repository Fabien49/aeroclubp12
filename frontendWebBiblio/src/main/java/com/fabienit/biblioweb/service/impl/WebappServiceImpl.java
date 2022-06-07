package com.fabienit.biblioweb.service.impl;

import com.fabienit.biblioweb.model.beans.*;
import com.fabienit.biblioweb.model.dto.RegisteredUserDto;
import com.fabienit.biblioweb.security.UserPrincipal;
import com.fabienit.biblioweb.service.WebappService;
import com.fabienit.biblioweb.web.proxies.ApiProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * WebappServiceImpl
 */
@Service
public class WebappServiceImpl implements WebappService {

    @Autowired
    private ApiProxy apiProxy;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Create Registered user bean with user input, call proxy api method to
     * register new user
     */
    @Override
    public ResponseEntity<Void> createUser(RegisteredUserDto accountDto) {

        logger.debug("Creating RegisteredUserBean for registration");

        // Create new RegisteredUserBean
        RegisteredUserBean registered = new RegisteredUserBean();

        // Fill it with user input
        registered.setFirstName(accountDto.getFirstName());
        registered.setLastName(accountDto.getLastName());
        registered.setEmail(accountDto.getEmail());
        registered.setPassword(accountDto.getPassword());
        registered.setRoles("USER");

        // Call api proxy method to register new user
        return apiProxy.addRegisteredUser(registered);
    }

    /**
     * Extract RegisteredUserBean from authenticated userPrincipal to return user id
     */
    @Override
    public int getAuthenticatedRegisteredUserId() {

        logger.debug("Getting authenticated current user id");

        // Get authentification context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get Authenticated userPrincipal
        UserPrincipal authenticatedUser = (UserPrincipal) authentication.getPrincipal();

        // Get user id
        int authenticatedRegisteredUserId = authenticatedUser.getUserPrincipal().getId();

        return authenticatedRegisteredUserId;
    }

    /**
     * Return true if a user is authenticated
     *
     * @return
     */
    @Override
    public Boolean getIsAuthenticated() {

        // Get authentification context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return true;
    }

    /**
     * Extend duration for the target borrow
     */
    /*@Override
    public ResponseEntity<Void> extendBorrow(int borrowId, BorrowBean borrowBean) {

        logger.debug("Extending Borrow bean duration");

        // Get borrow data
        BorrowBean extendedBorrow = new BorrowBean();
        extendedBorrow = apiProxy.getBorrowById(borrowId);

        // Create updated borrow bean
        BorrowBean updatedBorrow = extendedBorrow;

        // Add 4 weeks to return date
        updatedBorrow.setReturnDate(extendedBorrow.getReturnDate().plusWeeks(4));

        // Set true extended duration attribute
        updatedBorrow.setExtendedDuration(true);

        // Update database
        return apiProxy.updateBorrow(updatedBorrow.getId(), updatedBorrow);

    }*/

    /**
     * Get active borrows (bookReturned = false) for current authenticated user
     */
    @Override
    public List<BorrowBean> getActiveBorrowsByRegisteredUserId() {

        logger.debug("Getting active borrows for current authenticated user");

        // Get authenticated user id
        int authenticatedUserId = getAuthenticatedRegisteredUserId();

        // Init currentUserActiveBorrows list
        List<BorrowBean> currentUserActiveBorrows = new ArrayList<>();

        // Get list of borrows by user id
        List<BorrowBean> authenticatedUserBorrows = apiProxy.getBorrowsByUserId(authenticatedUserId);

        // Record only active borrows in currentUserActiveBorrows list
        for (BorrowBean borrowBean : authenticatedUserBorrows) {
            if (!borrowBean.getBookReturned()) {
                currentUserActiveBorrows.add(borrowBean);
            }
        }

        return currentUserActiveBorrows;
    }

    /**
     * Get reservation list for authenticated user
     *
     * @return
     */
    @Override
    public List<ReservationBean> getReservationsByRegisteredUserId() {

        logger.debug("Getting reservations for current authenticated user");

        // Get authenticated user id
        int authenticatedUserId = getAuthenticatedRegisteredUserId();

        // Init current user reservation list
        List<ReservationBean> currentUserReservations = new ArrayList<>();

        // Get list of reservation by user id
        List<ReservationBean> authenticatedUserReservationsList = apiProxy.getReservationByRegisteredUser(authenticatedUserId);

        return authenticatedUserReservationsList;
    }

    /**
     * Create reservation bean
     */
    @Override
    public ReservationBean createReservation(int bookId, int libraryId) {

        AvailableCopieBean availableCopie = new AvailableCopieBean();
        availableCopie.setId(new AvailableCopieKeyBean(bookId, libraryId));

        RegisteredUserBean registeredUser = new RegisteredUserBean();
        registeredUser.setId(getAuthenticatedRegisteredUserId());

        ReservationBean reservation = new ReservationBean();
        reservation.setAvailableCopie(availableCopie);
        reservation.setRegistereduser(registeredUser);
        reservation.setNotificationIsSent(false);
        reservation.setAvailabilityDate(null);
        reservation.setPosition(0);

        return reservation;
    }

    /**
     * Get list of book already reserved by authenticated user
     *
     * @param authenticatedUserId
     * @return
     */
    @Override
    public List<Integer> getBookIdReservationList(int authenticatedUserId) {

        List<Integer> bookIdReservationList = new ArrayList<>();

        List<ReservationBean> reservationList = apiProxy.getReservationByRegisteredUser(authenticatedUserId);

        for (ReservationBean reservation : reservationList) {
            bookIdReservationList.add(reservation.getAvailableCopie().getId().getBookId());
        }

        return bookIdReservationList;
    }

    /**
     * Get list of book already borrowed by authenticated user
     *
     * @return
     */
    @Override
    public List<Integer> getBookIdActiveBorrowList() {

        List<Integer> bookIdActiveBorrowList = new ArrayList<>();

        List<BorrowBean> borrowList = getActiveBorrowsByRegisteredUserId();

        for (BorrowBean borrow : borrowList) {
            bookIdActiveBorrowList.add(borrow.getBook().getId());
        }
        return bookIdActiveBorrowList;
    }

    /**
     * Get AvailableCopie list and set nearestReturnDateString for each instance
     */
    @Override
    public List<AvailableCopieBean> getAvailableCopies() {
        List<AvailableCopieBean> copiesList = apiProxy.getAvailableCopies();
        return copiesList;
    }
}