package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.BillingCompany;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BillingCompany}.
 */
public interface BillingCompanyService {

    /**
     * Save a billingCompany.
     *
     * @param billingCompany the entity to save.
     * @return the persisted entity.
     */
    BillingCompany save(BillingCompany billingCompany);

    /**
     * Get all the billingCompanies.
     *
     * @return the list of entities.
     */
    List<BillingCompany> findAll();


    /**
     * Get the "id" billingCompany.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillingCompany> findOne(Long id);

    /**
     * Delete the "id" billingCompany.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
