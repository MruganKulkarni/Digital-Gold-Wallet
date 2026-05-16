package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;

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

    /*
     * Constructor Injection
     */
    public AddressServiceImpl(
            AddressRepository addressRepository) {

        this.addressRepository =
                addressRepository;
    }

    /*
     * Create Address
     */
    @Override
    public AddressResponseDto createAddress(
            AddressRequestDto requestDto) {

        Address address = new Address();

        address.setStreet(
                requestDto.getStreet()
        );

        address.setCity(
                requestDto.getCity()
        );

        address.setState(
                requestDto.getState()
        );

        address.setPostalCode(
                requestDto.getPostalCode()
        );

        address.setCountry(
                requestDto.getCountry()
        );

        Address savedAddress =
                addressRepository.save(address);

        return mapToResponse(savedAddress);
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

        return mapToResponse(address);
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

        existingAddress.setStreet(
                requestDto.getStreet()
        );

        existingAddress.setCity(
                requestDto.getCity()
        );

        existingAddress.setState(
                requestDto.getState()
        );

        existingAddress.setPostalCode(
                requestDto.getPostalCode()
        );

        existingAddress.setCountry(
                requestDto.getCountry()
        );

        Address updatedAddress =
                addressRepository.save(
                        existingAddress
                );

        return mapToResponse(updatedAddress);
    }

    /*
     * Entity -> DTO Mapper
     */
    private AddressResponseDto mapToResponse(
            Address address) {

        return new AddressResponseDto(
                address.getAddressId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );
    }
}