package com.fabienit.flyingclub.model.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A Revision.
 */
@Entity
@Table(name = "revision")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "levels")
    private Boolean levels;

    @Column(name = "pressure")
    private Boolean pressure;

    @Column(name = "bodywork")
    private Boolean bodywork;

    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Aircraft aircraft;

    public Revision() {
    }

    public Revision(Integer id, Boolean levels, Boolean pressure, Boolean bodywork, Date date, Aircraft aircraft) {
        this.id = id;
        this.levels = levels;
        this.pressure = pressure;
        this.bodywork = bodywork;
        this.date = date;
        this.aircraft = aircraft;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLevels() {
        return levels;
    }

    public void setLevels(Boolean levels) {
        this.levels = levels;
    }

    public Boolean getPressure() {
        return pressure;
    }

    public void setPressure(Boolean pressure) {
        this.pressure = pressure;
    }

    public Boolean getBodywork() {
        return bodywork;
    }

    public void setBodywork(Boolean bodywork) {
        this.bodywork = bodywork;
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
        if (!(o instanceof Revision)) {
            return false;
        }
        return id != null && id.equals(((Revision) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "Revision{" +
                "id=" + id +
                ", levels=" + levels +
                ", pressure=" + pressure +
                ", bodywork=" + bodywork +
                ", date=" + date +
                ", aircraft=" + aircraft +
                '}';
    }
}
