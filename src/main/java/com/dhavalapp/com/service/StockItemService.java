package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.StockItem;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StockItem}.
 */
public interface StockItemService {

    /**
     * Save a stockItem.
     *
     * @param stockItem the entity to save.
     * @return the persisted entity.
     */
    StockItem save(StockItem stockItem);

    /**
     * Get all the stockItems.
     *
     * @return the list of entities.
     */
    List<StockItem> findAll();


    /**
     * Get the "id" stockItem.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StockItem> findOne(Long id);

    /**
     * Delete the "id" stockItem.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
