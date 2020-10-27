package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.Packing;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Packing}.
 */
public interface PackingService {

    /**
     * Save a packing.
     *
     * @param packing the entity to save.
     * @return the persisted entity.
     */
    Packing save(Packing packing);

    /**
     * Get all the packings.
     *
     * @return the list of entities.
     */
    List<Packing> findAll();


    /**
     * Get the "id" packing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Packing> findOne(Long id);

    /**
     * Delete the "id" packing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
