package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.TransactionHistoryRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;

import java.util.List;

public interface TransactionHistoryService {

    /*
     * Create transaction
     */

    TransactionHistoryResponseDto
    createTransaction(
            TransactionHistoryRequestDto dto
    );

    /*
     * Get transaction by ID
     */

    TransactionHistoryResponseDto
    getTransactionById(
            Integer transactionId
    );

    /*
     * Get user transactions
     */

    List<TransactionHistoryResponseDto>
    getTransactionsByUser(
            Integer userId
    );

}