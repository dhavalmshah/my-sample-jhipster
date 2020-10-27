package com.dhavalapp.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A BillingCompany.
 */
@Entity
@Table(name = "billing_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Size(max = 65535)
    @Column(name = "notes", length = 65535)
    private String notes;

    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BillingLocation> locations = new HashSet<>();

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

    public BillingCompany name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public BillingCompany notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<BillingLocation> getLocations() {
        return locations;
    }

    public BillingCompany locations(Set<BillingLocation> billingLocations) {
        this.locations = billingLocations;
        return this;
    }

    public BillingCompany addLocations(BillingLocation billingLocation) {
        this.locations.add(billingLocation);
        billingLocation.setCompany(this);
        return this;
    }

    public BillingCompany removeLocations(BillingLocation billingLocation) {
        this.locations.remove(billingLocation);
        billingLocation.setCompany(null);
        return this;
    }

    public void setLocations(Set<BillingLocation> billingLocations) {
        this.locations = billingLocations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingCompany)) {
            return false;
        }
        return id != null && id.equals(((BillingCompany) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingCompany{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
