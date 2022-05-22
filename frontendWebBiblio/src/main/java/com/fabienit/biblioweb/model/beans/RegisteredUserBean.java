package com.fabienit.biblioweb.model.beans;

import com.fabienit.biblioweb.validation.ValidEmail;

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
    private String roles;

    public RegisteredUserBean() {
    }

    public RegisteredUserBean(int id, String firstName, String lastName, String email, String password, String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "RegisteredUserBean [email=" + email + ", firstName=" + firstName + ", id=" + id + ", lastName="
                + lastName + ", password=" + password + ", roles=" + roles + "]";
    }

    
    
    
}