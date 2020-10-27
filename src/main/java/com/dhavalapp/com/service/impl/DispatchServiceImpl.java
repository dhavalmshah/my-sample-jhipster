package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.DispatchService;
import com.dhavalapp.com.domain.Dispatch;
import com.dhavalapp.com.repository.DispatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Dispatch}.
 */
@Service
@Transactional
public class DispatchServiceImpl implements DispatchService {

    private final Logger log = LoggerFactory.getLogger(DispatchServiceImpl.class);

    private final DispatchRepository dispatchRepository;

    public DispatchServiceImpl(DispatchRepository dispatchRepository) {
        this.dispatchRepository = dispatchRepository;
    }

    @Override
    public Dispatch save(Dispatch dispatch) {
        log.debug("Request to save Dispatch : {}", dispatch);
        return dispatchRepository.save(dispatch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dispatch> findAll() {
        log.debug("Request to get all Dispatches");
        return dispatchRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Dispatch> findOne(Long id) {
        log.debug("Request to get Dispatch : {}", id);
        return dispatchRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dispatch : {}", id);
        dispatchRepository.deleteById(id);
    }
}
