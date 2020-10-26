package com.dhavalapp.com.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dhavalapp.com.domain.enumeration.QuantityType;

/**
 * A Unit.
 */
@Entity
@Table(name = "unit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_type")
    private QuantityType quantityType;

    @NotNull
    @Size(max = 50)
    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "base_unit_multiplier", nullable = false)
    private Integer baseUnitMultiplier;

    /**
     * If the unit is smaller than base unit then check this flag
     */
    @NotNull
    @ApiModelProperty(value = "If the unit is smaller than base unit then check this flag", required = true)
    @Column(name = "is_smaller_than_base", nullable = false)
    private Boolean isSmallerThanBase;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Unit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuantityType getQuantityType() {
        return quantityType;
    }

    public Unit quantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
        return this;
    }

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
    }

    public String getFullName() {
        return fullName;
    }

    public Unit fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBaseUnitMultiplier() {
        return baseUnitMultiplier;
    }

    public Unit baseUnitMultiplier(Integer baseUnitMultiplier) {
        this.baseUnitMultiplier = baseUnitMultiplier;
        return this;
    }

    public void setBaseUnitMultiplier(Integer baseUnitMultiplier) {
        this.baseUnitMultiplier = baseUnitMultiplier;
    }

    public Boolean isIsSmallerThanBase() {
        return isSmallerThanBase;
    }

    public Unit isSmallerThanBase(Boolean isSmallerThanBase) {
        this.isSmallerThanBase = isSmallerThanBase;
        return this;
    }

    public void setIsSmallerThanBase(Boolean isSmallerThanBase) {
        this.isSmallerThanBase = isSmallerThanBase;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Unit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantityType='" + getQuantityType() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", baseUnitMultiplier=" + getBaseUnitMultiplier() +
            ", isSmallerThanBase='" + isIsSmallerThanBase() + "'" +
            "}";
    }
}
