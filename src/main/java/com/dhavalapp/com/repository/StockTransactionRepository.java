package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.StockTransaction;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
}
