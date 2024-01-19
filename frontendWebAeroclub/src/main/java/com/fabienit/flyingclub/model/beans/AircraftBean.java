package com.fabienit.flyingclub.model.beans;


/**
 * AircraftBean
 */
public class AircraftBean {

    private int id;
    private String mark;
    private String type;
    private String motor;
    private String power;
    private int seats;
    private int autonomy;
    private int aircraftHours;
    private int motorHours;
    private Boolean isAvailable;
    private String use;

    public AircraftBean() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(int autonomy) {
        this.autonomy = autonomy;
    }

    public int getAircraftHours() {
        return aircraftHours;
    }

    public void setAircraftHours(int aircraftHours) {
        this.aircraftHours = aircraftHours;
    }

    public int getMotorHours() {
        return motorHours;
    }

    public void setMotorHours(int motorHours) {
        this.motorHours = motorHours;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return "AircraftBean{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", type='" + type + '\'' +
                ", motor='" + motor + '\'' +
                ", power='" + power + '\'' +
                ", seats=" + seats +
                ", autonomy=" + autonomy +
                ", aircraftHours=" + aircraftHours +
                ", motorHours=" + motorHours +
                ", isAvailable=" + isAvailable +
                ", use='" + use + '\'' +
                '}';
    }
}