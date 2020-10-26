package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.PhoneNumber;
import com.dhavalapp.com.service.PhoneNumberService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.PhoneNumber}.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberResource {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberResource.class);

    private static final String ENTITY_NAME = "phoneNumber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberResource(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    /**
     * {@code POST  /phone-numbers} : Create a new phoneNumber.
     *
     * @param phoneNumber the phoneNumber to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phoneNumber, or with status {@code 400 (Bad Request)} if the phoneNumber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phone-numbers")
    public ResponseEntity<PhoneNumber> createPhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber) throws URISyntaxException {
        log.debug("REST request to save PhoneNumber : {}", phoneNumber);
        if (phoneNumber.getId() != null) {
            throw new BadRequestAlertException("A new phoneNumber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhoneNumber result = phoneNumberService.save(phoneNumber);
        return ResponseEntity.created(new URI("/api/phone-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phone-numbers} : Updates an existing phoneNumber.
     *
     * @param phoneNumber the phoneNumber to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phoneNumber,
     * or with status {@code 400 (Bad Request)} if the phoneNumber is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phoneNumber couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phone-numbers")
    public ResponseEntity<PhoneNumber> updatePhoneNumber(@Valid @RequestBody PhoneNumber phoneNumber) throws URISyntaxException {
        log.debug("REST request to update PhoneNumber : {}", phoneNumber);
        if (phoneNumber.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PhoneNumber result = phoneNumberService.save(phoneNumber);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phoneNumber.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /phone-numbers} : get all the phoneNumbers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phoneNumbers in body.
     */
    @GetMapping("/phone-numbers")
    public List<PhoneNumber> getAllPhoneNumbers() {
        log.debug("REST request to get all PhoneNumbers");
        return phoneNumberService.findAll();
    }

    /**
     * {@code GET  /phone-numbers/:id} : get the "id" phoneNumber.
     *
     * @param id the id of the phoneNumber to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phoneNumber, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phone-numbers/{id}")
    public ResponseEntity<PhoneNumber> getPhoneNumber(@PathVariable Long id) {
        log.debug("REST request to get PhoneNumber : {}", id);
        Optional<PhoneNumber> phoneNumber = phoneNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phoneNumber);
    }

    /**
     * {@code DELETE  /phone-numbers/:id} : delete the "id" phoneNumber.
     *
     * @param id the id of the phoneNumber to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phone-numbers/{id}")
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable Long id) {
        log.debug("REST request to delete PhoneNumber : {}", id);
        phoneNumberService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
