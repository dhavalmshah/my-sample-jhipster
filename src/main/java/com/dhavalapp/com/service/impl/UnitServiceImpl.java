package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.UnitService;
import com.dhavalapp.com.domain.Unit;
import com.dhavalapp.com.repository.UnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Unit}.
 */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);

    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public Unit save(Unit unit) {
        log.debug("Request to save Unit : {}", unit);
        return unitRepository.save(unit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Unit> findAll() {
        log.debug("Request to get all Units");
        return unitRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Unit> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
