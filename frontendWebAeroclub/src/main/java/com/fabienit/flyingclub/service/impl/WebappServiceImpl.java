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
     * @Return addRegisteredUser
     */
    @Override
    public ResponseEntity<Void> createUser(RegisteredUserDto accountDto) {

        logger.debug("Creating RegisteredUserBean for registration");

        RegisteredUserBean registered = new RegisteredUserBean();

        registered.setFirstName(accountDto.getFirstName());
        registered.setLastName(accountDto.getLastName());
        registered.setEmail(accountDto.getEmail());
        registered.setPassword(accountDto.getPassword());
        registered.setHours(accountDto.getHours());
        registered.setRoles(accountDto.getRole());

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
     * @return authentication
     */
    @Override
    public Boolean getIsAuthenticated() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    /**
     * Get registeredUser by Id
     * @Param id
     */
    @Override
    public RegisteredUserBean getRegisteredUserById(int id) {
        return apiProxy.getRegisteredUserById(id);
    }

    /**
     * Get registeredUser list for authenticated user
     * @Return List registeredUser
     */
    @Override
    public List<RegisteredUserBean> getAllRegisteredUsers() {
        return apiProxy.getRegisteredUsers();
    }

    /**
     * Put registeredUser for authenticated user
     * @Param id,
     * @Param registeredUser
     * @Return updateRegisteredUser
     */
    @Override
    public RegisteredUserBean updateRegisteredUser(int id, RegisteredUserBean updateRegisteredUserBean) {
        return apiProxy.updateRegisteredUser(id, updateRegisteredUserBean);
    }

    /**
     * Get reservation list for authenticated user
     * @Return getAllReservation
     */
    @Override
    public List<ReservationBean> getAllReservation(){
        return apiProxy.getAllReservation();
    }

    /**
     * Get reservation by id for authenticated user
     * @Param id
     * @Return getReservationById
     */
    @Override
    public ReservationBean getReservationById(int id) {
        return apiProxy.getReservationById(id);
    }

    /**
     * Get reservation list for authenticated user
     * @Return getReservationByRegisteredUser
     */
    @Override
    public List<ReservationBean> getReservationsByRegisteredUserId() {

        logger.debug("Getting reservations for current authenticated user");

        int authenticatedUserId = getAuthenticatedRegisteredUserId();

        return apiProxy.getReservationByRegisteredUser(authenticatedUserId);
    }

    /**
     * Get reservation by id and dates for authenticated user
     * @Param id, startDate, endDate
     * @Return getReservationByIdAndDate
     */
    @Override
    public boolean getReservationByIdAndDate(int id, LocalDate startDate, LocalDate endDate) {
        return apiProxy.getReservationByIdAndDate(id, startDate, endDate);
    }

    /**
     * Create Aircraft with authenticated user
     * @Return aircraftBean
     */
    @Override
    public AircraftBean createAircraft(AircraftBean aircraftBean) {
        return aircraftBean;
    }

    /**
     * Get aircraft by id for authenticated user
     * @Param id
     * @Return getAircraftById
     */
    @Override
    public AircraftBean getAircraftById(int id) {
        return apiProxy.getAircraftById(id);
    }

    /**
     * Get aircraft by reservation id for authenticated user
     * @Param id
     * @Return getAircraftByReservationId
     */
    @Override
    public AircraftBean getAircraftByReservationId(int id) {
        return apiProxy.getAircraftByReservationId(id);
    }


    /**
     * Put aircraft for authenticated user
     * @Param id, aircraftBean
     * @Return updateAircraft
     */
    @Override
    public AircraftBean updateAircraft(int id, AircraftBean aircraftBean) {

        if (aircraftBean.getMotorHours() >= 10000) {
            aircraftBean.setAvailable(false);
            aircraftBean = apiProxy.updateAircraft(id, aircraftBean);
            WorkshopBean workshopBean = new WorkshopBean();
            workshopBean.setAircraft(apiProxy.getAircraftById(id));
            workshopBean.setEntryDate(LocalDate.now());
            workshopBean.setMotorChange(false);
            workshopBean.setHelixChange(false);
            workshopBean.setManual(false);
            workshopBean.setCanceled(false);
            apiProxy.addWorkshop(workshopBean);
        } else {
            aircraftBean = apiProxy.updateAircraft(id, aircraftBean);
        }

        return aircraftBean;
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
        return apiProxy.getWorkshopById(id);
    }

    @Override
    public ResponseEntity<Void> createWorkshop(WorkshopBean workshopBean) {

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

    /**
     * Cancelled intervention by id.
     * @Param id
     * @Return ResponseEntity<Void>
     * @Throws EntityNotFoundException
     */
    @Override
    public ResponseEntity<Void>  canceledIntervention(int id) {
        WorkshopBean workshopBean = apiProxy.getWorkshopById(id);
        AircraftBean aircraftBean = apiProxy.getWorkshopById(id).getAircraft();
        aircraftBean.setAvailable(true);

        if (workshopBean != null) {
            workshopBean.setCanceled(true);
            apiProxy.updateAircraft(aircraftBean.getId(),aircraftBean);
            apiProxy.updateWorkshop(id, workshopBean);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("L'intervention avec l'ID " + id + " n'a pas été trouvée.");
        }
    }

    @Override
    public ResponseEntity<Void> createReservation(ReservationDto reservationDto) {

        ReservationBean reservationBean = reservationMapper.convertToEntity(reservationDto);

        return apiProxy.addReservation(reservationBean);
    }

    @Override
    public ResponseEntity<Void> updateAircraftReservation(int id, ReservationBean reservationBean) {
        return apiProxy.updateAircraftReservation(id, reservationBean);
    }

    @Override
    public ResponseEntity<Void> updateReservation(int id, ReservationBean reservationBean) {
        return apiProxy.updateReservation(id, reservationBean);
    }

    /**
     * Cancelled reservation by id.
     * @Param id
     * @Return ResponseEntity<Void>
     * @Throws EntityNotFoundException
     */
    @Override
    public ResponseEntity<Void> canceledReservation(int id) {
        ReservationBean reservationBean = apiProxy.getReservationById(id);

        if (reservationBean != null) {
            reservationBean.setCanceled(true);
            apiProxy.updateReservation(id, reservationBean);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("La réservation avec l'ID " + id + " n'a pas été trouvée.");
        }
    }

}





