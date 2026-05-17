package com.digitalgoldwallet.digital_gold_wallet.controller; // package declaration for controllers

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.service.VendorService; // service interface for vendor operations

import io.swagger.v3.oas.annotations.Operation; // swagger annotation for describing an endpoint
import io.swagger.v3.oas.annotations.tags.Tag; // swagger annotation for grouping endpoints

import jakarta.validation.Valid; // triggers @Valid validation on request body

import org.springframework.beans.factory.annotation.Autowired; // used for dependency injection
import org.springframework.data.domain.Page; // used for paginated response
import org.springframework.data.domain.PageRequest; // used to create pagination parameters
import org.springframework.http.HttpStatus; // used for HTTP status codes
import org.springframework.http.ResponseEntity; // used to return HTTP response with body and status
import org.springframework.web.bind.annotation.GetMapping; // maps GET requests
import org.springframework.web.bind.annotation.PathVariable; // binds URL path variable to method parameter
import org.springframework.web.bind.annotation.PostMapping; // maps POST requests
import org.springframework.web.bind.annotation.PutMapping; // maps PUT requests
import org.springframework.web.bind.annotation.RequestBody; // binds request body to method parameter
import org.springframework.web.bind.annotation.RequestMapping; // maps base URL for all endpoints in this controller
import org.springframework.web.bind.annotation.RequestParam; // binds query parameter to method parameter
import org.springframework.web.bind.annotation.RestController; // marks this class as a REST controller

import java.math.BigDecimal; // used for gold price response

/*
 * REST Controller for Vendor module
 * Handles all HTTP requests for vendor operations
 * Base URL: /api/v1/vendors
 */
@RestController // marks this as a REST controller — combines @Controller and @ResponseBody
@RequestMapping("/api/v1/vendors") // base URL for all vendor endpoints
@Tag(name = "Vendor Management", description = "APIs for managing gold vendors") // swagger tag groups all vendor endpoints
public class VendorController {

    private final VendorService vendorService; // service for vendor business logic

    @Autowired // injects VendorService automatically by Spring
    public VendorController(VendorService vendorService) { // constructor injection
        this.vendorService = vendorService; // assigns injected service
    }

    /*
     * POST /api/v1/vendors
     * Creates a new vendor
     * Returns 201 CREATED with created vendor data
     */
    @PostMapping // maps POST /api/v1/vendors
    @Operation(summary = "Create a new vendor", description = "Creates a new gold vendor in the system") // swagger description
    public ResponseEntity<VendorResponseDto> createVendor(
            @Valid @RequestBody VendorRequestDto vendorRequestDto) { // @Valid triggers validation — @RequestBody maps JSON to DTO

        VendorResponseDto response = vendorService.createVendor(vendorRequestDto); // calls service to create vendor

        return new ResponseEntity<>(response, HttpStatus.CREATED); // returns 201 CREATED with vendor response
    }

    /*
     * GET /api/v1/vendors/{vendorId}
     * Fetches a vendor by ID
     * Returns 200 OK with vendor data
     */
    @GetMapping("/{vendorId}") // maps GET /api/v1/vendors/{vendorId}
    @Operation(summary = "Get vendor by ID", description = "Fetches a gold vendor by their unique ID") // swagger description
    public ResponseEntity<VendorResponseDto> getVendorById(
            @PathVariable Integer vendorId) { // @PathVariable binds {vendorId} from URL to method parameter

        VendorResponseDto response = vendorService.getVendorById(vendorId); // calls service to fetch vendor by ID

        return new ResponseEntity<>(response, HttpStatus.OK); // returns 200 OK with vendor response
    }

    /*
     * GET /api/v1/vendors
     * Fetches all vendors with pagination
     * Returns 200 OK with paginated vendor list
     */
    @GetMapping // maps GET /api/v1/vendors
    @Operation(summary = "Get all vendors", description = "Fetches all gold vendors with pagination support") // swagger description
    public ResponseEntity<Page<VendorResponseDto>> getAllVendors(
            @RequestParam(defaultValue = "0") int page, // page number — defaults to 0 if not provided
            @RequestParam(defaultValue = "10") int size) { // page size — defaults to 10 if not provided

        Page<VendorResponseDto> response = vendorService.getAllVendors(PageRequest.of(page, size)); // calls service with pagination params

        return new ResponseEntity<>(response, HttpStatus.OK); // returns 200 OK with paginated vendor list
    }

    /*
     * PUT /api/v1/vendors/{vendorId}
     * Updates an existing vendor by ID
     * Returns 200 OK with updated vendor data
     */
    @PutMapping("/{vendorId}") // maps PUT /api/v1/vendors/{vendorId}
    @Operation(summary = "Update vendor", description = "Updates an existing gold vendor's details") // swagger description
    public ResponseEntity<VendorResponseDto> updateVendor(
            @PathVariable Integer vendorId, // @PathVariable binds {vendorId} from URL
            @Valid @RequestBody VendorRequestDto vendorRequestDto) { // @Valid triggers validation — @RequestBody maps JSON to DTO

        VendorResponseDto response = vendorService.updateVendor(vendorId, vendorRequestDto); // calls service to update vendor

        return new ResponseEntity<>(response, HttpStatus.OK); // returns 200 OK with updated vendor response
    }

    /*
     * GET /api/v1/vendors/{vendorId}/price
     * Fetches current gold price for a vendor
     * Returns 200 OK with gold price
     */
    @GetMapping("/{vendorId}/price") // maps GET /api/v1/vendors/{vendorId}/price
    @Operation(summary = "Get gold price by vendor", description = "Fetches the current gold price set by a specific vendor") // swagger description
    public ResponseEntity<BigDecimal> getGoldPriceByVendorId(
            @PathVariable Integer vendorId) { // @PathVariable binds {vendorId} from URL

        BigDecimal price = vendorService.getGoldPriceByVendorId(vendorId); // calls service to get gold price

        return new ResponseEntity<>(price, HttpStatus.OK); // returns 200 OK with gold price
    }
}