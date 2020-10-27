package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A BillingLocation.
 */
@Entity
@Table(name = "billing_location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^[A-Z][a-z]+\\d*$")
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "^[A-Z][a-z]+\\d*$")
    @Column(name = "short_code", length = 10, nullable = false, unique = true)
    private String shortCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "pan_number", length = 255, nullable = false)
    private String panNumber;

    @NotNull
    @Size(max = 255)
    @Column(name = "gst_number", length = 255, nullable = false)
    private String gstNumber;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Contact contact;

    @ManyToOne
    @JsonIgnoreProperties(value = "locations", allowSetters = true)
    private BillingCompany company;

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

    public BillingLocation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortCode() {
        return shortCode;
    }

    public BillingLocation shortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public BillingLocation panNumber(String panNumber) {
        this.panNumber = panNumber;
        return this;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public BillingLocation gstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
        return this;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public BillingLocation contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public BillingCompany getCompany() {
        return company;
    }

    public BillingLocation company(BillingCompany billingCompany) {
        this.company = billingCompany;
        return this;
    }

    public void setCompany(BillingCompany billingCompany) {
        this.company = billingCompany;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingLocation)) {
            return false;
        }
        return id != null && id.equals(((BillingLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingLocation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortCode='" + getShortCode() + "'" +
            ", panNumber='" + getPanNumber() + "'" +
            ", gstNumber='" + getGstNumber() + "'" +
            "}";
    }
}
