package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.VendorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/*
 * REST Controller for Vendor module
 * Handles all HTTP requests for vendor operations
 * Base URL: /api/v1/vendors
 *
 * Validation:
 * - Request body validation using @Valid
 * - Path variable validation using @Min
 * - Pagination validation
 */

@RestController

@Validated

@RequestMapping("/api/v1/vendors")

@Tag(
        name = "Vendor Management",
        description = "APIs for managing gold vendors"
)

public class VendorController {

    private final VendorService vendorService;


    @Autowired
    public VendorController(
            VendorService vendorService
    ) {

        this.vendorService =
                vendorService;

    }


    /*
     * ============================================================
     * CREATE VENDOR
     * ============================================================
     *
     * POST /api/v1/vendors
     */

    @PostMapping

    @Operation(
            summary="Create a new vendor",
            description=
                    "Creates a new gold vendor in the system"
    )

    public ResponseEntity<VendorResponseDto>
    createVendor(

            @Valid

            @RequestBody

            VendorRequestDto vendorRequestDto
    ) {

        VendorResponseDto response =

                vendorService.createVendor(
                        vendorRequestDto
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );

    }


    /*
     * ============================================================
     * GET VENDOR BY ID
     * ============================================================
     *
     * GET /api/v1/vendors/{vendorId}
     */

    @GetMapping("/{vendorId}")

    @Operation(
            summary="Get vendor by ID",
            description=
                    "Fetches a gold vendor by their unique ID"
    )

    public ResponseEntity<VendorResponseDto>
    getVendorById(

            @PathVariable

            @Min(
                    value=1,
                    message="Vendor ID must be positive"
            )

            Integer vendorId
    ) {

        VendorResponseDto response =

                vendorService.getVendorById(
                        vendorId
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );

    }



    /*
     * ============================================================
     * GET ALL VENDORS
     * ============================================================
     *
     * GET /api/v1/vendors
     */

    @GetMapping

    @Operation(
            summary="Get all vendors",
            description=
                    "Fetches all gold vendors with pagination support"
    )

    public ResponseEntity<Page<VendorResponseDto>>
    getAllVendors(

            @RequestParam(
                    defaultValue="0"
            )

            @Min(
                    value=0,
                    message=
                            "Page cannot be negative"
            )

            int page,

            @RequestParam(
                    defaultValue="10"
            )

            @Min(
                    value=1,
                    message=
                            "Size must be positive"
            )

            int size
    ) {

        Page<VendorResponseDto> response =

                vendorService.getAllVendors(
                        PageRequest.of(
                                page,
                                size
                        )
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );

    }



    /*
     * ============================================================
     * UPDATE VENDOR
     * ============================================================
     *
     * PUT /api/v1/vendors/{vendorId}
     */

    @PutMapping("/{vendorId}")

    @Operation(
            summary="Update vendor",
            description=
                    "Updates an existing gold vendor"
    )

    public ResponseEntity<VendorResponseDto>
    updateVendor(

            @PathVariable

            @Min(
                    value=1,
                    message="Vendor ID must be positive"
            )

            Integer vendorId,

            @Valid

            @RequestBody

            VendorRequestDto vendorRequestDto
    ) {

        VendorResponseDto response =

                vendorService.updateVendor(
                        vendorId,
                        vendorRequestDto
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );

    }



    /*
     * ============================================================
     * GET GOLD PRICE
     * ============================================================
     *
     * GET /api/v1/vendors/{vendorId}/price
     */

    @GetMapping("/{vendorId}/price")

    @Operation(
            summary="Get gold price by vendor",
            description=
                    "Fetches current gold price"
    )

    public ResponseEntity<BigDecimal>
    getGoldPriceByVendorId(

            @PathVariable

            @Min(
                    value=1,
                    message="Vendor ID must be positive"
            )

            Integer vendorId
    ) {

        BigDecimal price =

                vendorService
                        .getGoldPriceByVendorId(
                                vendorId
                        );

        return new ResponseEntity<>(
                price,
                HttpStatus.OK
        );

    }

}