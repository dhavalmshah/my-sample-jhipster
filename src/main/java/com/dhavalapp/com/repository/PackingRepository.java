package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.Packing;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Packing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackingRepository extends JpaRepository<Packing, Long> {
}
