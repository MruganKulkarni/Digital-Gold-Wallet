package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;

import java.util.List;

/*
 * ============================================================
 * Physical Gold Service
 * ============================================================
 */

public interface
PhysicalGoldTransactionService {


    /*
     * ============================================================
     * Convert virtual gold to physical gold
     *
     * API:
     * POST /api/v1/gold/physical/convert
     *
     * ============================================================
     */

    PhysicalGoldTransactionResponseDto
    convertToPhysical(
            PhysicalGoldTransactionRequestDto dto
    );



    /*
     * Existing Day-3 create
     */

    PhysicalGoldTransactionResponseDto
    createTransaction(
            PhysicalGoldTransactionRequestDto dto
    );



    /*
     * ============================================================
     * User physical orders
     *
     * GET:
     * /api/v1/users/{userId}/gold/physical
     *
     * ============================================================
     */

    List<PhysicalGoldTransactionResponseDto>
    getByUser(
            Integer userId
    );



    /*
     * ============================================================
     * Delivery details
     *
     * GET:
     * /api/v1/physical-transactions/{transactionId}
     *
     * ============================================================
     */

    PhysicalGoldTransactionResponseDto
    getById(
            Integer id
    );

}