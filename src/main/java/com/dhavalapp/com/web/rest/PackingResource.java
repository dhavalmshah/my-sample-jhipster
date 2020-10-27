package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.Packing;
import com.dhavalapp.com.service.PackingService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.Packing}.
 */
@RestController
@RequestMapping("/api")
public class PackingResource {

    private final Logger log = LoggerFactory.getLogger(PackingResource.class);

    private static final String ENTITY_NAME = "packing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PackingService packingService;

    public PackingResource(PackingService packingService) {
        this.packingService = packingService;
    }

    /**
     * {@code POST  /packings} : Create a new packing.
     *
     * @param packing the packing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new packing, or with status {@code 400 (Bad Request)} if the packing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/packings")
    public ResponseEntity<Packing> createPacking(@Valid @RequestBody Packing packing) throws URISyntaxException {
        log.debug("REST request to save Packing : {}", packing);
        if (packing.getId() != null) {
            throw new BadRequestAlertException("A new packing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Packing result = packingService.save(packing);
        return ResponseEntity.created(new URI("/api/packings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /packings} : Updates an existing packing.
     *
     * @param packing the packing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated packing,
     * or with status {@code 400 (Bad Request)} if the packing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the packing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/packings")
    public ResponseEntity<Packing> updatePacking(@Valid @RequestBody Packing packing) throws URISyntaxException {
        log.debug("REST request to update Packing : {}", packing);
        if (packing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Packing result = packingService.save(packing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, packing.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /packings} : get all the packings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of packings in body.
     */
    @GetMapping("/packings")
    public List<Packing> getAllPackings() {
        log.debug("REST request to get all Packings");
        return packingService.findAll();
    }

    /**
     * {@code GET  /packings/:id} : get the "id" packing.
     *
     * @param id the id of the packing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the packing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/packings/{id}")
    public ResponseEntity<Packing> getPacking(@PathVariable Long id) {
        log.debug("REST request to get Packing : {}", id);
        Optional<Packing> packing = packingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(packing);
    }

    /**
     * {@code DELETE  /packings/:id} : delete the "id" packing.
     *
     * @param id the id of the packing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/packings/{id}")
    public ResponseEntity<Void> deletePacking(@PathVariable Long id) {
        log.debug("REST request to delete Packing : {}", id);
        packingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
