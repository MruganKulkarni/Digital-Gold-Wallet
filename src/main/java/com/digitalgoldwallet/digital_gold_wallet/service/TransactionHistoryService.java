package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.TransactionHistoryRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;

import java.util.List;

/*
 * ============================================================
 * Transaction History Service
 * ============================================================
 */

public interface TransactionHistoryService {


    /*
     * Existing Day-3 create method
     */

    TransactionHistoryResponseDto
    createTransaction(
            TransactionHistoryRequestDto dto
    );



    /*
     * Get all user transactions
     *
     * GET:
     * /api/v1/users/{userId}/transactions
     */

    List<TransactionHistoryResponseDto>
    getTransactionsByUser(
            Integer userId
    );



    /*
     * Get branch transactions
     *
     * GET:
     * /api/v1/branches/{branchId}/transactions
     */

    List<TransactionHistoryResponseDto>
    getTransactionsByBranch(
            Integer branchId
    );

}