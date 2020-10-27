package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.ProductTypeService;
import com.dhavalapp.com.domain.ProductType;
import com.dhavalapp.com.repository.ProductTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductType}.
 */
@Service
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService {

    private final Logger log = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Override
    public ProductType save(ProductType productType) {
        log.debug("Request to save ProductType : {}", productType);
        return productTypeRepository.save(productType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductType> findAll() {
        log.debug("Request to get all ProductTypes");
        return productTypeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProductType> findOne(Long id) {
        log.debug("Request to get ProductType : {}", id);
        return productTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductType : {}", id);
        productTypeRepository.deleteById(id);
    }
}
