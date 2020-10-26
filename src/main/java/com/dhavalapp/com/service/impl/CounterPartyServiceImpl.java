package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.CounterPartyService;
import com.dhavalapp.com.domain.CounterParty;
import com.dhavalapp.com.repository.CounterPartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CounterParty}.
 */
@Service
@Transactional
public class CounterPartyServiceImpl implements CounterPartyService {

    private final Logger log = LoggerFactory.getLogger(CounterPartyServiceImpl.class);

    private final CounterPartyRepository counterPartyRepository;

    public CounterPartyServiceImpl(CounterPartyRepository counterPartyRepository) {
        this.counterPartyRepository = counterPartyRepository;
    }

    @Override
    public CounterParty save(CounterParty counterParty) {
        log.debug("Request to save CounterParty : {}", counterParty);
        return counterPartyRepository.save(counterParty);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CounterParty> findAll() {
        log.debug("Request to get all CounterParties");
        return counterPartyRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CounterParty> findOne(Long id) {
        log.debug("Request to get CounterParty : {}", id);
        return counterPartyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CounterParty : {}", id);
        counterPartyRepository.deleteById(id);
    }
}
