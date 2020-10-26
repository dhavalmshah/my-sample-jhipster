package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.CounterParty;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CounterParty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CounterPartyRepository extends JpaRepository<CounterParty, Long> {
}
