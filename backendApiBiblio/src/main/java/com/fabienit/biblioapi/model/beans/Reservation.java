package com.fabienit.biblioapi.model.beans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "book_id"),
            @JoinColumn(name = "library_id")
    })
    private AvailableCopie availableCopie;

    @ManyToOne
    @JoinColumn(name = "registered_user_id")
    private RegisteredUser registereduser;

    @NotNull
    @Column(name = "notification_is_sent")
    private Boolean notificationIsSent;

    @Column(name = "avalaibility_date")
    private LocalDate availabilityDate;

    @NotNull
    private int position;


    public Reservation() {
    }

    public Reservation(int id, AvailableCopie availableCopie, RegisteredUser registereduser, Boolean notificationIsSent, LocalDate availabilityDate, int position) {
        this.id = id;
        this.availableCopie = availableCopie;
        this.registereduser = registereduser;
        this.notificationIsSent = notificationIsSent;
        this.availabilityDate = availabilityDate;
        this.position = position;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AvailableCopie getAvailableCopie() {
        return availableCopie;
    }

    public void setAvailableCopie(AvailableCopie availableCopie) {
        this.availableCopie = availableCopie;
    }

    public RegisteredUser getRegistereduser() {
        return registereduser;
    }

    public void setRegistereduser(RegisteredUser registereduser) {
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
        return "Reservation{" +
                "id=" + id +
                ", availableCopie=" + availableCopie +
                ", registereduser=" + registereduser +
                ", notificationIsSent=" + notificationIsSent +
                ", availabilityDate=" + availabilityDate +
                ", position=" + position +
                '}';
    }
}