package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.Dispatch;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Dispatch}.
 */
public interface DispatchService {

    /**
     * Save a dispatch.
     *
     * @param dispatch the entity to save.
     * @return the persisted entity.
     */
    Dispatch save(Dispatch dispatch);

    /**
     * Get all the dispatches.
     *
     * @return the list of entities.
     */
    List<Dispatch> findAll();


    /**
     * Get the "id" dispatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Dispatch> findOne(Long id);

    /**
     * Delete the "id" dispatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
