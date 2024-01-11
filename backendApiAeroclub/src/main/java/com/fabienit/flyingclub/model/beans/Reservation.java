package com.fabienit.flyingclub.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @DateTimeFormat(pattern = "dd-MMMM-yy")
    @Column(name = "borrowing_date")
    private LocalDate borrowingDate;

    @DateTimeFormat(pattern = "dd-MMMM-yy")
    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "finished")
    private boolean finished;

    @Column(name = "canceled")
    private boolean canceled;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @JoinColumn(name = "registeredUser_id")
    private RegisteredUser registeredUser;

    public Reservation() {
    }

    public Reservation(int id, LocalDate borrowingDate, LocalDate returnDate, boolean finished, boolean canceled, Aircraft aircraft, RegisteredUser registeredUser) {
        this.id = id;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
        this.finished = finished;
        this.canceled = canceled;
        this.aircraft = aircraft;
        this.registeredUser = registeredUser;
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

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return Objects.equals(id, ((Reservation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                ", finished=" + finished +
                ", canceled=" + canceled +
                ", aircraft=" + aircraft +
                ", registeredUser=" + registeredUser +
                '}';
    }
}
