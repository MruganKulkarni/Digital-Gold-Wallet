package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VirtualGoldHoldingRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VirtualGoldHoldingResponseDto;

import java.util.List;

/*
 * ============================================================
 * Virtual Gold Holding Service
 * ============================================================
 *
 * Handles:
 *
 * - Buy virtual gold
 * - Sell virtual gold
 * - Fetch by id
 * - User holdings
 * - Branch holdings
 *
 * ============================================================
 */

public interface VirtualGoldHoldingService {


    /*
     * ============================================================
     * Buy virtual gold
     *
     * API:
     * POST /api/v1/gold/virtual/buy
     *
     * ============================================================
     */

    VirtualGoldHoldingResponseDto
    buyGold(
            VirtualGoldHoldingRequestDto dto
    );



    /*
     * ============================================================
     * Sell virtual gold
     *
     * API:
     * POST /api/v1/gold/virtual/sell
     *
     * ============================================================
     */

    VirtualGoldHoldingResponseDto
    sellGold(
            VirtualGoldHoldingRequestDto dto
    );



    /*
     * ============================================================
     * Existing method from Day 3
     * ============================================================
     */

    VirtualGoldHoldingResponseDto
    getHoldingById(
            Integer id
    );



    /*
     * ============================================================
     * Get holdings of user
     *
     * GET:
     * /api/v1/users/{userId}/gold/virtual
     *
     * ============================================================
     */

    List<VirtualGoldHoldingResponseDto>
    getHoldingsByUser(
            Integer userId
    );



    /*
     * ============================================================
     * Get holdings of branch
     *
     * GET:
     * /api/v1/branches/{branchId}/gold/virtual
     *
     * ============================================================
     */

    List<VirtualGoldHoldingResponseDto>
    getHoldingsByBranch(
            Integer branchId
    );

}