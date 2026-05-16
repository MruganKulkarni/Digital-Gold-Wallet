package com.digitalgoldwallet.digital_gold_wallet.mapper; // package declaration for mapper classes

import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO — used to build response from entity
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // VendorBranch entity — the JPA entity mapped to vendor_branches table

/*
 * Mapper class for VendorBranch module
 * Handles all conversions between VendorBranch entity and its DTOs
 * All methods are static — no need to instantiate this class
 * Keeps service layer clean by removing mapping logic from VendorBranchServiceImpl
 *
 * NOTE: toEntity() is NOT included here because VendorBranch entity requires
 * full Vendor and Address objects (not just IDs) — those lookups happen in
 * VendorBranchServiceImpl where repositories are available
 */
public class VendorBranchMapper { // no @Component — used as a utility class with static methods only

    /*
     * Converts VendorBranch entity to VendorBranchResponseDto
     * Used in all GET responses in VendorBranchServiceImpl
     * Requires @Transactional on service — Vendor and Address are lazy loaded
     * Never exposes the entity directly — always returns DTO
     */
    public static VendorBranchResponseDto toResponseDto(VendorBranch branch) { // accepts VendorBranch entity, returns response DTO

        VendorBranchResponseDto dto = new VendorBranchResponseDto(); // creates new empty response DTO object
        dto.setBranchId(branch.getBranchId()); // sets branch id from entity
        dto.setVendorId(branch.getVendor().getVendorId()); // sets vendor id from lazy-loaded vendor — safe inside @Transactional
        dto.setVendorName(branch.getVendor().getVendorName()); // sets vendor name from lazy-loaded vendor — safe inside @Transactional
        dto.setAddressId(branch.getAddress().getAddressId()); // sets address id from lazy-loaded address — safe inside @Transactional
        dto.setCity(branch.getAddress().getCity()); // sets city from lazy-loaded address — safe inside @Transactional
        dto.setState(branch.getAddress().getState()); // sets state from lazy-loaded address — safe inside @Transactional
        dto.setQuantity(branch.getQuantity()); // sets gold quantity from entity
        dto.setCreatedAt(branch.getCreatedAt()); // sets creation timestamp from entity

        return dto; // returns fully populated response DTO
    }
}