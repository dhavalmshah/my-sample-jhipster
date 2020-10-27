package com.dhavalapp.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.dhavalapp.com.domain.enumeration.StockTransactionType;

/**
 * A StockTransaction.
 */
@Entity
@Table(name = "stock_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 65335)
    @Column(name = "description", length = 65335, nullable = false)
    private String description;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private StockTransactionType transactionType;

    @OneToMany(mappedBy = "creditTrans")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<StockItem> credits = new HashSet<>();

    @OneToMany(mappedBy = "debitTrans")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<StockItem> debits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public StockTransaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public StockTransaction transactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public StockTransactionType getTransactionType() {
        return transactionType;
    }

    public StockTransaction transactionType(StockTransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(StockTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Set<StockItem> getCredits() {
        return credits;
    }

    public StockTransaction credits(Set<StockItem> stockItems) {
        this.credits = stockItems;
        return this;
    }

    public StockTransaction addCredits(StockItem stockItem) {
        this.credits.add(stockItem);
        stockItem.setCreditTrans(this);
        return this;
    }

    public StockTransaction removeCredits(StockItem stockItem) {
        this.credits.remove(stockItem);
        stockItem.setCreditTrans(null);
        return this;
    }

    public void setCredits(Set<StockItem> stockItems) {
        this.credits = stockItems;
    }

    public Set<StockItem> getDebits() {
        return debits;
    }

    public StockTransaction debits(Set<StockItem> stockItems) {
        this.debits = stockItems;
        return this;
    }

    public StockTransaction addDebits(StockItem stockItem) {
        this.debits.add(stockItem);
        stockItem.setDebitTrans(this);
        return this;
    }

    public StockTransaction removeDebits(StockItem stockItem) {
        this.debits.remove(stockItem);
        stockItem.setDebitTrans(null);
        return this;
    }

    public void setDebits(Set<StockItem> stockItems) {
        this.debits = stockItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StockTransaction)) {
            return false;
        }
        return id != null && id.equals(((StockTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockTransaction{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            "}";
    }
}
