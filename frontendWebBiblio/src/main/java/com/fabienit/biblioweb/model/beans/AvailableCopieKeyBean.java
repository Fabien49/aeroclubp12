package com.fabienit.biblioweb.model.beans;

/**
 * AvailableCopieKeyBean
 */
public class AvailableCopieKeyBean {

    private int bookId;
    private int libraryId;

    public AvailableCopieKeyBean() {
    }

    public AvailableCopieKeyBean(int bookId, int libraryId) {
        this.bookId = bookId;
        this.libraryId = libraryId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public String toString() {
        return "AvailableCopieKeyBean [bookId=" + bookId + ", libraryId=" + libraryId + "]";
    }
    
    
    

    
}