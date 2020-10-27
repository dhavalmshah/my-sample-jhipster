package com.dhavalapp.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dhavalapp.com.domain.enumeration.GoodsReceivedStatus;

/**
 * A GoodsReceived.
 */
@Entity
@Table(name = "goods_received")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoodsReceived implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GoodsReceivedStatus status;

    @OneToOne
    @JoinColumn(unique = true)
    private StockTransaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoodsReceivedStatus getStatus() {
        return status;
    }

    public GoodsReceived status(GoodsReceivedStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GoodsReceivedStatus status) {
        this.status = status;
    }

    public StockTransaction getTransaction() {
        return transaction;
    }

    public GoodsReceived transaction(StockTransaction stockTransaction) {
        this.transaction = stockTransaction;
        return this;
    }

    public void setTransaction(StockTransaction stockTransaction) {
        this.transaction = stockTransaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoodsReceived)) {
            return false;
        }
        return id != null && id.equals(((GoodsReceived) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoodsReceived{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}