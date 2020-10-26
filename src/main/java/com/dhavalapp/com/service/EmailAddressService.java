package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.EmailAddress;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EmailAddress}.
 */
public interface EmailAddressService {

    /**
     * Save a emailAddress.
     *
     * @param emailAddress the entity to save.
     * @return the persisted entity.
     */
    EmailAddress save(EmailAddress emailAddress);

    /**
     * Get all the emailAddresses.
     *
     * @return the list of entities.
     */
    List<EmailAddress> findAll();


    /**
     * Get the "id" emailAddress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailAddress> findOne(Long id);

    /**
     * Delete the "id" emailAddress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
