package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * ============================================================
 * Repository for PhysicalGoldTransaction Entity
 * ============================================================
 *
 * Provides CRUD operations and custom finder methods
 *
 * ============================================================
 */

@Repository
public interface PhysicalGoldTransactionRepository
        extends JpaRepository<
        PhysicalGoldTransaction,
        Integer> {

    /*
     * Get all physical transactions
     * of a user
     */
    List<PhysicalGoldTransaction>
    findByUserUserId(
            Integer userId
    );

}