package com.digitalgoldwallet.digital_gold_wallet.controller;

// importing request DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;

// importing response DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

// importing service layer
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;

// validation annotation
import jakarta.validation.Valid;

// HTTP response wrapper
import org.springframework.http.ResponseEntity;

// REST annotations
import org.springframework.web.bind.annotation.*;

/*
 * ============================================================
 * Address REST Controller
 * ============================================================
 */

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    /*
     * Service injection
     */
    private final AddressService addressService;

    /*
     * Constructor injection
     */
    public AddressController(
            AddressService addressService) {

        this.addressService =
                addressService;
    }

    /*
     * ============================================================
     * CREATE ADDRESS API
     * ============================================================
     *
     * POST /api/v1/addresses
     */
    @PostMapping
    public ResponseEntity<AddressResponseDto>
    createAddress(
            @Valid
            @RequestBody
            AddressRequestDto requestDto) {

        return ResponseEntity.ok(
                addressService.createAddress(
                        requestDto
                )
        );
    }

    /*
     * ============================================================
     * GET ADDRESS BY ID API
     * ============================================================
     *
     * GET /api/v1/addresses/{addressId}
     */
    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto>
    getAddressById(
            @PathVariable
            Integer addressId) {

        return ResponseEntity.ok(
                addressService.getAddressById(
                        addressId
                )
        );
    }

    /*
     * ============================================================
     * UPDATE ADDRESS API
     * ============================================================
     *
     * PUT /api/v1/addresses/{addressId}
     */
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto>
    updateAddress(
            @PathVariable
            Integer addressId,

            @Valid
            @RequestBody
            AddressRequestDto requestDto) {

        return ResponseEntity.ok(
                addressService.updateAddress(
                        addressId,
                        requestDto
                )
        );
    }
}