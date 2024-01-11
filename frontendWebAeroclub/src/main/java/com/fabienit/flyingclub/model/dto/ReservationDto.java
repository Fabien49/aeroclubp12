package com.fabienit.flyingclub.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class ReservationDto {

    @NotNull(message = "La date d'emprunt ne peut pas être nulle")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private LocalDate borrowingDate;
    @NotNull(message = "La date de retour ne peut pas être nulle")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;

    private AircraftDto aircraftDto;

    private int registeredUserId;

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public AircraftDto getAircraftDto() {
        return aircraftDto;
    }

    public void setAircraftDto(AircraftDto aircraftDto) {
        this.aircraftDto = aircraftDto;
    }

    public int getRegisteredUserId() {
        return registeredUserId;
    }

    public void setRegisteredUserId(int registeredUserId) {
        this.registeredUserId = registeredUserId;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "borrowingDate=" + borrowingDate +
                ", returnDate=" + returnDate +
                ", aircraftDto=" + aircraftDto +
                ", registeredUserId=" + registeredUserId +
                '}';
    }
}
