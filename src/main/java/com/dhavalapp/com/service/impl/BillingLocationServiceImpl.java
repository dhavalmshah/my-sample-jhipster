package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.BillingLocationService;
import com.dhavalapp.com.domain.BillingLocation;
import com.dhavalapp.com.repository.BillingLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BillingLocation}.
 */
@Service
@Transactional
public class BillingLocationServiceImpl implements BillingLocationService {

    private final Logger log = LoggerFactory.getLogger(BillingLocationServiceImpl.class);

    private final BillingLocationRepository billingLocationRepository;

    public BillingLocationServiceImpl(BillingLocationRepository billingLocationRepository) {
        this.billingLocationRepository = billingLocationRepository;
    }

    @Override
    public BillingLocation save(BillingLocation billingLocation) {
        log.debug("Request to save BillingLocation : {}", billingLocation);
        return billingLocationRepository.save(billingLocation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillingLocation> findAll() {
        log.debug("Request to get all BillingLocations");
        return billingLocationRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BillingLocation> findOne(Long id) {
        log.debug("Request to get BillingLocation : {}", id);
        return billingLocationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingLocation : {}", id);
        billingLocationRepository.deleteById(id);
    }
}
