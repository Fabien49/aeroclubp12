package com.fabienit.biblioweb.model.beans;

import java.time.LocalDate;

public class ReservationBean {


    private int id;
    private AvailableCopieBean availableCopie;
    private RegisteredUserBean registereduser;
    private Boolean notificationIsSent;
    private LocalDate availabilityDate;
    private int position;

    public ReservationBean() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AvailableCopieBean getAvailableCopie() {
        return availableCopie;
    }

    public void setAvailableCopie(AvailableCopieBean availableCopie) {
        this.availableCopie = availableCopie;
    }

    public RegisteredUserBean getRegistereduser() {
        return registereduser;
    }

    public void setRegistereduser(RegisteredUserBean registereduser) {
        this.registereduser = registereduser;
    }

    public Boolean getNotificationIsSent() {
        return notificationIsSent;
    }

    public void setNotificationIsSent(Boolean notificationIsSent) {
        this.notificationIsSent = notificationIsSent;
    }

    public LocalDate getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ReservationBean{" +
                "id=" + id +
                ", availableCopie=" + availableCopie +
                ", registereduser=" + registereduser +
                ", notificationIsSent=" + notificationIsSent +
                ", availabilityDate=" + availabilityDate +
                ", position=" + position +
                '}';
    }
}
