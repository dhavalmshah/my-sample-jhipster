package com.dhavalapp.com.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^[A-Z][a-z]+$")
    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^[A-Z][a-z]+$")
    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Size(max = 255)
    @Pattern(regexp = "^[A-Z][a-z]+$")
    @Column(name = "designation", length = 255)
    private String designation;

    @Size(max = 65535)
    @Column(name = "notes", length = 65535)
    private String notes;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "contact")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PhoneNumber> phones = new HashSet<>();

    @OneToMany(mappedBy = "contact")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmailAddress> emails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Contact firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public Contact designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNotes() {
        return notes;
    }

    public Contact notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Address getAddress() {
        return address;
    }

    public Contact address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<PhoneNumber> getPhones() {
        return phones;
    }

    public Contact phones(Set<PhoneNumber> phoneNumbers) {
        this.phones = phoneNumbers;
        return this;
    }

    public Contact addPhones(PhoneNumber phoneNumber) {
        this.phones.add(phoneNumber);
        phoneNumber.setContact(this);
        return this;
    }

    public Contact removePhones(PhoneNumber phoneNumber) {
        this.phones.remove(phoneNumber);
        phoneNumber.setContact(null);
        return this;
    }

    public void setPhones(Set<PhoneNumber> phoneNumbers) {
        this.phones = phoneNumbers;
    }

    public Set<EmailAddress> getEmails() {
        return emails;
    }

    public Contact emails(Set<EmailAddress> emailAddresses) {
        this.emails = emailAddresses;
        return this;
    }

    public Contact addEmails(EmailAddress emailAddress) {
        this.emails.add(emailAddress);
        emailAddress.setContact(this);
        return this;
    }

    public Contact removeEmails(EmailAddress emailAddress) {
        this.emails.remove(emailAddress);
        emailAddress.setContact(null);
        return this;
    }

    public void setEmails(Set<EmailAddress> emailAddresses) {
        this.emails = emailAddresses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }
}
