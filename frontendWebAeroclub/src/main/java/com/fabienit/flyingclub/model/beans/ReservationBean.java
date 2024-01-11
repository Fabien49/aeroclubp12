package com.fabienit.flyingclub.model.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class ReservationBean {

    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La date d'emprunt ne peut pas être nulle")
    private LocalDate borrowingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La date de retour ne peut pas être nulle")
    private LocalDate returnDate;

    private boolean finished;

    private boolean canceled;
    private RegisteredUserBean registeredUser;
    private AircraftBean aircraft;

    public ReservationBean() {
    }

    public ReservationBean(int id, LocalDate borrowingDate, LocalDate returnDate, boolean finished, boolean canceled, RegisteredUserBean registeredUser, AircraftBean aircraft) {
        this.id = id;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.finished = finished;
        this.canceled = canceled;
        this.registeredUser = registeredUser;
        this.aircraft = aircraft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public RegisteredUserBean getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUserBean registeredUser) {
        this.registeredUser = registeredUser;
    }

    public AircraftBean getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftBean aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public String toString() {
        return "ReservationBean{" +
                "id=" + id +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                ", finished=" + finished +
                ", canceled=" + canceled +
                ", registeredUser=" + registeredUser +
                ", aircraft=" + aircraft +
                '}';
    }
}

