package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.model.dto.CanceledReservationDto;
import com.fabienit.flyingclub.model.dto.RegisteredUserDto;
import com.fabienit.flyingclub.model.dto.ReservationDto;
import com.fabienit.flyingclub.model.mappers.AircraftMapper;
import com.fabienit.flyingclub.model.mappers.ReservationMapper;
import com.fabienit.flyingclub.service.WebappService;
import com.fabienit.flyingclub.web.proxies.ApiProxy;
import com.fabienit.flyingclub.model.beans.*;
import com.fabienit.flyingclub.model.dto.AircraftDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

/**
 * WebappController
 */
@Controller
public class WebappController {


    private final ApiProxy apiProxy;
    private final WebappService webappService;

    private final AircraftMapper aircraftMapper;

    private final ReservationMapper reservationMapper;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public WebappController(ApiProxy apiProxy, WebappService webappService, AircraftMapper aircraftMapper, ReservationMapper reservationMapper) {
        this.apiProxy = apiProxy;
        this.webappService = webappService;
        this.aircraftMapper = aircraftMapper;
        this.reservationMapper = reservationMapper;
    }

    @GetMapping(value = "/")
    public String mainPage() {

        logger.info("Reach url: / - GET");

        return "Home";
    }

    @GetMapping(value = "/aircrafts")
    public String getAircraftsPage(Model model) {

        logger.info("Reach url: /aircrafts - GET");

/*       AircraftBean aircraftBean = new AircraftBean();
        model.addAttribute("addAircraft", aircraftBean);*/


        List<AircraftBean> aircrafts = apiProxy.getAircrafts();
        model.addAttribute("aircrafts", aircrafts);
        System.out.println("############################### Voici la liste des avions : " + aircrafts);





        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated){
            int authenticatedUserId = webappService.getAuthenticatedRegisteredUserId();


        }

        return "Aircrafts";
    }

    @GetMapping(value = "/addAircraft")
    public String getAddAircraftPage(Model model){

        logger.info("Reach url: /addAircraft - GET");
        AircraftBean aircraftBean = new AircraftBean();
        model.addAttribute("aircraftBean", aircraftBean);

        return "AddAircraft";
    }

    @PostMapping(value = "/addAircraft")
    public String addAircraft(@Valid AircraftBean aircraftBean, RedirectAttributes redirectAttributes){

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&& objet aircraft depuis *****WebappController*****" + aircraftBean);
        logger.info("Reach url: /addAircraft - POST");



         aircraftBean = webappService.createAircraft(aircraftBean);

        System.out.println("objet aircraft depuis *****WebappController*****" + aircraftBean);

        ResponseEntity<Void> response = null;
        int status = 0;

        try {
            response = apiProxy.addAircraft(aircraftBean);
            status = response.getStatusCodeValue();
        } catch (FeignException e) {
            logger.debug(e.getMessage());
            logger.debug(e.getLocalizedMessage());
            status = e.status();
            e.printStackTrace();
        }

        if(status == 201){
            redirectAttributes.addAttribute("aircraftStatus", "success");
        }
        return "redirect:/aircrafts";
    }

    @RequestMapping(value = "/aircrafts/{id}")
    public String deleteAircraft(@PathVariable int id, RedirectAttributes redirectAttributes) {

        System.out.println("4545445455454545445454545454545554 : " + id);
        apiProxy.deleteAircraft(id);
        /*redirectAttributes.addAttribute("deletedAircraft", true);*/
        return "redirect:/aircrafts";
    }

    @GetMapping("/aircrafts/updateAircraft/{id}")
    public String upddateAircraftForm(Model model, @PathVariable int id){
        logger.info("Reach url: /profile/updateRegisteredUserForm - GET");

        AircraftBean aircraftBean = webappService.getAircraftById(id);

            model.addAttribute("aircraft", aircraftBean);


        return "UpdateAircraft";
    }

    @PostMapping("/aircrafts/updateAircraft/{id}")
    public String updateAircraft(@PathVariable int id, @ModelAttribute("aircraft") AircraftBean updateAircraftFormDto, BindingResult result, RedirectAttributes redirectAttributes, Model model){

// Valider les données mises à jour
        if (result.hasErrors()) {
            // Si des erreurs de validation existent, retourner à la page de mise à jour avec les erreurs
            return "UpdateAircraft";
        }

        updateAircraftFormDto.setMark(updateAircraftFormDto.getMark());
        updateAircraftFormDto.setType(updateAircraftFormDto.getType());
        updateAircraftFormDto.setMotor(updateAircraftFormDto.getMotor());
        updateAircraftFormDto.setPower(updateAircraftFormDto.getPower());
        updateAircraftFormDto.setSeats(updateAircraftFormDto.getSeats());
        updateAircraftFormDto.setAutonomy(updateAircraftFormDto.getAutonomy());
        updateAircraftFormDto.setAircraftHours(updateAircraftFormDto.getAircraftHours());
        updateAircraftFormDto.setAvailable(updateAircraftFormDto.getAvailable());

        // Mettre à jour l'utilisateur
        try {
            webappService.updateAircraft(id, updateAircraftFormDto);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès!");
        } catch (FeignException e) {
            // Gérer les erreurs liées à la mise à jour de l'utilisateur
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }

        model.addAttribute("updateAircraftForm", updateAircraftFormDto);

        // Rediriger vers une page de confirmation ou une autre vue
        return "redirect:/aircrafts";
    }


    @GetMapping(value = "/flyingClub")
    public String getFlyingClub(Model model){

        List<FlyingClubBean> flyingClubBean = apiProxy.getFlyingClub();
        model.addAttribute("flyingClub", flyingClubBean);

        System.out.println("############################### Voici la liste des aéroclubs : " + flyingClubBean);

/*        List<AircraftBean> aircrafts = apiProxy.getAircrafts();
        int nbAircrafts = aircrafts.size();
        model.addAttribute("aircraftsSize", nbAircrafts);*/

        return "FlyingClub";
    }




    @GetMapping(value = "/registration")
    public String getRegistrationPage(Model model) {

        logger.info("Reach url: /registration - GET");

        model.addAttribute("user", new RegisteredUserDto());

        return "Registration";
    }

    @PostMapping(value = "/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid RegisteredUserDto accountDto, BindingResult result, Model model) {

        logger.info("Reach url: /registration - POST");

        ResponseEntity<Void> response = null;
        int status = 0;
        if (!result.hasErrors()) {

            try {
                response = webappService.createUser(accountDto);
                status = response.getStatusCodeValue();
            } catch (FeignException e) {
                logger.debug(e.getMessage());
                logger.debug(e.getLocalizedMessage());
                status = e.status();
                e.printStackTrace();
            }
            if (status == 400)
                result.addError(new ObjectError("emailAreadyExist",
                        "Cette adresse email est déjà liée à un compte utilisateur"));
        }

        if (result.hasErrors()) {
            model.addAttribute("user", accountDto);
            return "Registration";
        } else {
            model.addAttribute("user", accountDto);
            return "SuccesRegister";
        }

    }

    @GetMapping(value = "/connexion")
    public String getLoginPage(Model model) {

        logger.info("Reach url: /connexion - GET");

        model.addAttribute("user", new RegisteredUserDto());
        return "Login";
    }

    @GetMapping(value = "/profile")
    public String getProfilePage(Model model) {

        logger.info("Reach url: /profile - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int id = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(id);

            // Get registeredUser
             registeredUserBean = webappService.getRegisteredUserById(id);

            model.addAttribute("registeredUser", registeredUserBean);
        }


        return "Profile";
    }

    @GetMapping("/profile/updateRegisteredUser")
    public String upddateRegisteredUserForm(Model model){
        logger.info("Reach url: /profile/updateRegisteredUserForm - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int id = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(id);

            // Get registeredUser
            registeredUserBean = webappService.getRegisteredUserById(id);

            model.addAttribute("registeredUser", registeredUserBean);

            System.out.println("888888888888888888888888888888888888888888888888888888888888" + registeredUserBean);
        }

        return "UpdateRegisteredUser";
    }

    @PostMapping("/profile/updateRegisteredUser/{id}")
    public String updateRegisteredUser(@PathVariable int id, @ModelAttribute("registeredUser") RegisteredUserBean updateRegisteredUserFormDto, BindingResult result, RedirectAttributes redirectAttributes, Model model){

// Valider les données mises à jour
        if (result.hasErrors()) {
            // Si des erreurs de validation existent, retourner à la page de mise à jour avec les erreurs
            return "UpdateRegisteredUser";
        }

        updateRegisteredUserFormDto.setLastName(updateRegisteredUserFormDto.getLastName());
        updateRegisteredUserFormDto.setFirstName(updateRegisteredUserFormDto.getFirstName());
        updateRegisteredUserFormDto.setEmail(updateRegisteredUserFormDto.getEmail());
        updateRegisteredUserFormDto.setHours(updateRegisteredUserFormDto.getHours());
        updateRegisteredUserFormDto.setPassword(updateRegisteredUserFormDto.getPassword());
        updateRegisteredUserFormDto.setRoles(updateRegisteredUserFormDto.getRoles());

        System.out.println(updateRegisteredUserFormDto.getRoles());


        // Mettre à jour l'utilisateur
        try {
            webappService.updateRegisteredUser(id, updateRegisteredUserFormDto);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès!");
        } catch (FeignException e) {
            // Gérer les erreurs liées à la mise à jour de l'utilisateur
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }

        model.addAttribute("updateRegisteredUserFormDto", updateRegisteredUserFormDto);

        System.out.println("77777777777777777777777777777777777777777777777777777777" + updateRegisteredUserFormDto);

        // Rediriger vers une page de confirmation ou une autre vue
        return "redirect:/profile";
    }


    @PutMapping("/modify-hours/{id}/{action}/{hoursToAdd}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> modifyHours(
            @PathVariable int id,
            @PathVariable String action,
            @PathVariable int hoursToAdd) {

        try {
            // Effectuez les opérations nécessaires pour modifier les heures côté serveur
            webappService.modifyUserHours(id, action, hoursToAdd);

            // Renvoyez une réponse avec les nouvelles heures à afficher côté client
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Modification réussie");
            response.put("newTotalHours", webappService.getUserTotalHours(id));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // En cas d'erreur, renvoyez une réponse d'erreur
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors de la modification des heures : " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping(value = "/profile")
    public String extendBorrowDuration(@RequestParam int borrowId) {

        logger.info("Reach url: /profile - POST");

        // Get Borrow
        BorrowBean borrowBean = apiProxy.getBorrowById(borrowId);

        // Extend borrow duration
        apiProxy.extendBorrow(borrowId, borrowBean);

        return "redirect:/profile";
    }


    @GetMapping(value = "/reservations")
    public String getReservationPage(Model model) {

        logger.info("Reach url: /reservations - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int id = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(id);


            // Get available aircrafts
            List<AircraftDto> availableAircrafts = webappService.getAvailableAircrafts();
            model.addAttribute("availableAircrafts", availableAircrafts);


            // Get reservations list for current user
            List<ReservationBean> currentUserReservations = webappService.getReservationsByRegisteredUserId();

            model.addAttribute("currentDate", LocalDate.now());
            model.addAttribute("currentUserReservations", currentUserReservations);

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setRegisteredUserId(id);
            model.addAttribute("reservationDto", reservationDto);

            CanceledReservationDto canceledReservationDto = new CanceledReservationDto();
            canceledReservationDto.setCanceled(true);
            model.addAttribute("canceledReservationDto",canceledReservationDto);


        }

        return "Reservations";
    }

    @GetMapping(value = "/addDateReservation")
    public String getAddDateReservationPage(Model model) {

        logger.info("Reach url: /addDateReservation - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int id = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(id);

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setRegisteredUserId(id);
            model.addAttribute("reservationDto", reservationDto);
            model.addAttribute("currentDate", LocalDate.now());


        }

        return "AddDateReservation";
    }

    @GetMapping(value = "/addAircraftReservation")
    public String getAddAircraftReservationPage(@ModelAttribute("reservationDto") ReservationDto reservationDto, Model model) {

        logger.info("Reach url: /addAircraftReservation - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int id = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(id);

            reservationDto.setRegisteredUserId(id);
            model.addAttribute("reservationDto", reservationDto);
            List<AircraftDto> aircraftList = webappService.getAvailableAircraftsBetweenDates(reservationDto.getBorrowingDate(), reservationDto.getReturnDate());
            model.addAttribute("aircraftList", aircraftList);

        }

        return "AddAircraftReservation";
    }




    @GetMapping("/getAvailableAircrafts")
    @ResponseBody
    public List<AircraftDto> getAvailableAircrafts( @RequestParam("borrowingDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date borrowingDate,
                                                    @RequestParam("returnDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnDate, Model model) {

        // Get available aircrafts
        List<AircraftDto> availableAircrafts = webappService.getAvailableAircrafts();
        model.addAttribute("availableAircrafts", availableAircrafts);

        System.out.println("la date d'emprunt est : " + borrowingDate + " et la date de retour est : " + returnDate);




            List<AircraftBean> availableAircraftsBean = new ArrayList<>();
            List<ReservationBean> reservations = apiProxy.getReservations(availableAircraftsBean);




            return availableAircrafts;
        }




    @PostMapping(value = "/saveReservation")
    public String saveReservation(@ModelAttribute ("reservationDto") ReservationDto reservationDto, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        System.out.println("test : " + reservationDto.toString());

        if (bindingResult.hasErrors()) {

            model.addAttribute("reservationDto", reservationDto);

            return "redirect:/reservations";
        }


        System.out.println("objet reservation depuis *****WebappController*****" + reservationDto);

        logger.info("Reach url: /saveReservation - POST");



        ResponseEntity<Void> response = null;
        int status = 0;

        try {
            response = webappService.createReservation(reservationDto);
            status = response.getStatusCodeValue();
        } catch (FeignException e) {
            logger.debug(e.getMessage());
            logger.debug(e.getLocalizedMessage());
            status = e.status();
            e.printStackTrace();
        }

        if(status == 201){
            redirectAttributes.addAttribute("reservationStatus", "success");
        }
        return "redirect:/reservations";
    }

    /**
     *
     */
    @PostMapping(value = "/canceledReservation/{id}")
    public String canceledReservation(@PathVariable int id, RedirectAttributes redirectAttributes) {

        logger.info("Reach url: /canceledReservation - POST");
        webappService.canceledReservation(id);

        System.out.println("La réservation que l'on veut annuler à l'ID suivant : " + id + "::::::::::::::::::::::::::::::");
        redirectAttributes.addAttribute("canceledReservation", true);
        return "redirect:/reservations";
    }

    /**
     *
     */
    @RequestMapping(value = "/reservations/{id}")
    public String deleteReservation(@PathVariable int id, RedirectAttributes redirectAttributes) {

        apiProxy.deleteReservation(id);
        redirectAttributes.addAttribute("deletedReservation", true);
        return "redirect:/reservations";
    }


    @GetMapping(value = "/addHours")
    public String getAddHoursPage(Model model){

        logger.info("Reach url: /addHours - GET");
        RegisteredUserBean registeredUserBean = new RegisteredUserBean();
        model.addAttribute("RegisteredUserBean", registeredUserBean);
        WorkshopBean workshopBean = new WorkshopBean();
        model.addAttribute("workshopBean", workshopBean);
        List<AircraftDto> aircraftDtoList = webappService.getAvailableAircrafts();
        model.addAttribute("aircraftAvailable", aircraftDtoList);

        return "ConfirmFinishReservation";
    }


    @GetMapping("/confirmFinishReservation/{id}")
    public String getConfirmFinishPage(@PathVariable int id, Model model) {
        ReservationBean reservation = webappService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        return "ConfirmFinishReservation";
    }

    @PostMapping("/confirmFinishReservation/{id}")
    public String confirmFinish(@PathVariable int id, @RequestParam("hours") int hours, Model model) {

        ReservationBean reservation = webappService.getReservationById(id);

        // Mettez à jour les heures du pilote
        RegisteredUserBean pilot = reservation.getRegisteredUser();
        pilot.setHours(pilot.getHours() + hours);
        webappService.updateRegisteredUser(reservation.getRegisteredUser().getId(), pilot);


        // Mettez à jour les heures de l'avion et les heures du moteur
        AircraftBean aircraft = reservation.getAircraft();
        aircraft.setAircraftHours(aircraft.getAircraftHours() + hours);
        aircraft.setMotorHours(aircraft.getMotorHours() + hours);
        webappService.updateAircraft(reservation.getAircraft().getId(), aircraft);



        // Marquez la réservation comme terminée
        reservation.setFinished(true);

        // Mettez à jour la réservation
        webappService.updateReservation(id, reservation);

        return "redirect:/reservations";
    }

    @GetMapping("/updateReservation/{id}")
    public String updateReservationForm(@PathVariable int id, Model model){

        ReservationDto reservationDto = reservationMapper.convertToDTO(webappService.getReservationById(id));


        model.addAttribute("reservationDto", reservationDto);
        model.addAttribute("currentDate", LocalDate.now());
        System.out.println("La date d'emprunt est : " + reservationDto.getBorrowingDate());
        System.out.println("La date de retour est : " + reservationDto.getReturnDate());


        return "updateReservationForm";
    }

    @GetMapping(value = "/updateAircraftReservation/{id}")
    public String getUpdateAircraftReservationPage(@PathVariable int id, @ModelAttribute("reservationDto") ReservationDto reservationDto, Model model) {

        logger.info("Reach url: /updateAircraftReservation - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int registeredUserId = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(registeredUserId);

            reservationDto.setRegisteredUserId(registeredUserId);
            model.addAttribute("reservationDto", reservationDto);
            System.out.println(reservationDto);
            List<AircraftDto> aircraftList = webappService.getAvailableAircraftsBetweenDates(reservationDto.getBorrowingDate(), reservationDto.getReturnDate());

            System.out.println("reseration existante : " + webappService.getReservationByIdAndDate(registeredUserId, reservationDto.getBorrowingDate(), reservationDto.getReturnDate()));

           if (webappService.getReservationByIdAndDate(registeredUserId, reservationDto.getBorrowingDate(), reservationDto.getReturnDate())){
               System.out.println(aircraftList);
               System.out.println("L'avion que l'on veut ajouter est  " + aircraftMapper.convertToDTO(webappService.getAircraftByReservationId(id)) );
               aircraftList.add(aircraftMapper.convertToDTO(webappService.getAircraftByReservationId(id)));
           }

            model.addAttribute("aircraftList", aircraftList);

        }

        return "updateAircraftReservation";
    }

    @PostMapping("/saveUpdateAircraftReservation/{id}")
    public String saveUpdateAircraftReservation (@PathVariable int id, @ModelAttribute("reservationDto") ReservationDto reservationDto, RedirectAttributes redirectAttributes,Model model, BindingResult result){

        logger.info("Reach url: /saveUpdateAircraftReservation - POST");

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            System.out.println("il y a une ou des erreurs de validation");
            return "updateAircraftReservation";
        }

        reservationDto.setId(reservationDto.getId());
        reservationDto.setBorrowingDate(reservationDto.getBorrowingDate());
        reservationDto.setReturnDate(reservationDto.getReturnDate());
        AircraftDto aircraftDto = aircraftMapper.convertToDTO(webappService.getAircraftById(reservationDto.getAircraftDto().getId()));
        reservationDto.setAircraftDto(aircraftDto);
        model.addAttribute("reservationDto", reservationDto);
        System.out.println("ICI SE TROUVE LA MARQUE DE l'AVION : " + reservationDto.getAircraftDto().getMark());
        System.out.println("ICI SE TROUVE L'ID DE l'AVION : " + reservationDto.getAircraftDto().getId());

        System.out.println("Voici le détail de la réservation que l'on veut mettre à jour : " + reservationDto);


        try {
            webappService.updateAircraftReservation(id, reservationMapper.convertToEntity(reservationDto));
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès!");
        } catch (FeignException e) {

            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }

        return "redirect:/reservations";
    }





    @PostMapping("/updateHours/{id}")
    public String updateHours(@PathVariable int id, @ModelAttribute("registeredUser") RegisteredUserBean updateRegisteredUserFormDto, BindingResult result, RedirectAttributes redirectAttributes, Model model){

// Valider les données mises à jour
        if (result.hasErrors()) {
            // Si des erreurs de validation existent, retourner à la page de mise à jour avec les erreurs
            return "UpdateHours";
        }

        updateRegisteredUserFormDto.setLastName(updateRegisteredUserFormDto.getLastName());
        updateRegisteredUserFormDto.setFirstName(updateRegisteredUserFormDto.getFirstName());
        updateRegisteredUserFormDto.setEmail(updateRegisteredUserFormDto.getEmail());
        updateRegisteredUserFormDto.setHours(updateRegisteredUserFormDto.getHours());
        updateRegisteredUserFormDto.setPassword(updateRegisteredUserFormDto.getPassword());
        updateRegisteredUserFormDto.setRoles(updateRegisteredUserFormDto.getRoles());

        System.out.println(updateRegisteredUserFormDto.getRoles());


        // Mettre à jour l'utilisateur
        try {
            webappService.updateRegisteredUser(id, updateRegisteredUserFormDto);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès!");
        } catch (FeignException e) {
            // Gérer les erreurs liées à la mise à jour de l'utilisateur
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }

        model.addAttribute("updateRegisteredUserFormDto", updateRegisteredUserFormDto);

        System.out.println("77777777777777777777777777777777777777777777777777777777" + updateRegisteredUserFormDto);

        // Rediriger vers une page de confirmation ou une autre vue
        return "redirect:/reservations";
    }

    @GetMapping(value = "/workshop")
    public String getWorkshopPage(Model model) {

        logger.info("Reach url: /workshop - GET");

        List<WorkshopBean> workshopBean = apiProxy.getWorkshops();
        model.addAttribute("workshop", workshopBean);

        return "Workshop";
    }

    @GetMapping(value = "/addWorkshop")
    public String getAddWorkshopPage(Model model){

        logger.info("Reach url: /addWorkshop - GET");
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate);
        WorkshopBean workshopBean = new WorkshopBean();
        model.addAttribute("workshopBean", workshopBean);
        List<AircraftDto> aircraftDtoList = webappService.getAvailableAircrafts();
        model.addAttribute("aircraftAvailable", aircraftDtoList);

        return "AddWorkshop";
    }

    @PostMapping(value = "/addWorkshop")
    public String addWorkshop(@Valid WorkshopBean workshopBean, RedirectAttributes redirectAttributes){

        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&& objet workshop depuis *****WebappController*****" + workshopBean);
        logger.info("Reach url: /addWorkshop - POST");


        System.out.println("objet workshop depuis *****WebappController*****" + workshopBean);

        ResponseEntity<Void> response = null;
        int status = 0;

        try {
            workshopBean.setEntryDate(LocalDate.now());
            response = webappService.createWorkshop(workshopBean);
            status = response.getStatusCodeValue();
        } catch (FeignException e) {
            logger.debug(e.getMessage());
            logger.debug(e.getLocalizedMessage());
            status = e.status();
            e.printStackTrace();
        }

        if(status == 201){
            redirectAttributes.addAttribute("workshopStatus", "success");
        }
        return "redirect:/workshop";
    }

    @GetMapping("/intervention/{id}")
    public String interventionForm(Model model, @PathVariable int id){
        logger.info("Reach url: /interventionForm - GET");

        WorkshopBean workshopBean = webappService.getWorkshopBeanById(id);

        model.addAttribute("intervention", workshopBean);


        return "Intervention";
    }

    @PostMapping("/saveIntervention/{id}")
    public String saveIntervention(@PathVariable int id, @ModelAttribute("intervention") WorkshopBean workshopBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){


        if (result.hasErrors()) {
            // Si des erreurs de validation existent, retourner à la page de mise à jour avec les erreurs
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            System.out.println("il y a une ou des erreurs de validation");
            return "Intervention";
        }

        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate);

        workshopBean.setHelixChange(workshopBean.getHelixChange());
        workshopBean.setMotorChange(workshopBean.getMotorChange());
        workshopBean.setOther(workshopBean.getOther());
        workshopBean.setEntryDate(workshopBean.getEntryDate());
        workshopBean.setExitDate(currentDate);
        workshopBean.setAircraft(workshopBean.getAircraft());

        AircraftBean aircraftBean = webappService.getAircraftById(workshopBean.getAircraft().getId());
        aircraftBean.setAvailable(true);
        aircraftBean.setMotorHours(0);
        webappService.updateAircraft(aircraftBean.getId(), aircraftBean);
        System.out.println("L'avion que l'on veut rendre disponible est : " + aircraftBean);


        // Mettre à jour l'utilisateur
        try {
            webappService.saveIntervention(id, workshopBean);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès!");
        } catch (FeignException e) {
            // Gérer les erreurs liées à la mise à jour de l'utilisateur
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }

        model.addAttribute("intervention", workshopBean);

        return "redirect:/workshop";
    }


    @GetMapping("/updateWorkshop/{id}")
    public String updateWorkshopForm(Model model, @PathVariable int id){
        logger.info("Reach url: /updateWorkshopForm - GET");

        WorkshopBean workshopBean = webappService.getWorkshopBeanById(id);

        model.addAttribute("workshop", workshopBean);


        return "UpdateWorkshop";
    }

    @PostMapping("/updateWorkshop/{id}")
    public String updateWorkshopBean(@PathVariable int id, @ModelAttribute("workshop") WorkshopBean workshopBean, BindingResult result, RedirectAttributes redirectAttributes, Model model){

// Valider les données mises à jour
        if (result.hasErrors()) {
            // Si des erreurs de validation existent, retourner à la page de mise à jour avec les erreurs
            for (ObjectError error : result.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            System.out.println("il y a une ou des erreurs de validation");
            return "UpdateWorkshop";
        }

        workshopBean.setHelixChange(workshopBean.getHelixChange());
        workshopBean.setMotorChange(workshopBean.getMotorChange());
        workshopBean.setOther(workshopBean.getOther());
        workshopBean.setEntryDate(workshopBean.getEntryDate());
        workshopBean.setExitDate(workshopBean.getExitDate());
        workshopBean.setAircraft(workshopBean.getAircraft());

        // Mettre à jour l'utilisateur
        try {
            webappService.updateWorkshopBean(id, workshopBean);
            redirectAttributes.addFlashAttribute("successMessage", "Utilisateur mis à jour avec succès!");
        } catch (FeignException e) {
            // Gérer les erreurs liées à la mise à jour de l'utilisateur
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la mise à jour de l'utilisateur.");
        }

        model.addAttribute("workshop", workshopBean);

        // Rediriger vers une page de confirmation ou une autre vue
        return "redirect:/workshop";
    }



    @GetMapping(value="/disconnect")
    public String redirectLoginPageAfterLogout() {

        logger.info("Reach url: /disconnect - GET");

        return "redirect:/connexion?logout=true";
    }

}