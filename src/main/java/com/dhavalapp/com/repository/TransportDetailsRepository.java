package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.TransportDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransportDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransportDetailsRepository extends JpaRepository<TransportDetails, Long> {
}
