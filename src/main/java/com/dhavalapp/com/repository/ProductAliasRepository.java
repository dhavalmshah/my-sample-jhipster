package com.dhavalapp.com.repository;

import com.dhavalapp.com.domain.ProductAlias;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductAlias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAliasRepository extends JpaRepository<ProductAlias, Long> {
}
