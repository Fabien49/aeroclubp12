package com.fabienit.biblioweb.web.controllers;

import com.fabienit.biblioweb.model.beans.AvailableCopieBean;
import com.fabienit.biblioweb.model.beans.BookBean;
import com.fabienit.biblioweb.model.beans.BorrowBean;
import com.fabienit.biblioweb.model.beans.ReservationBean;
import com.fabienit.biblioweb.model.dto.RegisteredUserDto;
import com.fabienit.biblioweb.service.WebappService;
import com.fabienit.biblioweb.web.proxies.ApiProxy;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping(value = "/books")
    public String getBooksPage(Model model, @RequestParam(required = false) String query, @RequestParam(required = false) String reservationStatus) {

        logger.info("Reach url: / - GET");

        if (query == null)
            query = "";

        List<BookBean> books = apiProxy.getBooks(query);
        List<AvailableCopieBean> availableCopies = webappService.getAvailableCopies();

        model.addAttribute("books", books);
        model.addAttribute("availableCopies", availableCopies);
        model.addAttribute("reservationStatus", reservationStatus);

        Boolean isAuthenticated = webappService.getIsAuthenticated();

        if (isAuthenticated){
            int authenticatedUserId = webappService.getAuthenticatedRegisteredUserId();

            List<Integer> bookIdReservationList = webappService.getBookIdReservationList(authenticatedUserId);
            model.addAttribute("userReservationList", bookIdReservationList );

            List<Integer> bookIdActiveBorrowList = webappService.getBookIdActiveBorrowList();
            model.addAttribute("userActiveBorrowList", bookIdActiveBorrowList);
        }

        return "Books";
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
    public String getProfilePage(Model model, @RequestParam(required = false) Boolean deletedReservation) {

        logger.info("Reach url: /profile - GET");

        // Get active borrows list for current user
        List<BorrowBean> currentUserActiveBorrows = webappService.getActiveBorrowsByRegisteredUserId();

        // Get reservations list for current user
        List<ReservationBean> currentUserReservations = webappService.getReservationsByRegisteredUserId();

        model.addAttribute("currentUserActiveBorrows", currentUserActiveBorrows);
        model.addAttribute("currentUserReservations", currentUserReservations);
        model.addAttribute("deletedReservation", deletedReservation);

        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);


        return "Profile";
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



    @PostMapping(value = "/reserve")
    public String reserveAvailableCopie(@RequestParam int bookId, @RequestParam int libraryId, RedirectAttributes redirectAttributes){

        logger.info("Reach url: /reserve - POST");

        ReservationBean reservation = webappService.createReservation(bookId, libraryId);

        ResponseEntity<Void> response = null;
        int status = 0;

        try {
            response = apiProxy.addReservation(reservation);
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
        return "redirect:/profile";
    }

    /**
     *
     */
    @PostMapping(value = "/reservation/delete")
    public String deleteReservation(@RequestParam int reservationId, RedirectAttributes redirectAttributes) {

        apiProxy.deleteReservation(reservationId);
        redirectAttributes.addAttribute("deletedReservation", true);
        return "redirect:/profile";

    }

    @GetMapping(value="/disconnect")
    public String redirectLoginPageAfterLogout() {

        logger.info("Reach url: /disconnect - GET");

        return "redirect:/connexion?logout=true";
    }

}