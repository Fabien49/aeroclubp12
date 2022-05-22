package com.fabienit.biblioapi.model.beans;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * AvailableCopie
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"book_id", "library_id"})})
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

    @NotNull
    private Boolean bookCanBeReserved;


    private LocalDate nearestReturnDate;

    @NotNull
    private int reservationCount;

    public AvailableCopie() {
    }

    public AvailableCopie(@NotNull @Valid AvailableCopieKey id, Book book, Library library, @NotNull int ownedQuantity, @NotNull int availableQuantity, @NotNull Boolean bookCanBeReserved, @NotNull LocalDate nearestReturnDate, @NotNull int reservationCount) {
        this.id = id;
        this.book = book;
        this.library = library;
        this.ownedQuantity = ownedQuantity;
        this.availableQuantity = availableQuantity;
        this.bookCanBeReserved = bookCanBeReserved;
        this.nearestReturnDate = nearestReturnDate;
        this.reservationCount = reservationCount;
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

    public Boolean getBookCanBeReserved() {
        return bookCanBeReserved;
    }

    public void setBookCanBeReserved(Boolean bookCanBeReserved) {
        this.bookCanBeReserved = bookCanBeReserved;
    }

    public LocalDate getNearestReturnDate() {
        return nearestReturnDate;
    }

    public void setNearestReturnDate(LocalDate nearestReturnDate) {
        this.nearestReturnDate = nearestReturnDate;
    }

    public int getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(int reservationCount) {
        this.reservationCount = reservationCount;
    }

    @Override
    public String toString() {
        return "AvailableCopie{" +
                "id=" + id +
                ", book=" + book +
                ", library=" + library +
                ", ownedQuantity=" + ownedQuantity +
                ", availableQuantity=" + availableQuantity +
                ", bookCanBeReserved=" + bookCanBeReserved +
                ", nearestReturnDate=" + nearestReturnDate +
                ", reservationCount=" + reservationCount +
                '}';
    }
}