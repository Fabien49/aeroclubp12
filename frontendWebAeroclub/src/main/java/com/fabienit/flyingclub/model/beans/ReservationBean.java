package com.fabienit.flyingclub.model.beans;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ReservationBean {

    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La date d'emprunt ne peut pas être nulle")
    private Date borrowingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "La date de retour ne peut pas être nulle")
    private Date returnDate;
    private RegisteredUserBean registeredUser;
    private AircraftBean aircraft;

    public ReservationBean() {
    }

    public ReservationBean(int id, Date borrowingDate, Date returnDate, RegisteredUserBean registeredUser, AircraftBean aircraft) {
        this.id = id;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.registeredUser = registeredUser;
        this.aircraft = aircraft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public AircraftBean getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftBean aircraft) {
        this.aircraft = aircraft;
    }

    public RegisteredUserBean getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUserBean registeredUser) {
        this.registeredUser = registeredUser;
    }

    @Override
    public String toString() {
        return "ReservationBean{" +
                "id=" + id +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                ", registeredUser=" + registeredUser +
                ", aircraft=" + aircraft +
                '}';
    }



}

