package com.fabienit.flyingclub.model.beans;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Workshop
 */
@Entity
@Table(name = "workshop")
/*@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)*/
public class Workshop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "motor_change")
    private Boolean motorChange;

    @Column(name = "helix_change")
    private Boolean helixChange;

    @Column(name = "other")
    private String other;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @Column(name = "exit_date")
    private LocalDate exitDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    public Workshop() {
    }

    public Workshop(int id, Boolean motorChange, Boolean helixChange, String other, LocalDate entryDate, LocalDate exitDate, Aircraft aircraft) {
        this.id = id;
        this.motorChange = motorChange;
        this.helixChange = helixChange;
        this.other = other;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.aircraft = aircraft;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workshop)) {
            return false;
        }
        return Objects.equals(id, ((Workshop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }


    @Override
    public String toString() {
        return "Workshop{" +
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
