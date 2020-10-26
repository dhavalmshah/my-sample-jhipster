package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.ProductAliasService;
import com.dhavalapp.com.domain.ProductAlias;
import com.dhavalapp.com.repository.ProductAliasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductAlias}.
 */
@Service
@Transactional
public class ProductAliasServiceImpl implements ProductAliasService {

    private final Logger log = LoggerFactory.getLogger(ProductAliasServiceImpl.class);

    private final ProductAliasRepository productAliasRepository;

    public ProductAliasServiceImpl(ProductAliasRepository productAliasRepository) {
        this.productAliasRepository = productAliasRepository;
    }

    @Override
    public ProductAlias save(ProductAlias productAlias) {
        log.debug("Request to save ProductAlias : {}", productAlias);
        return productAliasRepository.save(productAlias);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAlias> findAll() {
        log.debug("Request to get all ProductAliases");
        return productAliasRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAlias> findOne(Long id) {
        log.debug("Request to get ProductAlias : {}", id);
        return productAliasRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductAlias : {}", id);
        productAliasRepository.deleteById(id);
    }
}
