package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.BillingLocation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BillingLocation}.
 */
public interface BillingLocationService {

    /**
     * Save a billingLocation.
     *
     * @param billingLocation the entity to save.
     * @return the persisted entity.
     */
    BillingLocation save(BillingLocation billingLocation);

    /**
     * Get all the billingLocations.
     *
     * @return the list of entities.
     */
    List<BillingLocation> findAll();


    /**
     * Get the "id" billingLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillingLocation> findOne(Long id);

    /**
     * Delete the "id" billingLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
