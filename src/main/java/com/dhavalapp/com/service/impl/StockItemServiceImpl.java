package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.StockItemService;
import com.dhavalapp.com.domain.StockItem;
import com.dhavalapp.com.repository.StockItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StockItem}.
 */
@Service
@Transactional
public class StockItemServiceImpl implements StockItemService {

    private final Logger log = LoggerFactory.getLogger(StockItemServiceImpl.class);

    private final StockItemRepository stockItemRepository;

    public StockItemServiceImpl(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    @Override
    public StockItem save(StockItem stockItem) {
        log.debug("Request to save StockItem : {}", stockItem);
        return stockItemRepository.save(stockItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockItem> findAll() {
        log.debug("Request to get all StockItems");
        return stockItemRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StockItem> findOne(Long id) {
        log.debug("Request to get StockItem : {}", id);
        return stockItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockItem : {}", id);
        stockItemRepository.deleteById(id);
    }
}
