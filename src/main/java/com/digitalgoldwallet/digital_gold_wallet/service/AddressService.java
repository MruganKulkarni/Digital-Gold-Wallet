package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

/*
 * ============================================================
 * Address Service Interface
 * ============================================================
 */

public interface AddressService {

    /*
     * Create address
     */
    AddressResponseDto createAddress(
            AddressRequestDto requestDto
    );

    /*
     * Get address by ID
     */
    AddressResponseDto getAddressById(
            Integer addressId
    );

    /*
     * Update address
     */
    AddressResponseDto updateAddress(
            Integer addressId,
            AddressRequestDto requestDto
    );
}