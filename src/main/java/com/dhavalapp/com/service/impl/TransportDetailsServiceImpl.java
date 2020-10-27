package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.TransportDetailsService;
import com.dhavalapp.com.domain.TransportDetails;
import com.dhavalapp.com.repository.TransportDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TransportDetails}.
 */
@Service
@Transactional
public class TransportDetailsServiceImpl implements TransportDetailsService {

    private final Logger log = LoggerFactory.getLogger(TransportDetailsServiceImpl.class);

    private final TransportDetailsRepository transportDetailsRepository;

    public TransportDetailsServiceImpl(TransportDetailsRepository transportDetailsRepository) {
        this.transportDetailsRepository = transportDetailsRepository;
    }

    @Override
    public TransportDetails save(TransportDetails transportDetails) {
        log.debug("Request to save TransportDetails : {}", transportDetails);
        return transportDetailsRepository.save(transportDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportDetails> findAll() {
        log.debug("Request to get all TransportDetails");
        return transportDetailsRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TransportDetails> findOne(Long id) {
        log.debug("Request to get TransportDetails : {}", id);
        return transportDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransportDetails : {}", id);
        transportDetailsRepository.deleteById(id);
    }
}
