package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.GoodsReceived;
import com.dhavalapp.com.service.GoodsReceivedService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.GoodsReceived}.
 */
@RestController
@RequestMapping("/api")
public class GoodsReceivedResource {

    private final Logger log = LoggerFactory.getLogger(GoodsReceivedResource.class);

    private static final String ENTITY_NAME = "goodsReceived";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoodsReceivedService goodsReceivedService;

    public GoodsReceivedResource(GoodsReceivedService goodsReceivedService) {
        this.goodsReceivedService = goodsReceivedService;
    }

    /**
     * {@code POST  /goods-receiveds} : Create a new goodsReceived.
     *
     * @param goodsReceived the goodsReceived to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goodsReceived, or with status {@code 400 (Bad Request)} if the goodsReceived has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goods-receiveds")
    public ResponseEntity<GoodsReceived> createGoodsReceived(@Valid @RequestBody GoodsReceived goodsReceived) throws URISyntaxException {
        log.debug("REST request to save GoodsReceived : {}", goodsReceived);
        if (goodsReceived.getId() != null) {
            throw new BadRequestAlertException("A new goodsReceived cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoodsReceived result = goodsReceivedService.save(goodsReceived);
        return ResponseEntity.created(new URI("/api/goods-receiveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goods-receiveds} : Updates an existing goodsReceived.
     *
     * @param goodsReceived the goodsReceived to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goodsReceived,
     * or with status {@code 400 (Bad Request)} if the goodsReceived is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goodsReceived couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goods-receiveds")
    public ResponseEntity<GoodsReceived> updateGoodsReceived(@Valid @RequestBody GoodsReceived goodsReceived) throws URISyntaxException {
        log.debug("REST request to update GoodsReceived : {}", goodsReceived);
        if (goodsReceived.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoodsReceived result = goodsReceivedService.save(goodsReceived);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goodsReceived.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goods-receiveds} : get all the goodsReceiveds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goodsReceiveds in body.
     */
    @GetMapping("/goods-receiveds")
    public List<GoodsReceived> getAllGoodsReceiveds() {
        log.debug("REST request to get all GoodsReceiveds");
        return goodsReceivedService.findAll();
    }

    /**
     * {@code GET  /goods-receiveds/:id} : get the "id" goodsReceived.
     *
     * @param id the id of the goodsReceived to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goodsReceived, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goods-receiveds/{id}")
    public ResponseEntity<GoodsReceived> getGoodsReceived(@PathVariable Long id) {
        log.debug("REST request to get GoodsReceived : {}", id);
        Optional<GoodsReceived> goodsReceived = goodsReceivedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goodsReceived);
    }

    /**
     * {@code DELETE  /goods-receiveds/:id} : delete the "id" goodsReceived.
     *
     * @param id the id of the goodsReceived to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goods-receiveds/{id}")
    public ResponseEntity<Void> deleteGoodsReceived(@PathVariable Long id) {
        log.debug("REST request to delete GoodsReceived : {}", id);
        goodsReceivedService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
