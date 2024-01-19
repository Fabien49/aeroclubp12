package com.fabienit.flyingclub.service.impl;

import com.fabienit.flyingclub.model.beans.*;
import com.fabienit.flyingclub.model.dto.*;
import com.fabienit.flyingclub.model.mappers.AircraftMapper;
import com.fabienit.flyingclub.model.mappers.ReservationMapper;
import com.fabienit.flyingclub.security.UserPrincipal;
import com.fabienit.flyingclub.service.WebappService;
import com.fabienit.flyingclub.web.proxies.ApiProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * WebappServiceImpl
 */
@Service
public class WebappServiceImpl implements WebappService {

    private final ReservationMapper reservationMapper;

    private final AircraftMapper aircraftMapper;
    private final ApiProxy apiProxy;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    public WebappServiceImpl(ReservationMapper reservationMapper, AircraftMapper aircraftMapper, ApiProxy apiProxy) {
        this.reservationMapper = reservationMapper;
        this.aircraftMapper = aircraftMapper;
        this.apiProxy = apiProxy;
    }

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

    @Override
    public RegisteredUserBean getRegisteredUserById(int id) {
        // Obtient l'utilisateur enregistré à partir de votre repository
        // Cette logique dépend de votre modèle d'utilisateur et de votre repository
            RegisteredUserBean registeredUserBean = apiProxy.getRegisteredUserById(id);
            return registeredUserBean;
        }

    @Override
    public RegisteredUserBean updateRegisteredUser(int id, RegisteredUserBean updateRegisteredUserBean) {

        updateRegisteredUserBean = apiProxy.updateRegisteredUser(id, updateRegisteredUserBean);

        return updateRegisteredUserBean;
    }


    @Override
    public ResponseEntity<Void> modifyUserHours(int id, String action, int hoursToAdd) {
        // Effectuez la logique métier pour modifier les heures de l'utilisateur
        // Vous pouvez utiliser le repository pour récupérer et mettre à jour les données
        RegisteredUserBean registeredUserBean = apiProxy.getRegisteredUserById(id);

        if ("add".equals(action)) {
            registeredUserBean.setHours(registeredUserBean.getHours() + hoursToAdd);
        } else if ("subtract".equals(action)) {
            int remainingHours = registeredUserBean.getHours() - hoursToAdd;
            registeredUserBean.setHours(Math.max(0, remainingHours));
        }

       return apiProxy.modifyRegisteredUserHours(id, action, hoursToAdd);
    }

    @Override
    public int getUserTotalHours(int id) {
        // Utilisez le repository pour récupérer les heures actuelles de l'utilisateur
        RegisteredUserBean registeredUserBean = apiProxy.getRegisteredUserById(id);
        return registeredUserBean.getHours();
    }

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

    @Override
    public ReservationBean getReservationById(int id) {
        return apiProxy.getReservationById(id);
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
        /*List<ReservationBean> currentUserReservations = new ArrayList<>();*/

        // Get list of reservation by user id
        List<ReservationBean> authenticatedUserReservationsList = apiProxy.getReservationByRegisteredUser(authenticatedUserId);

/*        for (Reservation reservation : authenticatedUserReservationsList) {
            // Récupérer l'avion associé à chaque réservation (exemple)
            AircraftBean aircraft = apiProxy.getAircrafts().get(authenticatedUserId);
                    reservation.setAircraft(aircraft);
        }*/

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + authenticatedUserReservationsList);

        return authenticatedUserReservationsList;
    }

    @Override
    public boolean getReservationByIdAndDate(int id, LocalDate startDate, LocalDate endDate) {
        return apiProxy.getReservationByIdAndDate(id, startDate, endDate);
    }

    @Override
    public AircraftBean createAircraft(AircraftBean aircraftBean) {
        return aircraftBean;
    }

    @Override
    public AircraftBean getAircraftById(int id) {

        AircraftBean aircraftBean = apiProxy.getAircraftById(id);

        return aircraftBean;
    }

    @Override
    public AircraftBean getAircraftByReservationId(int id) {
        return apiProxy.getAircraftByReservationId(id);
    }


    /**
     * Get list of aircraft already reserved by authenticated user
     *
     * @param authenticatedUserId
     * @return
     */
    @Override
    public List<Integer> getAircraftIdReservationsList(int authenticatedUserId) {

        List<Integer> bookIdReservationList = new ArrayList<>();

        List<ReservationBean> reservationList = apiProxy.getReservationByRegisteredUser(authenticatedUserId);

/*        for (ReservationBean reservation : reservationList) {
            bookIdReservationList.add(reservation);
        }*/

        return bookIdReservationList;
    }

    @Override
    public AircraftBean updateAircraft(int id, AircraftBean aircraftBean) {

        if(aircraftBean.getMotorHours()>= 10000){
            aircraftBean.setAvailable(false);
            aircraftBean = apiProxy.updateAircraft(id, aircraftBean);
            WorkshopBean workshopBean = new WorkshopBean();
            workshopBean.setAircraft(apiProxy.getAircraftById(id));
            workshopBean.setEntryDate(LocalDate.now());
            workshopBean.setMotorChange(false);
            workshopBean.setHelixChange(false);
            apiProxy.addWorkshop(workshopBean);
        }else {
            aircraftBean = apiProxy.updateAircraft(id, aircraftBean);
        }


        return aircraftBean;
    }

    @Override
    public List<Integer> getBookIdActiveBorrowList() {
        return null;
    }


    @Override
    public List<AircraftBean> checkIfAircraftIsAvailable() {
        return null;
    }


    @Override
    public List<AircraftDto> getAvailableAircrafts() {
        return apiProxy.getAvailableAircrafts().stream()
                .map(aircraftMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AircraftDto> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return apiProxy.getAvailableAircraftsBetweenDates(startDate, endDate).stream()
                .map(aircraftMapper::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public WorkshopBean getWorkshopBeanById(int id) {

        WorkshopBean workshopBean = apiProxy.getWorkshopById(id);

        return workshopBean;
    }

    @Override
    public ResponseEntity<Void> createWorkshop(WorkshopBean workshopBean) {

        workshopBean.setAircraft(workshopBean.getAircraft());
        workshopBean.setHelixChange(!workshopBean.getHelixChange());
        workshopBean.setMotorChange(!workshopBean.getMotorChange());

        return apiProxy.addWorkshop(workshopBean);
    }

    @Override
    public WorkshopBean updateWorkshopBean(int id, WorkshopBean workshopBean) {
        return apiProxy.updateWorkshop(id, workshopBean);
    }

    @Override
    public WorkshopBean saveIntervention(int id, WorkshopBean workshopBean) {
        return apiProxy.saveIntervention(id, workshopBean);
    }

    public RegisteredUserReservationDto convertToDTO(RegisteredUserBean registeredUserBean) {
        RegisteredUserReservationDto registeredUserReservationDto = new RegisteredUserReservationDto();
        registeredUserReservationDto.setId(registeredUserBean.getId());
        // Ajoutez d'autres champs si nécessaire
        return registeredUserReservationDto;
    }

    @Override
    public  ResponseEntity<Void> createReservation(ReservationDto reservationDto) {

        ReservationBean reservationBean = reservationMapper.convertToEntity(reservationDto);
        System.out.println("reservationBean dans le webappService : " + reservationBean.toString());


        return    apiProxy.addReservation(reservationBean);
    }

    @Override
    public ResponseEntity<Void> updateAircraftReservation(int id, ReservationBean reservationBean) {
        return apiProxy.updateAircraftReservation(id, reservationBean);
    }

    @Override
    public ResponseEntity<Void> updateReservation(int id, ReservationBean reservationBean) {
        return apiProxy.updateReservation(id, reservationBean);
    }

    @Override
    public ResponseEntity<Void> canceledReservation(int id) {
        ReservationBean reservationBean = apiProxy.getReservationById(id);

        if (reservationBean != null) {
            reservationBean.setCanceled(true);

            // Sauvegarde de la réservation mise à jour dans la base de données
            apiProxy.updateReservation(id,reservationBean);
            return ResponseEntity.noContent().build();
        } else {
            // Gérer le cas où la réservation n'est pas trouvée
            throw new EntityNotFoundException("La réservation avec l'ID " + id + " n'a pas été trouvée.");
        }

    }



}





