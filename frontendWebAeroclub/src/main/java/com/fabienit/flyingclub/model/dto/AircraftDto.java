package com.fabienit.flyingclub.model.dto;

public class AircraftDto {

    private int id;
    private String mark;

    public AircraftDto() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "AircraftDto{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                '}';
    }
}
