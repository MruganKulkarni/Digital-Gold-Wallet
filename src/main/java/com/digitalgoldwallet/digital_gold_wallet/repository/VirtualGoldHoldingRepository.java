package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * ============================================================
 * Virtual Gold Holding Repository
 * ============================================================
 *
 * Handles database operations for:
 * - Buy virtual gold
 * - Sell virtual gold
 * - Fetch user holdings
 * - Fetch branch holdings
 *
 * JpaRepository already provides:
 *
 * save()
 * findById()
 * findAll()
 * delete()
 *
 * Additional custom queries added below
 *
 * ============================================================
 */

public interface VirtualGoldHoldingRepository
        extends JpaRepository<VirtualGoldHolding, Integer> {

    /*
     * ============================================================
     * Get all holdings of a user
     *
     * Used by:
     * GET /api/v1/users/{userId}/gold/virtual
     *
     * Spring generates query automatically:
     *
     * select *
     * from virtual_gold_holdings
     * where user_id=?
     *
     * ============================================================
     */

    List<VirtualGoldHolding>
    findByUserUserId(
            Integer userId
    );



    /*
     * ============================================================
     * Get all holdings of a branch
     *
     * Used by:
     * GET /api/v1/branches/{branchId}/gold/virtual
     *
     * Spring generates query automatically:
     *
     * select *
     * from virtual_gold_holdings
     * where branch_id=?
     *
     * ============================================================
     */

    List<VirtualGoldHolding>
    findByBranchBranchId(
            Integer branchId
    );

}