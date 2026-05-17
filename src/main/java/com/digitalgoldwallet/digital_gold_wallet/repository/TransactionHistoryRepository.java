package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * ============================================================
 * Transaction History Repository
 * ============================================================
 *
 * Handles transaction history operations
 *
 * JpaRepository already provides:
 *
 * save()
 * findById()
 * findAll()
 * delete()
 *
 * Additional custom methods added below
 *
 * ============================================================
 */

public interface TransactionHistoryRepository
        extends JpaRepository<TransactionHistory,Integer> {


    /*
     * ============================================================
     * Get all transactions of a user
     *
     * Used by:
     * GET /api/v1/users/{userId}/transactions
     *
     * ============================================================
     */

    List<TransactionHistory>
    findByUserUserId(
            Integer userId
    );



    /*
     * ============================================================
     * Get all transactions of a branch
     *
     * Used by:
     * GET /api/v1/branches/{branchId}/transactions
     *
     * ============================================================
     */

    List<TransactionHistory>
    findByBranchBranchId(
            Integer branchId
    );

}