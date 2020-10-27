package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.BillingLocation;
import com.dhavalapp.com.service.BillingLocationService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.BillingLocation}.
 */
@RestController
@RequestMapping("/api")
public class BillingLocationResource {

    private final Logger log = LoggerFactory.getLogger(BillingLocationResource.class);

    private static final String ENTITY_NAME = "billingLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillingLocationService billingLocationService;

    public BillingLocationResource(BillingLocationService billingLocationService) {
        this.billingLocationService = billingLocationService;
    }

    /**
     * {@code POST  /billing-locations} : Create a new billingLocation.
     *
     * @param billingLocation the billingLocation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billingLocation, or with status {@code 400 (Bad Request)} if the billingLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billing-locations")
    public ResponseEntity<BillingLocation> createBillingLocation(@Valid @RequestBody BillingLocation billingLocation) throws URISyntaxException {
        log.debug("REST request to save BillingLocation : {}", billingLocation);
        if (billingLocation.getId() != null) {
            throw new BadRequestAlertException("A new billingLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingLocation result = billingLocationService.save(billingLocation);
        return ResponseEntity.created(new URI("/api/billing-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billing-locations} : Updates an existing billingLocation.
     *
     * @param billingLocation the billingLocation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingLocation,
     * or with status {@code 400 (Bad Request)} if the billingLocation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billingLocation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billing-locations")
    public ResponseEntity<BillingLocation> updateBillingLocation(@Valid @RequestBody BillingLocation billingLocation) throws URISyntaxException {
        log.debug("REST request to update BillingLocation : {}", billingLocation);
        if (billingLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingLocation result = billingLocationService.save(billingLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billingLocation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /billing-locations} : get all the billingLocations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billingLocations in body.
     */
    @GetMapping("/billing-locations")
    public List<BillingLocation> getAllBillingLocations() {
        log.debug("REST request to get all BillingLocations");
        return billingLocationService.findAll();
    }

    /**
     * {@code GET  /billing-locations/:id} : get the "id" billingLocation.
     *
     * @param id the id of the billingLocation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billingLocation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billing-locations/{id}")
    public ResponseEntity<BillingLocation> getBillingLocation(@PathVariable Long id) {
        log.debug("REST request to get BillingLocation : {}", id);
        Optional<BillingLocation> billingLocation = billingLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingLocation);
    }

    /**
     * {@code DELETE  /billing-locations/:id} : delete the "id" billingLocation.
     *
     * @param id the id of the billingLocation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billing-locations/{id}")
    public ResponseEntity<Void> deleteBillingLocation(@PathVariable Long id) {
        log.debug("REST request to delete BillingLocation : {}", id);
        billingLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
