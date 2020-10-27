package com.dhavalapp.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.dhavalapp.com.domain.enumeration.ProductCategory;

import com.dhavalapp.com.domain.enumeration.QuantityType;

/**
 * A ProductType.
 */
@Entity
@Table(name = "product_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_type")
    private QuantityType quantityType;

    @NotNull
    @Column(name = "hsn_number", nullable = false)
    private String hsnNumber;

    @NotNull
    @Size(max = 65535)
    @Column(name = "description", length = 65535, nullable = false)
    private String description;

    @OneToMany(mappedBy = "productType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Product> products = new HashSet<>();

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

    public ProductType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public ProductType category(ProductCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public QuantityType getQuantityType() {
        return quantityType;
    }

    public ProductType quantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
        return this;
    }

    public void setQuantityType(QuantityType quantityType) {
        this.quantityType = quantityType;
    }

    public String getHsnNumber() {
        return hsnNumber;
    }

    public ProductType hsnNumber(String hsnNumber) {
        this.hsnNumber = hsnNumber;
        return this;
    }

    public void setHsnNumber(String hsnNumber) {
        this.hsnNumber = hsnNumber;
    }

    public String getDescription() {
        return description;
    }

    public ProductType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public ProductType products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public ProductType addProduct(Product product) {
        this.products.add(product);
        product.setProductType(this);
        return this;
    }

    public ProductType removeProduct(Product product) {
        this.products.remove(product);
        product.setProductType(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductType)) {
            return false;
        }
        return id != null && id.equals(((ProductType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", quantityType='" + getQuantityType() + "'" +
            ", hsnNumber='" + getHsnNumber() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
