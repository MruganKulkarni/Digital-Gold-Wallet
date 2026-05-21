package com.digitalgoldwallet.digital_gold_wallet.service; // package declaration for service interfaces

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO for vendor

import org.springframework.data.domain.Page; // used for paginated results
import org.springframework.data.domain.Pageable; // used for pagination parameters

import java.math.BigDecimal; // used for gold price return type
import java.util.List; // used for list of vendors

/*
 * Service interface for Vendor module
 * Defines all business operations for vendors
 * Implementation is in VendorServiceImpl
 */
public interface VendorService {

    /*
     * Create a new vendor
     * POST /api/v1/vendors
     */
    VendorResponseDto createVendor(VendorRequestDto vendorRequestDto); // creates vendor and returns response

    /*
     * Get vendor by ID
     * GET /api/v1/vendors/{vendorId}
     */
    VendorResponseDto getVendorById(Integer vendorId); // fetches vendor by ID and returns response

    /*
     * Get all vendors — no pagination
     * GET /api/v1/vendors
     */
    List<VendorResponseDto> getAllVendors(); // returns all vendors as a simple list — no pagination needed

    /*
     * Update vendor by ID
     * PUT /api/v1/vendors/{vendorId}
     */
    VendorResponseDto updateVendor(Integer vendorId, VendorRequestDto vendorRequestDto); // updates vendor and returns response

    /*
     * Get current gold price by vendor ID
     * GET /api/v1/vendors/{vendorId}/price
     */
    BigDecimal getGoldPriceByVendorId(Integer vendorId); // returns current gold price for vendor
}