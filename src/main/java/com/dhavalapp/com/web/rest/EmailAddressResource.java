package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.EmailAddress;
import com.dhavalapp.com.service.EmailAddressService;
import com.dhavalapp.com.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dhavalapp.com.domain.EmailAddress}.
 */
@RestController
@RequestMapping("/api")
public class EmailAddressResource {

    private final Logger log = LoggerFactory.getLogger(EmailAddressResource.class);

    private static final String ENTITY_NAME = "emailAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailAddressService emailAddressService;

    public EmailAddressResource(EmailAddressService emailAddressService) {
        this.emailAddressService = emailAddressService;
    }

    /**
     * {@code POST  /email-addresses} : Create a new emailAddress.
     *
     * @param emailAddress the emailAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailAddress, or with status {@code 400 (Bad Request)} if the emailAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-addresses")
    public ResponseEntity<EmailAddress> createEmailAddress(@Valid @RequestBody EmailAddress emailAddress) throws URISyntaxException {
        log.debug("REST request to save EmailAddress : {}", emailAddress);
        if (emailAddress.getId() != null) {
            throw new BadRequestAlertException("A new emailAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailAddress result = emailAddressService.save(emailAddress);
        return ResponseEntity.created(new URI("/api/email-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-addresses} : Updates an existing emailAddress.
     *
     * @param emailAddress the emailAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailAddress,
     * or with status {@code 400 (Bad Request)} if the emailAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-addresses")
    public ResponseEntity<EmailAddress> updateEmailAddress(@Valid @RequestBody EmailAddress emailAddress) throws URISyntaxException {
        log.debug("REST request to update EmailAddress : {}", emailAddress);
        if (emailAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailAddress result = emailAddressService.save(emailAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-addresses} : get all the emailAddresses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailAddresses in body.
     */
    @GetMapping("/email-addresses")
    public List<EmailAddress> getAllEmailAddresses() {
        log.debug("REST request to get all EmailAddresses");
        return emailAddressService.findAll();
    }

    /**
     * {@code GET  /email-addresses/:id} : get the "id" emailAddress.
     *
     * @param id the id of the emailAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-addresses/{id}")
    public ResponseEntity<EmailAddress> getEmailAddress(@PathVariable Long id) {
        log.debug("REST request to get EmailAddress : {}", id);
        Optional<EmailAddress> emailAddress = emailAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailAddress);
    }

    /**
     * {@code DELETE  /email-addresses/:id} : delete the "id" emailAddress.
     *
     * @param id the id of the emailAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-addresses/{id}")
    public ResponseEntity<Void> deleteEmailAddress(@PathVariable Long id) {
        log.debug("REST request to delete EmailAddress : {}", id);
        emailAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
