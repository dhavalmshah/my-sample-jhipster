package com.dhavalapp.com.service;

import com.dhavalapp.com.domain.ProductAlias;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductAlias}.
 */
public interface ProductAliasService {

    /**
     * Save a productAlias.
     *
     * @param productAlias the entity to save.
     * @return the persisted entity.
     */
    ProductAlias save(ProductAlias productAlias);

    /**
     * Get all the productAliases.
     *
     * @return the list of entities.
     */
    List<ProductAlias> findAll();


    /**
     * Get the "id" productAlias.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductAlias> findOne(Long id);

    /**
     * Delete the "id" productAlias.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
