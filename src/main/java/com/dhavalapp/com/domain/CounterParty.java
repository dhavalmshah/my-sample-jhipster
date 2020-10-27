package com.dhavalapp.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.dhavalapp.com.domain.enumeration.CounterPartyType;

/**
 * A CounterParty.
 */
@Entity
@Table(name = "counter_party")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CounterParty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CounterPartyType type;

    @Size(max = 65535)
    @Column(name = "notes", length = 65535)
    private String notes;

    @OneToMany(mappedBy = "counterParty")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Location> locations = new HashSet<>();

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

    public CounterParty name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CounterPartyType getType() {
        return type;
    }

    public CounterParty type(CounterPartyType type) {
        this.type = type;
        return this;
    }

    public void setType(CounterPartyType type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public CounterParty notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public CounterParty locations(Set<Location> locations) {
        this.locations = locations;
        return this;
    }

    public CounterParty addLocations(Location location) {
        this.locations.add(location);
        location.setCounterParty(this);
        return this;
    }

    public CounterParty removeLocations(Location location) {
        this.locations.remove(location);
        location.setCounterParty(null);
        return this;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CounterParty)) {
            return false;
        }
        return id != null && id.equals(((CounterParty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CounterParty{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
