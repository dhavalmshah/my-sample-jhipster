package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dhavalapp.com.domain.enumeration.StockStatus;

/**
 * A StockItem.
 */
@Entity
@Table(name = "stock_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StockStatus status;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Size(max = 50)
    @Column(name = "batch_number", length = 50, nullable = false)
    private String batchNumber;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "stockItems", allowSetters = true)
    private Unit unit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "stockItems", allowSetters = true)
    private Packing packing;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "stockItems", allowSetters = true)
    private Product product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "stockItems", allowSetters = true)
    private BillingLocation location;

    @ManyToOne
    @JsonIgnoreProperties(value = "credits", allowSetters = true)
    private StockTransaction creditTrans;

    @ManyToOne
    @JsonIgnoreProperties(value = "debits", allowSetters = true)
    private StockTransaction debitTrans;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockStatus getStatus() {
        return status;
    }

    public StockItem status(StockStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(StockStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public StockItem quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public StockItem batchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Unit getUnit() {
        return unit;
    }

    public StockItem unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Packing getPacking() {
        return packing;
    }

    public StockItem packing(Packing packing) {
        this.packing = packing;
        return this;
    }

    public void setPacking(Packing packing) {
        this.packing = packing;
    }

    public Product getProduct() {
        return product;
    }

    public StockItem product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BillingLocation getLocation() {
        return location;
    }

    public StockItem location(BillingLocation billingLocation) {
        this.location = billingLocation;
        return this;
    }

    public void setLocation(BillingLocation billingLocation) {
        this.location = billingLocation;
    }

    public StockTransaction getCreditTrans() {
        return creditTrans;
    }

    public StockItem creditTrans(StockTransaction stockTransaction) {
        this.creditTrans = stockTransaction;
        return this;
    }

    public void setCreditTrans(StockTransaction stockTransaction) {
        this.creditTrans = stockTransaction;
    }

    public StockTransaction getDebitTrans() {
        return debitTrans;
    }

    public StockItem debitTrans(StockTransaction stockTransaction) {
        this.debitTrans = stockTransaction;
        return this;
    }

    public void setDebitTrans(StockTransaction stockTransaction) {
        this.debitTrans = stockTransaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockItem)) {
            return false;
        }
        return id != null && id.equals(((StockItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockItem{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", batchNumber='" + getBatchNumber() + "'" +
            "}";
    }
}
