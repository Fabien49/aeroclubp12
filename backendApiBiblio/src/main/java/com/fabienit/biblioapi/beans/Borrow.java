package com.fabienit.biblioapi.beans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Borrow
 */
@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "registered_user_id")
    private RegisteredUser registereduser;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;    

    @NotNull
    private LocalDate borrowDate;
    
    @NotNull
    private LocalDate returnDate;
    @NotNull
    private Boolean extendedDuration;
    @NotNull
    private Boolean bookReturned;

    public Borrow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public RegisteredUser getRegistereduser() {
        return registereduser;
    }

    public void setRegistereduser(RegisteredUser registereduser) {
        this.registereduser = registereduser;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
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
        return "Borrow [book=" + book + ", bookReturned=" + bookReturned + ", borrowDate=" + borrowDate
                + ", extendedDuration=" + extendedDuration + ", id=" + id + ", library=" + library + ", registereduser="
                + registereduser + ", returnDate=" + returnDate + "]";
    }

    
    
}