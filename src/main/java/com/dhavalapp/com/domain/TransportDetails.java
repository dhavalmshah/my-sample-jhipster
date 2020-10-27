package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TransportDetails.
 */
@Entity
@Table(name = "transport_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransportDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "transport_number", nullable = false)
    private String transportNumber;

    @Column(name = "transport_start_date")
    private Instant transportStartDate;

    @Column(name = "estimated_end_date")
    private Instant estimatedEndDate;

    @Column(name = "actual_end_date")
    private Instant actualEndDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "transportDetails", allowSetters = true)
    private CounterParty transporter;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransportNumber() {
        return transportNumber;
    }

    public TransportDetails transportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
        return this;
    }

    public void setTransportNumber(String transportNumber) {
        this.transportNumber = transportNumber;
    }

    public Instant getTransportStartDate() {
        return transportStartDate;
    }

    public TransportDetails transportStartDate(Instant transportStartDate) {
        this.transportStartDate = transportStartDate;
        return this;
    }

    public void setTransportStartDate(Instant transportStartDate) {
        this.transportStartDate = transportStartDate;
    }

    public Instant getEstimatedEndDate() {
        return estimatedEndDate;
    }

    public TransportDetails estimatedEndDate(Instant estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
        return this;
    }

    public void setEstimatedEndDate(Instant estimatedEndDate) {
        this.estimatedEndDate = estimatedEndDate;
    }

    public Instant getActualEndDate() {
        return actualEndDate;
    }

    public TransportDetails actualEndDate(Instant actualEndDate) {
        this.actualEndDate = actualEndDate;
        return this;
    }

    public void setActualEndDate(Instant actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public CounterParty getTransporter() {
        return transporter;
    }

    public TransportDetails transporter(CounterParty counterParty) {
        this.transporter = counterParty;
        return this;
    }

    public void setTransporter(CounterParty counterParty) {
        this.transporter = counterParty;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransportDetails)) {
            return false;
        }
        return id != null && id.equals(((TransportDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransportDetails{" +
            "id=" + getId() +
            ", transportNumber='" + getTransportNumber() + "'" +
            ", transportStartDate='" + getTransportStartDate() + "'" +
            ", estimatedEndDate='" + getEstimatedEndDate() + "'" +
            ", actualEndDate='" + getActualEndDate() + "'" +
            "}";
    }
}
