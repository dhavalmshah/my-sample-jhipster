package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Location implements Serializable {

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
    private CounterParty counterParty;

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

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public Location panNumber(String panNumber) {
        this.panNumber = panNumber;
        return this;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public Location gstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
        return this;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public Location contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public CounterParty getCounterParty() {
        return counterParty;
    }

    public Location counterParty(CounterParty counterParty) {
        this.counterParty = counterParty;
        return this;
    }

    public void setCounterParty(CounterParty counterParty) {
        this.counterParty = counterParty;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", panNumber='" + getPanNumber() + "'" +
            ", gstNumber='" + getGstNumber() + "'" +
            "}";
    }
}
