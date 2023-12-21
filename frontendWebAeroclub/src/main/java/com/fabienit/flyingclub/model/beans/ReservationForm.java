package com.fabienit.flyingclub.model.beans;

import java.util.Date;

public class ReservationForm {

    private Integer id;
    private Date borrowingDate;
    private Date returnDate;
    private String mark;

    public ReservationForm() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "ReservationForm{" +
                "id=" + id +
                ", borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                ", mark='" + mark + '\'' +
                '}';
    }
}

