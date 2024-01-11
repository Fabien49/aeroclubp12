package com.fabienit.flyingclub.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CanceledReservationDto {


    private boolean canceled;

    public CanceledReservationDto() {
    }

    public CanceledReservationDto(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public String toString() {
        return "CanceledReservationDto{" +
                "canceled=" + canceled +
                '}';
    }
}
