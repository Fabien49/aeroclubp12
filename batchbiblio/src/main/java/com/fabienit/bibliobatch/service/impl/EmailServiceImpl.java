package com.fabienit.bibliobatch.service.impl;

import com.fabienit.bibliobatch.beans.BorrowBean;
import com.fabienit.bibliobatch.beans.ReservationBean;
import com.fabienit.bibliobatch.proxies.ApiProxy;
import com.fabienit.bibliobatch.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * EmailServiceImpl
 */
@Service
public class EmailServiceImpl implements EmailService {

   @Autowired
    JavaMailSender emailSender;

    @Autowired
            ApiProxy apiProxy;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *  Send email to users which have late borrows every day
     */
    @Scheduled(cron = "${batch.time.event.lateborrows}")
    public void sendMailForLateBorrowsEveryDay() {

        logger.info("Sending emails for late borrows");

        // Get borrows list
        List<BorrowBean> borrows = apiProxy.getBorrows();

        // Init today date
        LocalDate today = LocalDate.now();

        for (BorrowBean borrowBean : borrows) {

            // Filter active and late borrows
            if (borrowBean.getReturnDate().isBefore(today) && !borrowBean.getBookReturned()) {

                // Generate mail for current borrow
                HashMap<String, String> mailData = generateLateBorrowsEmail(borrowBean);

                // Send mail for current borrow
                sendSimpleMessage(mailData.get("to"), mailData.get("subject"), mailData.get("text"));
            }
        }
    }



    /**
     * Launch reservation daily task
     */
    @Scheduled(cron = "${batch.time.event.reservationsnotifications}")
    public void launchingReservationDailyTask() {
        // Delete outdated reservations
        apiProxy.deleteOutDatedReservations();
        // send reservations notifications
        sendMailReservationNotification();
    }


    /**
     *  Send email to users which have ready reservation
     */
    public void sendMailReservationNotification() {

        logger.info("Sending notification emails for available reservations");

        // Init today date
        LocalDate today = LocalDate.now();

        // Get reservation list
        List<ReservationBean> reservations = apiProxy.getReservations();

        for (ReservationBean reservationBean : reservations) {

            // Filter reservations by position(1) and sent notification status (false)
            if (reservationBean.getPosition() == 1 && !reservationBean.getNotificationIsSent()) {

                // Generate mail for current reservation
                HashMap<String, String> mailData = generateReservationNotificationEmail(reservationBean);

                // Send mail for current borrow
                sendSimpleMessage(mailData.get("to"), mailData.get("subject"), mailData.get("text"));

                // Update reservation database
                apiProxy.updateReservationAfterNotification(reservationBean);
            }
        }
    }

    /**
     * Generate mail data according to borrow data, return "to", "subject" and
     * "text" parameters
     * @param lateBorrow
     */
    private HashMap<String, String> generateLateBorrowsEmail(BorrowBean lateBorrow) {

        logger.debug("Generating mail data for late borrow, id:" + lateBorrow.getId());
        // Init new line
        String newLine = System.getProperty("line.separator");
        // Init HashMap witch will contains mail data
        HashMap<String, String> mailData = new HashMap<>();
        // Extract data from borrow bean
        String title = lateBorrow.getBook().getTitle();
        String library = lateBorrow.getLibrary().getName();
        LocalDate returnDate = lateBorrow.getReturnDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String returnDateFormatter = returnDate.format(formatter);
        // Build "to" parameter
        String to = lateBorrow.getRegistereduser().getEmail();
        // Build "subject" parameter
        String subject = "Date de retour dépassée du livre " + title;
        // Build "text" parameter
        String text = "Bonjour," + newLine  + newLine + "L'emprunt du livre \"" + title + "\""
                + " a dépassé sa date d'échéance qui était le " + returnDateFormatter + "." + newLine+ "Veuillez nous ramener le livre à la bibliothèque " + library
                + " dans les plus brefs délais." + newLine + newLine + "Cordialement." + newLine + newLine + "Les Bibliothèques de la ville d'Angers.";
        // Fill Hashmap with data
        mailData.put("to", to);
        mailData.put("subject", subject);
        mailData.put("text", text);

        return mailData;

    }

    /**
     * Generate mail data according to reservation data, return "to", "subject" and
     * "text" parameters
     * @param reservationBean
     */
    public HashMap<String, String> generateReservationNotificationEmail(ReservationBean reservationBean) {

        logger.debug("Generating mail data for reservation, id:" + reservationBean.getId());
        // Init new line
        String newLine = System.getProperty("line.separator");
        // Init HashMap witch will contains mail data
        HashMap<String, String> mailData = new HashMap<>();
        // Extract data from borrow bean
        String title = reservationBean.getAvailableCopie().getBook().getTitle();
        String library = reservationBean.getAvailableCopie().getLibrary().getName();
        // Build "to" parameter
        String to = reservationBean.getRegistereduser().getEmail();
        // Build "subject" parameter
        String subject = "Réservation du livre: " + title;
        // Build "text" parameter
        String text = "Le livre \"" + title + "\""
                + " que vous avez réservé est disponible à la bibliothèque de " + library
                + " vous disposez de 48h pour venir le récupérer, au delà la réservation sera annulée." + newLine + "Cordialement." + newLine + "OC-Bibliothèque.";
        // Fill Hashmap with data
        mailData.put("to", to);
        mailData.put("subject", subject);
        mailData.put("text", text);

        return mailData;

    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }

    }

}