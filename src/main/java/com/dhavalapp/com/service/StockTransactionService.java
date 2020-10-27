package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.StockTransaction;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StockTransaction}.
 */
public interface StockTransactionService {

    /**
     * Save a stockTransaction.
     *
     * @param stockTransaction the entity to save.
     * @return the persisted entity.
     */
    StockTransaction save(StockTransaction stockTransaction);

    /**
     * Get all the stockTransactions.
     *
     * @return the list of entities.
     */
    List<StockTransaction> findAll();


    /**
     * Get the "id" stockTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockTransaction> findOne(Long id);

    /**
     * Delete the "id" stockTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
