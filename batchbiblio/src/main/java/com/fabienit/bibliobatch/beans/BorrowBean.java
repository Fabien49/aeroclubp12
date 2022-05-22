package com.fabienit.bibliobatch.beans;

import java.time.LocalDate;

/**
 * BorrowBean
 */
public class BorrowBean {

    private int id;
    private BookBean book;
    private RegisteredUserBean registereduser;
    private LibraryBean library;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Boolean extendedDuration;
    private Boolean bookReturned;

    public BorrowBean() {
    }

    public BorrowBean(int id, BookBean book, RegisteredUserBean registereduser, LibraryBean library,
            LocalDate borrowDate, LocalDate returnDate, Boolean extendedDuration, Boolean bookReturned) {
        this.id = id;
        this.book = book;
        this.registereduser = registereduser;
        this.library = library;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.extendedDuration = extendedDuration;
        this.bookReturned = bookReturned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public RegisteredUserBean getRegistereduser() {
        return registereduser;
    }

    public void setRegistereduser(RegisteredUserBean registereduser) {
        this.registereduser = registereduser;
    }

    public LibraryBean getLibrary() {
        return library;
    }

    public void setLibrary(LibraryBean library) {
        this.library = library;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getExtendedDuration() {
        return extendedDuration;
    }

    public void setExtendedDuration(Boolean extendedDuration) {
        this.extendedDuration = extendedDuration;
    }

    public Boolean getBookReturned() {
        return bookReturned;
    }

    public void setBookReturned(Boolean bookReturned) {
        this.bookReturned = bookReturned;
    }

    @Override
    public String toString() {
        return "BorrowBean [book=" + book + ", bookReturned=" + bookReturned + ", borrowDate=" + borrowDate
                + ", extendedDuration=" + extendedDuration + ", id=" + id + ", library=" + library + ", registereduser="
                + registereduser + ", returnDate=" + returnDate + "]";
    }

    

    
    
}