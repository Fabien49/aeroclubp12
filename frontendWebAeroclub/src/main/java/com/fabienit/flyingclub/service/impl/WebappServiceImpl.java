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
     * @return addRegisteredUser
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
     * @return authenticatedRegisteredUserId
     */
    @Override
    public int getAuthenticatedRegisteredUserId() {

        logger.debug("Getting authenticated current user id");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal authenticatedUser = (UserPrincipal) authentication.getPrincipal();

        int authenticatedRegisteredUserId = authenticatedUser.getUserPrincipal().getId();

        return authenticatedRegisteredUserId;
    }

    /**
     * Return true if a user is authenticated
     * @return authentication
     */
    @Override
    public Boolean getIsAuthenticated() {

        logger.debug("Getting isAuthenticated ");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    /**
     * Get registeredUser by Id
     * @param id
     * @return apiProxy.getRegisteredUserById
     */
    @Override
    public RegisteredUserBean getRegisteredUserById(int id) {

        logger.debug("Getting authenticated current user by id");

        return apiProxy.getRegisteredUserById(id);
    }

    /**
     * Get registeredUser list for authenticated user
     * @return List registeredUser
     */
    @Override
    public List<RegisteredUserBean> getAllRegisteredUsers() {

        logger.debug("Getting all RegisteredUsers");

        return apiProxy.getRegisteredUsers();
    }

    /**
     * Put registeredUser for authenticated user
     * @param id,
     * @param updateRegisteredUserBean
     * @return updateRegisteredUser
     */
    @Override
    public RegisteredUserBean updateRegisteredUser(int id, RegisteredUserBean updateRegisteredUserBean) {

        logger.debug("Updating RegisteredUser by id");

        return apiProxy.updateRegisteredUser(id, updateRegisteredUserBean);
    }

    /**
     * Create Aircraft with authenticated user
     * @return aircraftBean
     */
    @Override
    public AircraftBean createAircraft(AircraftBean aircraftBean) {

        logger.debug("Creating AircraftBean for addAircraft");

        return aircraftBean;
    }

    /**
     * Get aircraft by id for authenticated user
     * @param id
     * @return getAircraftById
     */
    @Override
    public AircraftBean getAircraftById(int id) {

        logger.debug("Getting authenticated current user id");

        return apiProxy.getAircraftById(id);
    }

    /**
     * Get aircraft by reservation id for authenticated user
     * @param id
     * @return getAircraftByReservationId
     */
    @Override
    public AircraftBean getAircraftByReservationId(int id) {

        logger.debug("Getting Aircraft by ReservationId");

        return apiProxy.getAircraftByReservationId(id);
    }


    /**
     * Put aircraft for authenticated user
     * @param id
     * @param aircraftBean
     * @return updateAircraft
     */
    @Override
    public AircraftBean updateAircraft(int id, AircraftBean aircraftBean) {

        logger.debug("Updating AircraftBean by id");

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

    /**
     * Get all availableAircrafts for authenticated user
     * @return apiProxy.getAvailableAircrafts
     */
    @Override
    public List<AircraftDto> getAvailableAircrafts() {

        logger.debug("Getting all availableAircrafts");

        return apiProxy.getAvailableAircrafts().stream()
                .map(aircraftMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all availableAircrafts between dates for authenticated user
     * @param startDate
     * @param endDate
     * @return apiProxy.getAvailableAircraftsBetweenDates
     */
    @Override
    public List<AircraftDto> getAvailableAircraftsBetweenDates(LocalDate startDate, LocalDate endDate) {

        logger.debug("Getting availableAircrafts between dates for addAircraftReservation and/or updateAircraftReservation");

        return apiProxy.getAvailableAircraftsBetweenDates(startDate, endDate).stream()
                .map(aircraftMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create Reservation with authenticated user
     * @return addReservation
     */
    @Override
    public ResponseEntity<Void> createReservation(ReservationDto reservationDto) {

        logger.debug("Creating ReservationDto for addReservation");

        ReservationBean reservationBean = reservationMapper.convertToEntity(reservationDto);

        return apiProxy.addReservation(reservationBean);
    }

    /**
     * Get all reservation for authenticated user
     * @return getAllReservation
     */
    @Override
    public List<ReservationBean> getAllReservation(){

        logger.debug("Getting all Reservations");

        return apiProxy.getAllReservation();
    }

    /**
     * Get reservation by id for authenticated user
     * @param id
     * @return getReservationById
     */
    @Override
    public ReservationBean getReservationById(int id) {

        logger.debug("Getting Reservation by id");

        return apiProxy.getReservationById(id);
    }

    /**
     * Get reservation list for authenticated user
     * @return getReservationByRegisteredUser
     */
    @Override
    public List<ReservationBean> getReservationsByRegisteredUserId() {

        logger.debug("Getting reservations for current authenticated user");

        int authenticatedUserId = getAuthenticatedRegisteredUserId();

        return apiProxy.getReservationByRegisteredUser(authenticatedUserId);
    }

    /**
     * Get reservation by id and dates for authenticated user
     * @param id
     * @param startDate
     * @param endDate
     * @return getReservationByIdAndDate
     */
    @Override
    public boolean getReservationByIdAndDate(int id, LocalDate startDate, LocalDate endDate) {

        logger.debug("Getting Reservation by id and dates for updateAircraftReservation");

        return apiProxy.getReservationByIdAndDate(id, startDate, endDate);
    }

    /**
     * Put aircraftReservation for authenticated user
     * @param id
     * @param reservationBean
     * @return updateAircraftReservation
     */
    @Override
    public ResponseEntity<Void> updateAircraftReservation(int id, ReservationBean reservationBean) {

        logger.debug("Updating AircraftReservationBean by id");

        return apiProxy.updateAircraftReservation(id, reservationBean);
    }

    /**
     * Put reservation for authenticated user
     * @param id
     * @param reservationBean
     * @return updateReservation
     */
    @Override
    public ResponseEntity<Void> updateReservation(int id, ReservationBean reservationBean) {

        logger.debug("Updating ReservationBean by id");

        return apiProxy.updateReservation(id, reservationBean);
    }

    /**
     * Cancelled reservation by id.
     * @param id
     * @return reservationBean
     * @throws EntityNotFoundException
     */
    @Override
    public ResponseEntity<Void> canceledReservation(int id) {

        logger.debug("Cancelling Reservation by id");

        ReservationBean reservationBean = apiProxy.getReservationById(id);

        if (reservationBean != null) {
            reservationBean.setCanceled(true);
            apiProxy.updateReservation(id, reservationBean);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("La réservation avec l'ID " + id + " n'a pas été trouvée.");
        }
    }


    /**
     * Get workshopBean by id for authenticated user
     * @param id
     * @return getWorkshopById
     */
    @Override
    public WorkshopBean getWorkshopBeanById(int id) {

        logger.debug("Getting workshopBean by id");

        return apiProxy.getWorkshopById(id);
    }

    /**
     * Post workshopBean for authenticated user
     * @param workshopBean
     * @return apiProxy.addWorkshop
     */
    @Override
    public ResponseEntity<Void> createWorkshop(WorkshopBean workshopBean) {

        logger.debug("Creating WorkshopBean for addWorkshop");

        workshopBean.setHelixChange(!workshopBean.getHelixChange());
        workshopBean.setMotorChange(!workshopBean.getMotorChange());

        return apiProxy.addWorkshop(workshopBean);
    }


    /**
     * Put workshopBean by id for authenticated user
     * @param id
     * @param workshopBean
     * @return apiProxy.updateWorkhop
     */
    @Override
    public WorkshopBean updateWorkshopBean(int id, WorkshopBean workshopBean) {

        logger.debug("Updating WorkshopBean by id");

        return apiProxy.updateWorkshop(id, workshopBean);
    }

    /**
     * Post intervention for authenticated user
     * @param id It's the id of the aircraft that's going to be put in the workshop
     * @param workshopBean The object representing the workshop on which the operation is to be performed
     * @return getAllReservation
     */
    @Override
    public WorkshopBean saveIntervention(int id, WorkshopBean workshopBean) {

        logger.debug("Creating WorkshopBean by id");

        return apiProxy.saveIntervention(id, workshopBean);
    }

    /**
     * Cancelled intervention by id.
     * @param id
     * @return ResponseEntity<Void>
     * @throws EntityNotFoundException
     */
    @Override
    public ResponseEntity<Void>  canceledIntervention(int id) {

        logger.debug("Cancelling Intervention by id");

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

}





