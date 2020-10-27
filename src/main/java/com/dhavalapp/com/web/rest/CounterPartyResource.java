package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.CounterParty;
import com.dhavalapp.com.service.CounterPartyService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.CounterParty}.
 */
@RestController
@RequestMapping("/api")
public class CounterPartyResource {

    private final Logger log = LoggerFactory.getLogger(CounterPartyResource.class);

    private static final String ENTITY_NAME = "counterParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CounterPartyService counterPartyService;

    public CounterPartyResource(CounterPartyService counterPartyService) {
        this.counterPartyService = counterPartyService;
    }

    /**
     * {@code POST  /counter-parties} : Create a new counterParty.
     *
     * @param counterParty the counterParty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new counterParty, or with status {@code 400 (Bad Request)} if the counterParty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counter-parties")
    public ResponseEntity<CounterParty> createCounterParty(@Valid @RequestBody CounterParty counterParty) throws URISyntaxException {
        log.debug("REST request to save CounterParty : {}", counterParty);
        if (counterParty.getId() != null) {
            throw new BadRequestAlertException("A new counterParty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CounterParty result = counterPartyService.save(counterParty);
        return ResponseEntity.created(new URI("/api/counter-parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counter-parties} : Updates an existing counterParty.
     *
     * @param counterParty the counterParty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated counterParty,
     * or with status {@code 400 (Bad Request)} if the counterParty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the counterParty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counter-parties")
    public ResponseEntity<CounterParty> updateCounterParty(@Valid @RequestBody CounterParty counterParty) throws URISyntaxException {
        log.debug("REST request to update CounterParty : {}", counterParty);
        if (counterParty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CounterParty result = counterPartyService.save(counterParty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, counterParty.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /counter-parties} : get all the counterParties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of counterParties in body.
     */
    @GetMapping("/counter-parties")
    public List<CounterParty> getAllCounterParties() {
        log.debug("REST request to get all CounterParties");
        return counterPartyService.findAll();
    }

    /**
     * {@code GET  /counter-parties/:id} : get the "id" counterParty.
     *
     * @param id the id of the counterParty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the counterParty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counter-parties/{id}")
    public ResponseEntity<CounterParty> getCounterParty(@PathVariable Long id) {
        log.debug("REST request to get CounterParty : {}", id);
        Optional<CounterParty> counterParty = counterPartyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(counterParty);
    }

    /**
     * {@code DELETE  /counter-parties/:id} : delete the "id" counterParty.
     *
     * @param id the id of the counterParty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counter-parties/{id}")
    public ResponseEntity<Void> deleteCounterParty(@PathVariable Long id) {
        log.debug("REST request to delete CounterParty : {}", id);
        counterPartyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
