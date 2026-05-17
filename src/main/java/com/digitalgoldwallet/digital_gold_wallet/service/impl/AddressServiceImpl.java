package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.mapper.AddressMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;

import org.springframework.stereotype.Service;

/*
 * ============================================================
 * Address Service Implementation
 * ============================================================
 */

@Service
public class AddressServiceImpl
        implements AddressService {

    /*
     * Repository Injection
     */
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    /*
     * Constructor Injection
     */
    public AddressServiceImpl(
            AddressRepository addressRepository,
            AddressMapper addressMapper) {

        this.addressRepository =
                addressRepository;

        this.addressMapper =
                addressMapper;
    }

    /*
     * Create Address
     */
    @Override
    public AddressResponseDto createAddress(
            AddressRequestDto requestDto) {

        /*
         * DTO -> Entity
         */
        Address address =
                addressMapper.toEntity(
                        requestDto
                );

        Address savedAddress =
                addressRepository.save(address);

        return addressMapper.toResponseDto(
                savedAddress
        );
    }

    /*
     * Get Address By ID
     */
    @Override
    public AddressResponseDto getAddressById(
            Integer addressId) {

        Address address =
                addressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new AddressNotFoundException(
                                        "Address not found"
                                ));

        return addressMapper.toResponseDto(
                address
        );
    }

    /*
     * Update Address
     */
    @Override
    public AddressResponseDto updateAddress(
            Integer addressId,
            AddressRequestDto requestDto) {

        Address existingAddress =
                addressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new AddressNotFoundException(
                                        "Address not found"
                                ));

        /*
         * Update entity using mapper
         */
        addressMapper.updateEntity(
                existingAddress,
                requestDto
        );

        Address updatedAddress =
                addressRepository.save(
                        existingAddress
                );

        return addressMapper.toResponseDto(
                updatedAddress
        );
    }
}