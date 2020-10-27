package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.EmailAddressService;
import com.dhavalapp.com.domain.EmailAddress;
import com.dhavalapp.com.repository.EmailAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailAddress}.
 */
@Service
@Transactional
public class EmailAddressServiceImpl implements EmailAddressService {

    private final Logger log = LoggerFactory.getLogger(EmailAddressServiceImpl.class);

    private final EmailAddressRepository emailAddressRepository;

    public EmailAddressServiceImpl(EmailAddressRepository emailAddressRepository) {
        this.emailAddressRepository = emailAddressRepository;
    }

    @Override
    public EmailAddress save(EmailAddress emailAddress) {
        log.debug("Request to save EmailAddress : {}", emailAddress);
        return emailAddressRepository.save(emailAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmailAddress> findAll() {
        log.debug("Request to get all EmailAddresses");
        return emailAddressRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmailAddress> findOne(Long id) {
        log.debug("Request to get EmailAddress : {}", id);
        return emailAddressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailAddress : {}", id);
        emailAddressRepository.deleteById(id);
    }
}
