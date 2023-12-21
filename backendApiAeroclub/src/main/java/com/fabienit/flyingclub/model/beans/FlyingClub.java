package com.fabienit.flyingclub.model.beans;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * FlyingClub
 */
@Entity
@Table (name = "flying_club")
public class FlyingClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "The oaci must be filled in.")
    private String oaci;

    @NotEmpty(message = "The name must be filled in.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "The phoneNumber must be filled in.")
    @Column(name = "phonenumber")
    private String phoneNumber;

    @NotNull
    @Size(max = 80)
    @Column(name = "mail", length = 80, nullable = false, unique = true)
    private String mail;

    @NotNull
    @Size(max = 150)
    @Column(name = "address", length = 150, nullable = false)
    private String address;

    @NotNull
    @Size(max = 80)
    @Column(name = "postalcode", length = 80, nullable = false)
    private String postalCode;

    @NotNull
    @Size(max = 80)
    @Column(name = "city", length = 80, nullable = false)
    private String city;

    public FlyingClub() {
    }

    public FlyingClub(int id, String oaci, String name, String phoneNumber, String mail, String address, String postalCode, String city) {
        this.id = id;
        this.oaci = oaci;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOaci() {
        return oaci;
    }

    public void setOaci(String oaci) {
        this.oaci = oaci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    @Override
    public String toString() {
        return "FlyingClub{" +
                "id=" + id +
                ", oaci='" + oaci + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", adress='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}