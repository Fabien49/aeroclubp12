package com.fabienit.flyingclub.web.controllers;

import com.fabienit.flyingclub.model.dto.RegisteredUserDto;
import com.fabienit.flyingclub.model.dto.ReservationDto;
import com.fabienit.flyingclub.service.WebappService;
import com.fabienit.flyingclub.web.proxies.ApiProxy;
import com.fabienit.flyingclub.model.beans.*;
import com.fabienit.flyingclub.model.dto.AircraftDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * WebappController
 */
@Controller
public class WebappController {

    @Autowired
    private ApiProxy apiProxy;

    @Autowired
    private WebappService webappService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        updateAircraftFormDto.setHours(updateAircraftFormDto.getHours());
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
            System.out.println("*************Liste des reservations de l'utilisateur qui a pour id : " + id + "******" + currentUserReservations);

            model.addAttribute("currentUserReservations", currentUserReservations);

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setRegisteredUserId(id);
            model.addAttribute("reservationDto", reservationDto);

            System.out.println("/////////////********************------------- reservation avec l'utisateur connecte : " + reservationDto);


        }

        return "Reservations";
    }

    @GetMapping(value = "/addReservation")
    public String getAddReservationPage(Model model) {

        logger.info("Reach url: /reservations - GET");

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated) {
            int id = webappService.getAuthenticatedRegisteredUserId();
            RegisteredUserBean registeredUserBean = new RegisteredUserBean();
            registeredUserBean.setId(id);


/*            // Get available aircrafts
            List<AircraftDto> availableAircrafts = webappService.getAvailableAircrafts();
            model.addAttribute("availableAircrafts", availableAircrafts);


            // Get reservations list for current user
            List<ReservationBean> currentUserReservations = webappService.getReservationsByRegisteredUserId();
            System.out.println("*************Liste des reservations de l'utilisateur qui a pour id : " + id + "******" + currentUserReservations);

            model.addAttribute("currentUserReservations", currentUserReservations);*/

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setRegisteredUserId(id);
            model.addAttribute("reservationDto", reservationDto);

            System.out.println("/////////////********************------------- reservation avec l'utisateur connecte : " + reservationDto);


        }

        return "AddReservation";
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



    private List<AircraftDto> filterAvailableAircrafts(List<AircraftDto> allAircrafts, List<ReservationDto> reservations) {
        // Filtrer les avions disponibles en fonction des réservations pour les dates spécifiées
        List<AircraftDto> reservedAircraftIds = reservations.stream()
                .map(ReservationDto::getAircraftDto)
                .collect(Collectors.toList());

        List<AircraftDto> availableAircrafts = allAircrafts.stream()
                .filter(aircraft -> !reservedAircraftIds.contains(aircraft))
                .collect(Collectors.toList());

        return availableAircrafts;
    }

/*    @GetMapping("/updateAircrafts/{borrowingDate}")
    @ResponseBody
    public List<AircraftBean> updateAircraftList(@PathVariable String borrowingDate,
                                                @RequestParam(required = false) String returnDate) {

        try {
            // Votre logique pour obtenir la liste d'avions disponibles en fonction des dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedBorrowingDate = dateFormat.parse(borrowingDate);
            Date parsedReturnDate = (returnDate != null) ? dateFormat.parse(returnDate) : null;

            List<AircraftBean> availableAircrafts = apiProxy.getAvailableAircraftsBetweenDates(parsedBorrowingDate, returnDate);

            return availableAircrafts;
        } catch (DateTimeParseException e) {
            // Gérer l'erreur de format de date si nécessaire
            e.printStackTrace();
            return Collections.emptyList(); // Ou retourner une liste vide ou une erreur appropriée
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }*/



/*    @GetMapping(value = "/reservation")
    public String reservation(Model model){

        // Get available aircrafts
*//*        List<AircraftBean> availableAircrafs = webappService.getAvailableAircrafts();
        model.addAttribute("availableAircrafts", availableAircrafs);*//*


        model.addAttribute("reservationDto",  new ReservationDto());

        return "saveReservation";
    }*/

/*    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute("reservationDto") @RequestBody ReservationDto reservationDto, Model model) {
        model.addAttribute("reservationDto", reservationDto);
        System.out.println("=================================== c'est ici que ça se passe : " + reservationDto);
        return "/Home";
    }*/


    @PostMapping(value = "/saveReservation")
    public String saveReservation(@ModelAttribute ("reservationDto") @Valid ReservationDto reservationDto, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        System.out.println("test : " + reservationDto.toString());

        if (bindingResult.hasErrors()) {

            model.addAttribute("reservationDto", reservationDto);

            return "redirect:/reservations";
        }


        System.out.println("objet reservation depuis *****WebappController*****" + reservationDto);

        logger.info("Reach url: /reservations - POST");



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
    @RequestMapping(value = "/reservations/{id}")
    public String deleteReservation(@PathVariable int id, RedirectAttributes redirectAttributes) {

        apiProxy.deleteReservation(id);
        redirectAttributes.addAttribute("deletedReservation", true);
        return "redirect:/reservations";
    }


    @GetMapping(value = "/addHours")
    public String getAddHoursPage(Model model){

        logger.info("Reach url: /addHours - GET");
        WorkshopBean workshopBean = new WorkshopBean();
        model.addAttribute("workshopBean", workshopBean);
        List<AircraftDto> aircraftDtoList = webappService.getAvailableAircrafts();
        model.addAttribute("aircraftAvailable", aircraftDtoList);

        return "AddHours";
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

/*    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "Greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("greeting", greeting);
        return "result";
    }

    @GetMapping("/student")
    public String student(Model model) {
        model.addAttribute("student", new Student());
        return "Student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student, Model model) {
        model.addAttribute("student", student);
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&la date d'anniversaire de l'étudiant est : " + student);
        return "displayDate";
    }*/

}