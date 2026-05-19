package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // for custom JPQL query
import org.springframework.data.repository.query.Param; // for binding method params to query params

import java.math.BigDecimal; // for sum return type
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
    List<VirtualGoldHolding> findByUserUserId(
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
    List<VirtualGoldHolding> findByBranchBranchId(
            Integer branchId
    );

    /*
     * ============================================================
     * Sum total virtual gold holdings for a user at a specific branch
     *
     * Used by:
     * POST /api/v1/gold/physical/convert
     * to check if user has enough gold before converting
     *
     * JPQL: sums all quantities for userId + branchId combination
     * COALESCE returns 0 if no holdings found instead of null
     *
     * ============================================================
     */
    @Query("SELECT COALESCE(SUM(h.quantity), 0) FROM VirtualGoldHolding h WHERE h.user.userId = :userId AND h.branch.branchId = :branchId")
    BigDecimal sumQuantityByUserIdAndBranchId(
            @Param("userId") Integer userId, // binds userId param to query
            @Param("branchId") Integer branchId // binds branchId param to query
    );
}