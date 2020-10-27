package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.Dispatch;
import com.dhavalapp.com.service.DispatchService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.Dispatch}.
 */
@RestController
@RequestMapping("/api")
public class DispatchResource {

    private final Logger log = LoggerFactory.getLogger(DispatchResource.class);

    private static final String ENTITY_NAME = "dispatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispatchService dispatchService;

    public DispatchResource(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    /**
     * {@code POST  /dispatches} : Create a new dispatch.
     *
     * @param dispatch the dispatch to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispatch, or with status {@code 400 (Bad Request)} if the dispatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispatches")
    public ResponseEntity<Dispatch> createDispatch(@Valid @RequestBody Dispatch dispatch) throws URISyntaxException {
        log.debug("REST request to save Dispatch : {}", dispatch);
        if (dispatch.getId() != null) {
            throw new BadRequestAlertException("A new dispatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dispatch result = dispatchService.save(dispatch);
        return ResponseEntity.created(new URI("/api/dispatches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispatches} : Updates an existing dispatch.
     *
     * @param dispatch the dispatch to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispatch,
     * or with status {@code 400 (Bad Request)} if the dispatch is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispatch couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispatches")
    public ResponseEntity<Dispatch> updateDispatch(@Valid @RequestBody Dispatch dispatch) throws URISyntaxException {
        log.debug("REST request to update Dispatch : {}", dispatch);
        if (dispatch.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dispatch result = dispatchService.save(dispatch);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispatch.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dispatches} : get all the dispatches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispatches in body.
     */
    @GetMapping("/dispatches")
    public List<Dispatch> getAllDispatches() {
        log.debug("REST request to get all Dispatches");
        return dispatchService.findAll();
    }

    /**
     * {@code GET  /dispatches/:id} : get the "id" dispatch.
     *
     * @param id the id of the dispatch to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispatch, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispatches/{id}")
    public ResponseEntity<Dispatch> getDispatch(@PathVariable Long id) {
        log.debug("REST request to get Dispatch : {}", id);
        Optional<Dispatch> dispatch = dispatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispatch);
    }

    /**
     * {@code DELETE  /dispatches/:id} : delete the "id" dispatch.
     *
     * @param id the id of the dispatch to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispatches/{id}")
    public ResponseEntity<Void> deleteDispatch(@PathVariable Long id) {
        log.debug("REST request to delete Dispatch : {}", id);
        dispatchService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
