package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.PhoneNumber;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PhoneNumber}.
 */
public interface PhoneNumberService {

    /**
     * Save a phoneNumber.
     *
     * @param phoneNumber the entity to save.
     * @return the persisted entity.
     */
    PhoneNumber save(PhoneNumber phoneNumber);

    /**
     * Get all the phoneNumbers.
     *
     * @return the list of entities.
     */
    List<PhoneNumber> findAll();


    /**
     * Get the "id" phoneNumber.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PhoneNumber> findOne(Long id);

    /**
     * Delete the "id" phoneNumber.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
