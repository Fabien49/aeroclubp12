package com.fabienit.flyingclub.model.beans;

import java.time.LocalDate;

/**
 * AvailableCopieBean
 */
public class AvailableCopieBean {

    private AvailableCopieKeyBean id;
    private AircraftBean book;
    private LibraryBean library;
    private int ownedQuantity;
    private int availableQuantity;
    private Boolean bookCanBeReserved;
    private LocalDate nearestReturnDate;
    private int reservationCount;

    public AvailableCopieBean() {
    }

    public AvailableCopieKeyBean getId() {
        return id;
    }

    public void setId(AvailableCopieKeyBean id) {
        this.id = id;
    }

    public AircraftBean getBook() {
        return book;
    }

    public void setBook(AircraftBean book) {
        this.book = book;
    }

    public LibraryBean getLibrary() {
        return library;
    }

    public void setLibrary(LibraryBean library) {
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
        return "AvailableCopieBean{" +
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