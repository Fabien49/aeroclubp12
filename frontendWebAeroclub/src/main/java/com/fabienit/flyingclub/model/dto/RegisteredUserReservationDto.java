package com.fabienit.flyingclub.model.dto;

public class RegisteredUserReservationDto {

        private int id;

    public RegisteredUserReservationDto() {
    }

    public RegisteredUserReservationDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RegisteredUserReservationDto{" +
                "id=" + id +
                '}';
    }
}
