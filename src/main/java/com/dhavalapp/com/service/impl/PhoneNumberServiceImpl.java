package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.PhoneNumberService;
import com.dhavalapp.com.domain.PhoneNumber;
import com.dhavalapp.com.repository.PhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PhoneNumber}.
 */
@Service
@Transactional
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final Logger log = LoggerFactory.getLogger(PhoneNumberServiceImpl.class);

    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public PhoneNumber save(PhoneNumber phoneNumber) {
        log.debug("Request to save PhoneNumber : {}", phoneNumber);
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PhoneNumber> findAll() {
        log.debug("Request to get all PhoneNumbers");
        return phoneNumberRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PhoneNumber> findOne(Long id) {
        log.debug("Request to get PhoneNumber : {}", id);
        return phoneNumberRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PhoneNumber : {}", id);
        phoneNumberRepository.deleteById(id);
    }
}
