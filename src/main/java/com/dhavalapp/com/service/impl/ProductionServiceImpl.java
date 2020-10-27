package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.ProductionService;
import com.dhavalapp.com.domain.Production;
import com.dhavalapp.com.repository.ProductionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Production}.
 */
@Service
@Transactional
public class ProductionServiceImpl implements ProductionService {

    private final Logger log = LoggerFactory.getLogger(ProductionServiceImpl.class);

    private final ProductionRepository productionRepository;

    public ProductionServiceImpl(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    @Override
    public Production save(Production production) {
        log.debug("Request to save Production : {}", production);
        return productionRepository.save(production);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Production> findAll() {
        log.debug("Request to get all Productions");
        return productionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Production> findOne(Long id) {
        log.debug("Request to get Production : {}", id);
        return productionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Production : {}", id);
        productionRepository.deleteById(id);
    }
}
