package com.digitalgoldwallet.digital_gold_wallet.controller;

// importing request DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;

// importing response DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

// importing service layer
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;

// validation annotations
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

// enables path variable validation
import org.springframework.validation.annotation.Validated;

// HTTP response wrapper
import org.springframework.http.ResponseEntity;

// REST annotations
import org.springframework.web.bind.annotation.*;

/*
 * ============================================================
 * Address REST Controller
 * ============================================================
 *
 * Handles all HTTP requests related to Address module.
 *
 * Base URL:
 * /api/v1/addresses
 *
 * Validation:
 * - Request body validation using @Valid
 * - Path variable validation using @Min
 *
 * ============================================================
 */

@RestController

@Validated

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
            AddressService addressService
    ) {

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

            AddressRequestDto requestDto
    ) {

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

            @Min(
                    value=1,
                    message="Address ID must be positive"
            )

            Integer addressId
    ) {

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

            @Min(
                    value=1,
                    message="Address ID must be positive"
            )

            Integer addressId,

            @Valid

            @RequestBody

            AddressRequestDto requestDto
    ) {

        return ResponseEntity.ok(

                addressService.updateAddress(
                        addressId,
                        requestDto
                )

        );

    }

}