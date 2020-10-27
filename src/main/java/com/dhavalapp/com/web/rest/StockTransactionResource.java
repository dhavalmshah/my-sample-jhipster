package com.dhavalapp.com.web.rest;

import com.dhavalapp.com.domain.StockTransaction;
import com.dhavalapp.com.service.StockTransactionService;
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
 * REST controller for managing {@link com.dhavalapp.com.domain.StockTransaction}.
 */
@RestController
@RequestMapping("/api")
public class StockTransactionResource {

    private final Logger log = LoggerFactory.getLogger(StockTransactionResource.class);

    private static final String ENTITY_NAME = "stockTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StockTransactionService stockTransactionService;

    public StockTransactionResource(StockTransactionService stockTransactionService) {
        this.stockTransactionService = stockTransactionService;
    }

    /**
     * {@code POST  /stock-transactions} : Create a new stockTransaction.
     *
     * @param stockTransaction the stockTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stockTransaction, or with status {@code 400 (Bad Request)} if the stockTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stock-transactions")
    public ResponseEntity<StockTransaction> createStockTransaction(@Valid @RequestBody StockTransaction stockTransaction) throws URISyntaxException {
        log.debug("REST request to save StockTransaction : {}", stockTransaction);
        if (stockTransaction.getId() != null) {
            throw new BadRequestAlertException("A new stockTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockTransaction result = stockTransactionService.save(stockTransaction);
        return ResponseEntity.created(new URI("/api/stock-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stock-transactions} : Updates an existing stockTransaction.
     *
     * @param stockTransaction the stockTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stockTransaction,
     * or with status {@code 400 (Bad Request)} if the stockTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stockTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stock-transactions")
    public ResponseEntity<StockTransaction> updateStockTransaction(@Valid @RequestBody StockTransaction stockTransaction) throws URISyntaxException {
        log.debug("REST request to update StockTransaction : {}", stockTransaction);
        if (stockTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StockTransaction result = stockTransactionService.save(stockTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stockTransaction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stock-transactions} : get all the stockTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stockTransactions in body.
     */
    @GetMapping("/stock-transactions")
    public List<StockTransaction> getAllStockTransactions() {
        log.debug("REST request to get all StockTransactions");
        return stockTransactionService.findAll();
    }

    /**
     * {@code GET  /stock-transactions/:id} : get the "id" stockTransaction.
     *
     * @param id the id of the stockTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stockTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stock-transactions/{id}")
    public ResponseEntity<StockTransaction> getStockTransaction(@PathVariable Long id) {
        log.debug("REST request to get StockTransaction : {}", id);
        Optional<StockTransaction> stockTransaction = stockTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stockTransaction);
    }

    /**
     * {@code DELETE  /stock-transactions/:id} : delete the "id" stockTransaction.
     *
     * @param id the id of the stockTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stock-transactions/{id}")
    public ResponseEntity<Void> deleteStockTransaction(@PathVariable Long id) {
        log.debug("REST request to delete StockTransaction : {}", id);
        stockTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
