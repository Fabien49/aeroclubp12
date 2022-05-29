package com.fabienit.biblioapi.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Library
 */
@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @NotEmpty(message = "Le nom de la bibliothèque doit être renseigné.")
    @Column(unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "library", cascade = CascadeType.REMOVE)
    private Set<Borrow> borrows;

    @JsonIgnore
    @OneToMany(mappedBy = "library", cascade = CascadeType.REMOVE)
    private Set<AvailableCopie> availableCopies;

    public Library() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    public Set<AvailableCopie> getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Set<AvailableCopie> availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return "Library [availableCopies=" + availableCopies + ", borrows=" + borrows + ", id=" + id + ", name=" + name
                + "]";
    }

}