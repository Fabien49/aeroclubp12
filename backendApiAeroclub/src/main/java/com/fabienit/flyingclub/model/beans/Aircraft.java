package com.fabienit.flyingclub.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * Aircraft
 */
@Entity
@Table(name = "aircrafts")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mark")
    private String mark;


    @Column(name = "type")
    private String type;


    @Column(name = "motor")
    private String motor;


    @Column(name = "power")
    private String power;

    @Column(name = "seats")
    private int seats;

    @Column(name = "autonomy")
    private int autonomy;

    @Column(name = "use")
    private String use;

    @Column(name = "hours", nullable = false)
    private int hours;

    @Column(name = "is_available")
    private Boolean isAvailable;


    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Reservation> reservations;

    @JsonIgnore
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.REMOVE)
    private List<Revision> revisions;

    @JsonIgnore
    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL)
    private List<Workshop> workshops;

    public Aircraft() {
    }

    public Aircraft(int id) {
        this.id = id;
    }

    public Aircraft(int id, String mark, String type, String motor, String power, int seats, int autonomy, String use, int hours, Boolean isAvailable, List<Reservation> reservations, List<Revision> revisions, List<Workshop> workshops) {
        this.id = id;
        this.mark = mark;
        this.type = type;
        this.motor = motor;
        this.power = power;
        this.seats = seats;
        this.autonomy = autonomy;
        this.use = use;
        this.hours = hours;
        this.isAvailable = isAvailable;
        this.reservations = reservations;
        this.revisions = revisions;
        this.workshops = workshops;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", type='" + type + '\'' +
                ", motor='" + motor + '\'' +
                ", power='" + power + '\'' +
                ", seats=" + seats +
                ", autonomy=" + autonomy +
                ", use='" + use + '\'' +
                ", hours=" + hours +
                ", isAvailable=" + isAvailable +
                '}';
    }
}