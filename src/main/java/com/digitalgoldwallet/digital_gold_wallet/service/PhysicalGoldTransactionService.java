package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;

import java.util.List;

public interface PhysicalGoldTransactionService {

    /*
     * Create transaction
     */

    PhysicalGoldTransactionResponseDto
    createTransaction(
            PhysicalGoldTransactionRequestDto dto
    );

    /*
     * Get by id
     */

    PhysicalGoldTransactionResponseDto
    getById(
            Integer transactionId
    );

    /*
     * Get user history
     */

    List<PhysicalGoldTransactionResponseDto>
    getByUser(
            Integer userId
    );

}