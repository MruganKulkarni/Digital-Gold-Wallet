package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
 * Business logic implementation for Address module
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    // Repository injection
    private final AddressRepository addressRepository;

    /*
     * Create new address
     */
    @Override
    public AddressResponseDto createAddress(AddressRequestDto requestDto) {

        Address address = Address.builder()
                .street(requestDto.getStreet())
                .city(requestDto.getCity())
                .state(requestDto.getState())
                .postalCode(requestDto.getPostalCode())
                .country(requestDto.getCountry())
                .build();

        Address savedAddress = addressRepository.save(address);

        return mapToResponse(savedAddress);
    }

    /*
     * Fetch address by ID
     */
    @Override
    public AddressResponseDto getAddressById(Integer addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return mapToResponse(address);
    }

    /*
     * Update existing address
     */
    @Override
    public AddressResponseDto updateAddress(Integer addressId, AddressRequestDto requestDto) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setStreet(requestDto.getStreet());
        address.setCity(requestDto.getCity());
        address.setState(requestDto.getState());
        address.setPostalCode(requestDto.getPostalCode());
        address.setCountry(requestDto.getCountry());

        Address updatedAddress = addressRepository.save(address);

        return mapToResponse(updatedAddress);
    }

    /*
     * Convert entity into DTO
     */
    private AddressResponseDto mapToResponse(Address address) {

        return AddressResponseDto.builder()
                .addressId(address.getAddressId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }
}