package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.TransportDetails;
import com.dhavalapp.com.service.TransportDetailsService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.TransportDetails}.
 */
@RestController
@RequestMapping("/api")
public class TransportDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TransportDetailsResource.class);

    private static final String ENTITY_NAME = "transportDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransportDetailsService transportDetailsService;

    public TransportDetailsResource(TransportDetailsService transportDetailsService) {
        this.transportDetailsService = transportDetailsService;
    }

    /**
     * {@code POST  /transport-details} : Create a new transportDetails.
     *
     * @param transportDetails the transportDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transportDetails, or with status {@code 400 (Bad Request)} if the transportDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transport-details")
    public ResponseEntity<TransportDetails> createTransportDetails(@Valid @RequestBody TransportDetails transportDetails) throws URISyntaxException {
        log.debug("REST request to save TransportDetails : {}", transportDetails);
        if (transportDetails.getId() != null) {
            throw new BadRequestAlertException("A new transportDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransportDetails result = transportDetailsService.save(transportDetails);
        return ResponseEntity.created(new URI("/api/transport-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transport-details} : Updates an existing transportDetails.
     *
     * @param transportDetails the transportDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transportDetails,
     * or with status {@code 400 (Bad Request)} if the transportDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transportDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transport-details")
    public ResponseEntity<TransportDetails> updateTransportDetails(@Valid @RequestBody TransportDetails transportDetails) throws URISyntaxException {
        log.debug("REST request to update TransportDetails : {}", transportDetails);
        if (transportDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransportDetails result = transportDetailsService.save(transportDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transportDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transport-details} : get all the transportDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transportDetails in body.
     */
    @GetMapping("/transport-details")
    public List<TransportDetails> getAllTransportDetails() {
        log.debug("REST request to get all TransportDetails");
        return transportDetailsService.findAll();
    }

    /**
     * {@code GET  /transport-details/:id} : get the "id" transportDetails.
     *
     * @param id the id of the transportDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transportDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transport-details/{id}")
    public ResponseEntity<TransportDetails> getTransportDetails(@PathVariable Long id) {
        log.debug("REST request to get TransportDetails : {}", id);
        Optional<TransportDetails> transportDetails = transportDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transportDetails);
    }

    /**
     * {@code DELETE  /transport-details/:id} : delete the "id" transportDetails.
     *
     * @param id the id of the transportDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transport-details/{id}")
    public ResponseEntity<Void> deleteTransportDetails(@PathVariable Long id) {
        log.debug("REST request to delete TransportDetails : {}", id);
        transportDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
