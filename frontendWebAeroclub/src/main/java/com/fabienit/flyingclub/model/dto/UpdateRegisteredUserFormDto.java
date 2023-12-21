package com.fabienit.flyingclub.model.dto;

public class UpdateRegisteredUserFormDto {

    private int id;

    private String lastName;

    private String firstName;

    private String email;

    private int hours;

    private String roles;

    public UpdateRegisteredUserFormDto() {
    }

    public UpdateRegisteredUserFormDto(int id, String lastName, String firstName, String email, int hours, String roles) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.hours = hours;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "UpdateRegisteredUserFormDto{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", hours=" + hours +
                ", roles='" + roles + '\'' +
                '}';
    }
}
