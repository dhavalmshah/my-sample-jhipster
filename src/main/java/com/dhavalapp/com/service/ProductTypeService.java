package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.ProductType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductType}.
 */
public interface ProductTypeService {

    /**
     * Save a productType.
     *
     * @param productType the entity to save.
     * @return the persisted entity.
     */
    ProductType save(ProductType productType);

    /**
     * Get all the productTypes.
     *
     * @return the list of entities.
     */
    List<ProductType> findAll();


    /**
     * Get the "id" productType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductType> findOne(Long id);

    /**
     * Delete the "id" productType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
