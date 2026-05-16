package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * ============================================================
 * Repository for VirtualGoldHolding Entity
 * ============================================================
 *
 * Provides CRUD operations and custom finder methods
 *
 * ============================================================
 */

@Repository
public interface VirtualGoldHoldingRepository
        extends JpaRepository<
        VirtualGoldHolding,
        Integer> {

    /*
     * Get all holdings of a user
     */
    List<VirtualGoldHolding>
    findByUserUserId(
            Integer userId
    );

}