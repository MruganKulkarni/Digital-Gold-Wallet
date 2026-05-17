package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VirtualGoldHoldingRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VirtualGoldHoldingResponseDto;

import java.util.List;

public interface VirtualGoldHoldingService {

    /*
     * Create Holding
     */

    VirtualGoldHoldingResponseDto
    createHolding(
            VirtualGoldHoldingRequestDto dto
    );

    /*
     * Get By Id
     */

    VirtualGoldHoldingResponseDto
    getHoldingById(
            Integer holdingId
    );

    /*
     * Get user holdings
     */

    List<VirtualGoldHoldingResponseDto>
    getHoldingsByUser(
            Integer userId
    );

}