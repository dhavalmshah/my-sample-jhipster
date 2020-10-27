package com.dhavalapp.com.service.impl;

import com.dhavalapp.com.service.StockTransactionService;
import com.dhavalapp.com.domain.StockTransaction;
import com.dhavalapp.com.repository.StockTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StockTransaction}.
 */
@Service
@Transactional
public class StockTransactionServiceImpl implements StockTransactionService {

    private final Logger log = LoggerFactory.getLogger(StockTransactionServiceImpl.class);

    private final StockTransactionRepository stockTransactionRepository;

    public StockTransactionServiceImpl(StockTransactionRepository stockTransactionRepository) {
        this.stockTransactionRepository = stockTransactionRepository;
    }

    @Override
    public StockTransaction save(StockTransaction stockTransaction) {
        log.debug("Request to save StockTransaction : {}", stockTransaction);
        return stockTransactionRepository.save(stockTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockTransaction> findAll() {
        log.debug("Request to get all StockTransactions");
        return stockTransactionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StockTransaction> findOne(Long id) {
        log.debug("Request to get StockTransaction : {}", id);
        return stockTransactionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockTransaction : {}", id);
        stockTransactionRepository.deleteById(id);
    }
}
