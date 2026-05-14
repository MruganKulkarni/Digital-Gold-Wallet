package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 * REST Controller for Address APIs
 */
@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    // Injecting service layer
    private final AddressService addressService;

    /*
     * Create address
     * POST /api/v1/addresses
     */
    @PostMapping
    public AddressResponseDto createAddress(
            @Valid @RequestBody AddressRequestDto requestDto
    ) {
        return addressService.createAddress(requestDto);
    }

    /*
     * Get address details
     * GET /api/v1/addresses/{addressId}
     */
    @GetMapping("/{addressId}")
    public AddressResponseDto getAddressById(
            @PathVariable Integer addressId
    ) {
        return addressService.getAddressById(addressId);
    }

    /*
     * Update address
     * PUT /api/v1/addresses/{addressId}
     */
    @PutMapping("/{addressId}")
    public AddressResponseDto updateAddress(
            @PathVariable Integer addressId,
            @Valid @RequestBody AddressRequestDto requestDto
    ) {
        return addressService.updateAddress(addressId, requestDto);
    }
}