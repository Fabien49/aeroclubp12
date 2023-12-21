package com.fabienit.flyingclub.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * user
 */
@Entity
@Table(name = "registeredUser")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@NotEmpty
    @Column(name = "first_name")
    private String firstName;

    //@NotEmpty
    @Column(name = "last_name")
    private String lastName;

    //@NotEmpty
    @Column(unique = true)
    private String email;

    //@NotEmpty
    private String password;

    @Column(name = "hours")
    private int hours;

    //@NotEmpty
    private String roles;

    @JsonIgnore
    @OneToMany(mappedBy = "registeredUser", cascade = CascadeType.ALL)
    private List<Reservation> reservations;


    public RegisteredUser() {
    }

    public RegisteredUser(int id, String firstName, String lastName, String email, String password, int hours, String roles, List<Reservation> reservations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.hours = hours;
        this.roles = roles;
        this.reservations = reservations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hours='" + hours + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}