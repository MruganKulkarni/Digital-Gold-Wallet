package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

/*
 * Service interface for Address module
 */
public interface AddressService {

    AddressResponseDto createAddress(AddressRequestDto requestDto);

    AddressResponseDto getAddressById(Integer addressId);

    AddressResponseDto updateAddress(Integer addressId, AddressRequestDto requestDto);
}

