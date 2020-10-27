package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.Production;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Production}.
 */
public interface ProductionService {

    /**
     * Save a production.
     *
     * @param production the entity to save.
     * @return the persisted entity.
     */
    Production save(Production production);

    /**
     * Get all the productions.
     *
     * @return the list of entities.
     */
    List<Production> findAll();


    /**
     * Get the "id" production.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Production> findOne(Long id);

    /**
     * Delete the "id" production.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
