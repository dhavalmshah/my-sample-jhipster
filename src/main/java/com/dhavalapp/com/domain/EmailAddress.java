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
 * Email Address
 */
@ApiModel(description = "Email Address")
@Entity
@Table(name = "email_address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ContactType type;

    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&\\*\\+\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~\\-]+@[a-zA-Z0-9-]+\\.(?:[a-zA-Z0-9-]+)*$")
    @Column(name = "email_address", length = 255, nullable = false)
    private String emailAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = "emails", allowSetters = true)
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

    public EmailAddress type(ContactType type) {
        this.type = type;
        return this;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public EmailAddress emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Contact getContact() {
        return contact;
    }

    public EmailAddress contact(Contact contact) {
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
        if (!(o instanceof EmailAddress)) {
            return false;
        }
        return id != null && id.equals(((EmailAddress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailAddress{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            "}";
    }
}
