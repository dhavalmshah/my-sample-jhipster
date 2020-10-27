package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.BillingCompanyService;
import com.dhavalapp.com.domain.BillingCompany;
import com.dhavalapp.com.repository.BillingCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BillingCompany}.
 */
@Service
@Transactional
public class BillingCompanyServiceImpl implements BillingCompanyService {

    private final Logger log = LoggerFactory.getLogger(BillingCompanyServiceImpl.class);

    private final BillingCompanyRepository billingCompanyRepository;

    public BillingCompanyServiceImpl(BillingCompanyRepository billingCompanyRepository) {
        this.billingCompanyRepository = billingCompanyRepository;
    }

    @Override
    public BillingCompany save(BillingCompany billingCompany) {
        log.debug("Request to save BillingCompany : {}", billingCompany);
        return billingCompanyRepository.save(billingCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillingCompany> findAll() {
        log.debug("Request to get all BillingCompanies");
        return billingCompanyRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BillingCompany> findOne(Long id) {
        log.debug("Request to get BillingCompany : {}", id);
        return billingCompanyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingCompany : {}", id);
        billingCompanyRepository.deleteById(id);
    }
}
