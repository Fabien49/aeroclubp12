package com.fabienit.biblioweb.model.dto;

import com.fabienit.biblioweb.validation.PasswordMatches;
import com.fabienit.biblioweb.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * RegisteredUserBean
 */
@PasswordMatches
public class RegisteredUserDto {

    @NotNull
    @NotEmpty(message = "Saisir un prénom.")
    @Size(min=3, message="Le prénom doit contenir au minimum 3 caractères.")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Saisir un nom.")
    @Size(min = 3, message = "Le nom doit contenir au minimum 3 caractères.")
    private String lastName;

    @ValidEmail
    @NotNull
    @NotEmpty(message = "Saisir un email.")
    private String email;

    @NotNull
    @NotEmpty(message = "Saisir le mot de passe.")
    @Size(min = 6, message = "Le mot de passse doit contenir au minimum 6 caractères.")
    private String password;

    @NotNull
    @NotEmpty(message = "Saisir la confirmation du mot de passe.")
    @Size(min = 6, message = "La confirmation du mot de passse doit contenir au minimum 6 caractères.")
    private String matchingPassword;

    public RegisteredUserDto() {
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

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    @Override
    public String toString() {
        return "RegisteredUserDto [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
                + ", matchingPassword=" + matchingPassword + ", password=" + password + "]";
    }

}