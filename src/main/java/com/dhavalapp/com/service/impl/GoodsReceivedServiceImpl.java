package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.GoodsReceivedService;
import com.dhavalapp.com.domain.GoodsReceived;
import com.dhavalapp.com.repository.GoodsReceivedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GoodsReceived}.
 */
@Service
@Transactional
public class GoodsReceivedServiceImpl implements GoodsReceivedService {

    private final Logger log = LoggerFactory.getLogger(GoodsReceivedServiceImpl.class);

    private final GoodsReceivedRepository goodsReceivedRepository;

    public GoodsReceivedServiceImpl(GoodsReceivedRepository goodsReceivedRepository) {
        this.goodsReceivedRepository = goodsReceivedRepository;
    }

    @Override
    public GoodsReceived save(GoodsReceived goodsReceived) {
        log.debug("Request to save GoodsReceived : {}", goodsReceived);
        return goodsReceivedRepository.save(goodsReceived);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoodsReceived> findAll() {
        log.debug("Request to get all GoodsReceiveds");
        return goodsReceivedRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GoodsReceived> findOne(Long id) {
        log.debug("Request to get GoodsReceived : {}", id);
        return goodsReceivedRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoodsReceived : {}", id);
        goodsReceivedRepository.deleteById(id);
    }
}
