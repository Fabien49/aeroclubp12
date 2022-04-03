package com.fabienit.biblioweb.model.beans;

/**
 * AvailableCopieBean
 */
public class AvailableCopieBean {

    private AvailableCopieKeyBean id;
    private BookBean book;
    private LibraryBean library;
    private int ownedQuantity;
    private int availableQuantity;

    public AvailableCopieBean() {
    }

    public AvailableCopieKeyBean getId() {
        return id;
    }

    public void setId(AvailableCopieKeyBean id) {
        this.id = id;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
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

    @Override
    public String toString() {
        return "AvailableCopieBean [availableQuantity=" + availableQuantity + ", book=" + book + ", id=" + id
                + ", library=" + library + ", ownedQuantity=" + ownedQuantity + "]";
    }
    
    
}