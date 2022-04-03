package com.fabienit.biblioapi.beans;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * AvailableCopie
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"book_id","library_id"})})
public class AvailableCopie {

    @EmbeddedId
    @NotNull
    @Valid
    private AvailableCopieKey id;

     
     @ManyToOne
     @MapsId("book_id")
     @JoinColumn(name = "book_id")
     private Book book;

     
     @ManyToOne
     @MapsId("library_id")
     @JoinColumn(name = "library_id")
     private Library library;

     @NotNull
     private int ownedQuantity;

     @NotNull
     private int availableQuantity;

    public AvailableCopie() {
    }
    

    public AvailableCopieKey getId() {
        return id;
    }

    public void setId(AvailableCopieKey id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public int getOwnedQuantity() {
        return ownedQuantity;
    }

    public void setOwnedQuantity(int ownedQuantity) {
        this.ownedQuantity = ownedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public String toString() {
        return "AvailableCopie [availableQuantity=" + availableQuantity + ", book=" + book + ", id=" + id + ", library="
                + library + ", ownedQuantity=" + ownedQuantity + "]";
    }

   

    


}