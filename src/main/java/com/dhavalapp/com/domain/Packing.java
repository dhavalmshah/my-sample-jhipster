package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dhavalapp.com.domain.enumeration.PackingType;

/**
 * A Packing.
 */
@Entity
@Table(name = "packing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Packing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "name", length = 10, nullable = false)
    private String name;

    /**
     * quantity in a unit of item, e.g. if it is drums then quantity is 1, because every drum is a single piece
     */
    @NotNull
    @Min(value = 0)
    @ApiModelProperty(value = "quantity in a unit of item, e.g. if it is drums then quantity is 1, because every drum is a single piece", required = true)
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "packing_type", nullable = false)
    private PackingType packingType;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "net_weight", nullable = false)
    private Double netWeight;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "gross_weight", nullable = false)
    private Double grossWeight;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "packings", allowSetters = true)
    private Unit unit;

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

    public Packing name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Packing quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public PackingType getPackingType() {
        return packingType;
    }

    public Packing packingType(PackingType packingType) {
        this.packingType = packingType;
        return this;
    }

    public void setPackingType(PackingType packingType) {
        this.packingType = packingType;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public Packing netWeight(Double netWeight) {
        this.netWeight = netWeight;
        return this;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public Packing grossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
        return this;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Unit getUnit() {
        return unit;
    }

    public Packing unit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Packing)) {
            return false;
        }
        return id != null && id.equals(((Packing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Packing{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", quantity=" + getQuantity() +
            ", packingType='" + getPackingType() + "'" +
            ", netWeight=" + getNetWeight() +
            ", grossWeight=" + getGrossWeight() +
            "}";
    }
}
