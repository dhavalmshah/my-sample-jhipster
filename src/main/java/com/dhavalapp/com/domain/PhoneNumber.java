package com.dhavalapp.com.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.dhavalapp.com.domain.enumeration.ContactType;

/**
 * Phone number
 */
@ApiModel(description = "Phone number")
@Entity
@Table(name = "phone_number")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContactType type;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @NotNull
    @Size(min = 5, max = 255)
    @Pattern(regexp = "^[-\\s\\./0-9]*$")
    @Column(name = "phone_number", length = 255, nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = "phones", allowSetters = true)
    private Contact contact;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactType getType() {
        return type;
    }

    public PhoneNumber type(ContactType type) {
        this.type = type;
        return this;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public PhoneNumber countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public PhoneNumber phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Contact getContact() {
        return contact;
    }

    public PhoneNumber contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneNumber)) {
            return false;
        }
        return id != null && id.equals(((PhoneNumber) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhoneNumber{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
