package com.fabienit.flyingclub.model.beans;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Workshop
 */
@Entity
@Table(name = "workshop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Workshop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "motor_change")
    private Boolean motorChange;

    @Column(name = "helix_change")
    private Boolean helixChange;

    @Column(name = "other")
    private String other;

    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    private Aircraft aircraft;

    public Workshop() {
    }

    public Workshop(Integer id, Boolean motorChange, Boolean helixChange, String other, Date date, Aircraft aircraft) {
        this.id = id;
        this.motorChange = motorChange;
        this.helixChange = helixChange;
        this.other = other;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return id != null && id.equals(((Workshop) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "Workshop{" +
                "id=" + id +
                ", motorChange=" + motorChange +
                ", helixChange=" + helixChange +
                ", other='" + other + '\'' +
                ", date=" + date +
                ", aircraft=" + aircraft +
                '}';
    }
}
