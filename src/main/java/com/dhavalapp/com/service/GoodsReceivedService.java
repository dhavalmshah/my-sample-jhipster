package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.GoodsReceived;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GoodsReceived}.
 */
public interface GoodsReceivedService {

    /**
     * Save a goodsReceived.
     *
     * @param goodsReceived the entity to save.
     * @return the persisted entity.
     */
    GoodsReceived save(GoodsReceived goodsReceived);

    /**
     * Get all the goodsReceiveds.
     *
     * @return the list of entities.
     */
    List<GoodsReceived> findAll();


    /**
     * Get the "id" goodsReceived.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoodsReceived> findOne(Long id);

    /**
     * Delete the "id" goodsReceived.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
