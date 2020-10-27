package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.EmailAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmailAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailAddressRepository extends JpaRepository<EmailAddress, Long> {
}
