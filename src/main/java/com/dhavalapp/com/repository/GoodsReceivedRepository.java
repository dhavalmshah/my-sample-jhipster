package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.GoodsReceived;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GoodsReceived entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodsReceivedRepository extends JpaRepository<GoodsReceived, Long> {
}
