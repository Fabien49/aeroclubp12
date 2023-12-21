package com.fabienit.flyingclub.model.beans;

import com.fabienit.flyingclub.validation.ValidEmail;

/**
 * RegisteredUserBean
 */
public class RegisteredUserBean {

    private int id;
    private String firstName;
    private String lastName;
    @ValidEmail
    private String email;
    private String password;
    private int hours;
    private String roles;

    public RegisteredUserBean() {
    }

    public RegisteredUserBean(int id, String firstName, String lastName, String email, String password, int hours, String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.hours = hours;
        this.roles = roles;
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

    @Override
    public String toString() {
        return "RegisteredUserBean{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", hours=" + hours +
                ", roles='" + roles + '\'' +
                '}';
    }
}