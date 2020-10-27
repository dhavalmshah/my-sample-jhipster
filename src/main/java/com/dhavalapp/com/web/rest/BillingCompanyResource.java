package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.BillingCompany;
import com.dhavalapp.com.service.BillingCompanyService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.BillingCompany}.
 */
@RestController
@RequestMapping("/api")
public class BillingCompanyResource {

    private final Logger log = LoggerFactory.getLogger(BillingCompanyResource.class);

    private static final String ENTITY_NAME = "billingCompany";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillingCompanyService billingCompanyService;

    public BillingCompanyResource(BillingCompanyService billingCompanyService) {
        this.billingCompanyService = billingCompanyService;
    }

    /**
     * {@code POST  /billing-companies} : Create a new billingCompany.
     *
     * @param billingCompany the billingCompany to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billingCompany, or with status {@code 400 (Bad Request)} if the billingCompany has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/billing-companies")
    public ResponseEntity<BillingCompany> createBillingCompany(@Valid @RequestBody BillingCompany billingCompany) throws URISyntaxException {
        log.debug("REST request to save BillingCompany : {}", billingCompany);
        if (billingCompany.getId() != null) {
            throw new BadRequestAlertException("A new billingCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillingCompany result = billingCompanyService.save(billingCompany);
        return ResponseEntity.created(new URI("/api/billing-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /billing-companies} : Updates an existing billingCompany.
     *
     * @param billingCompany the billingCompany to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billingCompany,
     * or with status {@code 400 (Bad Request)} if the billingCompany is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billingCompany couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/billing-companies")
    public ResponseEntity<BillingCompany> updateBillingCompany(@Valid @RequestBody BillingCompany billingCompany) throws URISyntaxException {
        log.debug("REST request to update BillingCompany : {}", billingCompany);
        if (billingCompany.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillingCompany result = billingCompanyService.save(billingCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billingCompany.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /billing-companies} : get all the billingCompanies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billingCompanies in body.
     */
    @GetMapping("/billing-companies")
    public List<BillingCompany> getAllBillingCompanies() {
        log.debug("REST request to get all BillingCompanies");
        return billingCompanyService.findAll();
    }

    /**
     * {@code GET  /billing-companies/:id} : get the "id" billingCompany.
     *
     * @param id the id of the billingCompany to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billingCompany, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/billing-companies/{id}")
    public ResponseEntity<BillingCompany> getBillingCompany(@PathVariable Long id) {
        log.debug("REST request to get BillingCompany : {}", id);
        Optional<BillingCompany> billingCompany = billingCompanyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billingCompany);
    }

    /**
     * {@code DELETE  /billing-companies/:id} : delete the "id" billingCompany.
     *
     * @param id the id of the billingCompany to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/billing-companies/{id}")
    public ResponseEntity<Void> deleteBillingCompany(@PathVariable Long id) {
        log.debug("REST request to delete BillingCompany : {}", id);
        billingCompanyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
