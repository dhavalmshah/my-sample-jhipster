package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.CounterParty;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CounterParty}.
 */
public interface CounterPartyService {

    /**
     * Save a counterParty.
     *
     * @param counterParty the entity to save.
     * @return the persisted entity.
     */
    CounterParty save(CounterParty counterParty);

    /**
     * Get all the counterParties.
     *
     * @return the list of entities.
     */
    List<CounterParty> findAll();


    /**
     * Get the "id" counterParty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CounterParty> findOne(Long id);

    /**
     * Delete the "id" counterParty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
