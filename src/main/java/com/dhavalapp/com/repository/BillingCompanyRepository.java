package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.BillingCompany;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillingCompany entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingCompanyRepository extends JpaRepository<BillingCompany, Long> {
}
