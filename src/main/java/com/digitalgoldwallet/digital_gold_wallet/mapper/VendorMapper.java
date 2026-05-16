package com.digitalgoldwallet.digital_gold_wallet.mapper; // package declaration for mapper classes

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO — used to build entity from incoming data
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO — used to build response from entity
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // Vendor entity — the JPA entity mapped to vendors table

/*
 * Mapper class for Vendor module
 * Handles all conversions between Vendor entity and its DTOs
 * All methods are static — no need to instantiate this class
 * Keeps service layer clean by removing mapping logic from VendorServiceImpl
 */
public class VendorMapper { // no @Component — used as a utility class with static methods only

    /*
     * Converts VendorRequestDto to Vendor entity
     * Used in createVendor and updateVendor in VendorServiceImpl
     * Does NOT set vendorId or createdAt — those are managed by JPA
     */
    public static Vendor toEntity(VendorRequestDto dto) { // accepts request DTO, returns Vendor entity

        Vendor vendor = new Vendor(); // creates new empty Vendor entity object
        vendor.setVendorName(dto.getVendorName()); // sets vendor name from DTO
        vendor.setDescription(dto.getDescription()); // sets description from DTO
        vendor.setContactPersonName(dto.getContactPersonName()); // sets contact person name from DTO
        vendor.setContactEmail(dto.getContactEmail()); // sets contact email from DTO
        vendor.setContactPhone(dto.getContactPhone()); // sets contact phone from DTO
        vendor.setWebsiteUrl(dto.getWebsiteUrl()); // sets website url from DTO
        vendor.setTotalGoldQuantity(dto.getTotalGoldQuantity()); // sets total gold quantity from DTO
        vendor.setCurrentGoldPrice(dto.getCurrentGoldPrice()); // sets current gold price from DTO

        return vendor; // returns fully populated Vendor entity
    }

    /*
     * Converts Vendor entity to VendorResponseDto
     * Used in all GET responses in VendorServiceImpl
     * Never exposes the entity directly — always returns DTO
     */
    public static VendorResponseDto toResponseDto(Vendor vendor) { // accepts Vendor entity, returns response DTO

        VendorResponseDto dto = new VendorResponseDto(); // creates new empty response DTO object
        dto.setVendorId(vendor.getVendorId()); // sets vendor id from entity
        dto.setVendorName(vendor.getVendorName()); // sets vendor name from entity
        dto.setDescription(vendor.getDescription()); // sets description from entity
        dto.setContactPersonName(vendor.getContactPersonName()); // sets contact person name from entity
        dto.setContactEmail(vendor.getContactEmail()); // sets contact email from entity
        dto.setContactPhone(vendor.getContactPhone()); // sets contact phone from entity
        dto.setWebsiteUrl(vendor.getWebsiteUrl()); // sets website url from entity
        dto.setTotalGoldQuantity(vendor.getTotalGoldQuantity()); // sets total gold quantity from entity
        dto.setCurrentGoldPrice(vendor.getCurrentGoldPrice()); // sets current gold price from entity
        dto.setCreatedAt(vendor.getCreatedAt()); // sets creation timestamp from entity

        return dto; // returns fully populated response DTO
    }

    /*
     * Updates existing Vendor entity fields from VendorRequestDto
     * Used in updateVendor in VendorServiceImpl
     * Does NOT create a new entity — updates the existing one to preserve vendorId and createdAt
     */
    public static void updateEntityFromDto(VendorRequestDto dto, Vendor vendor) { // accepts DTO and existing entity

        vendor.setVendorName(dto.getVendorName()); // updates vendor name
        vendor.setDescription(dto.getDescription()); // updates description
        vendor.setContactPersonName(dto.getContactPersonName()); // updates contact person name
        vendor.setContactEmail(dto.getContactEmail()); // updates contact email
        vendor.setContactPhone(dto.getContactPhone()); // updates contact phone
        vendor.setWebsiteUrl(dto.getWebsiteUrl()); // updates website url
        vendor.setTotalGoldQuantity(dto.getTotalGoldQuantity()); // updates total gold quantity
        vendor.setCurrentGoldPrice(dto.getCurrentGoldPrice()); // updates current gold price
        // vendorId and createdAt are NOT updated — they never change after creation
    }
}