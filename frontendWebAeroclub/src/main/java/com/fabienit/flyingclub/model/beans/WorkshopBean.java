package com.fabienit.flyingclub.model.beans;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class WorkshopBean {

    private Integer id;

    private Boolean motorChange;

    private Boolean helixChange;

    private String other;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date exitDate;

    private AircraftBean aircraft;

    public WorkshopBean() {
    }

    public WorkshopBean(Integer id) {
        this.id = id;
    }

    public WorkshopBean(Integer id, Boolean motorChange, Boolean helixChange, String other, Date entryDate, Date exitDate, AircraftBean aircraft) {
        this.id = id;
        this.motorChange = motorChange;
        this.helixChange = helixChange;
        this.other = other;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.aircraft = aircraft;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getMotorChange() {
        return motorChange;
    }

    public void setMotorChange(Boolean motorChange) {
        this.motorChange = motorChange;
    }

    public Boolean getHelixChange() {
        return helixChange;
    }

    public void setHelixChange(Boolean helixChange) {
        this.helixChange = helixChange;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public AircraftBean getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftBean aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public String toString() {
        return "WorkshopBean{" +
                "id=" + id +
                ", motorChange=" + motorChange +
                ", helixChange=" + helixChange +
                ", other='" + other + '\'' +
                ", entryDate=" + entryDate +
                ", exitDate=" + exitDate +
                ", aircraft=" + aircraft +
                '}';
    }
}

