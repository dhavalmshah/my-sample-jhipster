package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.BillingLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillingLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingLocationRepository extends JpaRepository<BillingLocation, Long> {
}
