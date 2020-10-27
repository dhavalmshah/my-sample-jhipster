package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.PackingService;
import com.dhavalapp.com.domain.Packing;
import com.dhavalapp.com.repository.PackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Packing}.
 */
@Service
@Transactional
public class PackingServiceImpl implements PackingService {

    private final Logger log = LoggerFactory.getLogger(PackingServiceImpl.class);

    private final PackingRepository packingRepository;

    public PackingServiceImpl(PackingRepository packingRepository) {
        this.packingRepository = packingRepository;
    }

    @Override
    public Packing save(Packing packing) {
        log.debug("Request to save Packing : {}", packing);
        return packingRepository.save(packing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Packing> findAll() {
        log.debug("Request to get all Packings");
        return packingRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Packing> findOne(Long id) {
        log.debug("Request to get Packing : {}", id);
        return packingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Packing : {}", id);
        packingRepository.deleteById(id);
    }
}
