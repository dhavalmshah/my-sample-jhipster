package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.Dispatch;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Dispatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {
}
