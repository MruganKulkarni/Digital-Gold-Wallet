package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import org.springframework.stereotype.Component;

/*
 * ============================================================
 * Address Mapper
 * ============================================================
 *
 * Handles conversion between:
 *
 * DTO <-> Entity
 * ============================================================
 */

@Component
public class AddressMapper {

    /*
     * Request DTO -> Entity
     */
    public Address toEntity(
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

        return address;
    }

    /*
     * Entity -> Response DTO
     */
    public AddressResponseDto toResponseDto(
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

    /*
     * Update Existing Entity
     */
    public void updateEntity(
            Address existingAddress,
            AddressRequestDto requestDto) {

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
    }
}