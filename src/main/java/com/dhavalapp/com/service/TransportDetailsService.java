package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.TransportDetails;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TransportDetails}.
 */
public interface TransportDetailsService {

    /**
     * Save a transportDetails.
     *
     * @param transportDetails the entity to save.
     * @return the persisted entity.
     */
    TransportDetails save(TransportDetails transportDetails);

    /**
     * Get all the transportDetails.
     *
     * @return the list of entities.
     */
    List<TransportDetails> findAll();


    /**
     * Get the "id" transportDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransportDetails> findOne(Long id);

    /**
     * Delete the "id" transportDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
