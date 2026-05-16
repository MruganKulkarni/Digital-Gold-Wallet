package com.digitalgoldwallet.digital_gold_wallet.service; // package declaration for service interfaces

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto; // request DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO for vendor branch

import java.math.BigDecimal; // used for inventory quantity return type
import java.util.List; // used for list of branches

/*
 * Service interface for VendorBranch module
 * Defines all business operations for vendor branches
 * Implementation is in VendorBranchServiceImpl
 */
public interface VendorBranchService {

    /*
     * Add a new branch to a vendor
     * POST /api/v1/vendors/{vendorId}/branches
     */
    VendorBranchResponseDto addBranch(Integer vendorId, VendorBranchRequestDto vendorBranchRequestDto); // creates branch and returns response

    /*
     * Get branch by ID
     * GET /api/v1/branches/{branchId}
     */
    VendorBranchResponseDto getBranchById(Integer branchId); // fetches branch by ID and returns response

    /*
     * Get all branches of a vendor
     * GET /api/v1/vendors/{vendorId}/branches
     */
    List<VendorBranchResponseDto> getBranchesByVendorId(Integer vendorId); // returns list of branches for a vendor

    /*
     * Get gold inventory quantity at a branch
     * GET /api/v1/branches/{branchId}/inventory
     */
    BigDecimal getInventoryByBranchId(Integer branchId); // returns gold quantity at branch
}