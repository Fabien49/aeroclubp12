package com.fabienit.biblioapi.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * user
 */
@Entity
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String roles;

    @JsonIgnore
    @OneToMany(mappedBy = "registereduser", cascade = CascadeType.REMOVE)
    private Set<Borrow> borrows;

    @JsonIgnore
    @OneToMany(mappedBy = "registereduser", cascade = CascadeType.REMOVE)
    private Set<Reservation> reservations;

    public RegisteredUser() {
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
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
                ", roles='" + roles + '\'' +
                ", borrows=" + borrows +
                ", reservations=" + reservations +
                '}';
    }
}