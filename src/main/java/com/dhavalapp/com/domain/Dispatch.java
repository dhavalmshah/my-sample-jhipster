package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dhavalapp.com.domain.enumeration.GoodsDispatchStatus;

/**
 * A Dispatch.
 */
@Entity
@Table(name = "dispatch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dispatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GoodsDispatchStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private StockTransaction transaction;

    @ManyToOne
    @JsonIgnoreProperties(value = "dispatches", allowSetters = true)
    private TransportDetails transporter;

    @ManyToOne
    @JsonIgnoreProperties(value = "dispatches", allowSetters = true)
    private TransportDetails transporter2;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoodsDispatchStatus getStatus() {
        return status;
    }

    public Dispatch status(GoodsDispatchStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GoodsDispatchStatus status) {
        this.status = status;
    }

    public StockTransaction getTransaction() {
        return transaction;
    }

    public Dispatch transaction(StockTransaction stockTransaction) {
        this.transaction = stockTransaction;
        return this;
    }

    public void setTransaction(StockTransaction stockTransaction) {
        this.transaction = stockTransaction;
    }

    public TransportDetails getTransporter() {
        return transporter;
    }

    public Dispatch transporter(TransportDetails transportDetails) {
        this.transporter = transportDetails;
        return this;
    }

    public void setTransporter(TransportDetails transportDetails) {
        this.transporter = transportDetails;
    }

    public TransportDetails getTransporter2() {
        return transporter2;
    }

    public Dispatch transporter2(TransportDetails transportDetails) {
        this.transporter2 = transportDetails;
        return this;
    }

    public void setTransporter2(TransportDetails transportDetails) {
        this.transporter2 = transportDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dispatch)) {
            return false;
        }
        return id != null && id.equals(((Dispatch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dispatch{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
