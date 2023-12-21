package com.fabienit.flyingclub.model.beans;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Rate
 */
@Entity
@Table(name = "rates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "landing_fee")
    private Double landingFee;

    @Column(name = "parking_tax")
    private Double parkingTax;

    @Column(name = "fuel")
    private Double fuel;

    public Rate() {

    }

    public Rate(Integer id, Double landingFee, Double parkingTax, Double fuel) {
        this.id = id;
        this.landingFee = landingFee;
        this.parkingTax = parkingTax;
        this.fuel = fuel;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLandingFee() {
        return landingFee;
    }

    public void setLandingFee(Double landingFee) {
        this.landingFee = landingFee;
    }

    public Double getParkingTax() {
        return parkingTax;
    }

    public void setParkingTax(Double parkingTax) {
        this.parkingTax = parkingTax;
    }

    public Double getFuel() {
        return fuel;
    }

    public void setFuel(Double fuel) {
        this.fuel = fuel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rate)) {
            return false;
        }
        return id != null && id.equals(((Rate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", landingFee=" + landingFee +
                ", parkingTax=" + parkingTax +
                ", fuel=" + fuel +
                '}';
    }
}
