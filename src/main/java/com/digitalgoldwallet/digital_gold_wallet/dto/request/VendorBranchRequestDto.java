package com.digitalgoldwallet.digital_gold_wallet.dto.request; // package declaration for request DTOs

import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotNull; // validation: field cannot be null

import java.math.BigDecimal; // used for precise decimal numbers

/*
 * DTO used for validating VendorBranch request data
 * Used in POST (create) and PUT (update) API calls
 * Mirrors all validations from VendorBranch entity
 * Takes vendorId and addressId as integers instead of full objects
 */
public class VendorBranchRequestDto {

    @NotNull(message = "Vendor ID is required") // cannot be null — mirrors VendorBranch entity vendor validation
    private Integer vendorId; // ID of the vendor this branch belongs to

    @NotNull(message = "Address ID is required") // cannot be null — address is required for a branch
    private Integer addressId; // ID of the address for this branch location

    @NotNull(message = "Quantity is required") // cannot be null — mirrors VendorBranch entity validation
    @DecimalMin(value = "0.0", message = "Quantity cannot be negative") // min 0 — mirrors VendorBranch entity validation
    private BigDecimal quantity; // gold quantity available at this branch

    // ---- NO-ARGS CONSTRUCTOR ----
    public VendorBranchRequestDto() { // default empty constructor required for JSON deserialization
    }

    // ---- ALL-ARGS CONSTRUCTOR ----
    public VendorBranchRequestDto(Integer vendorId, // vendor id
                                  Integer addressId, // address id
                                  BigDecimal quantity) { // gold quantity
        this.vendorId = vendorId; // sets vendor id
        this.addressId = addressId; // sets address id
        this.quantity = quantity; // sets gold quantity
    }

    // ---- GETTERS ----
    public Integer getVendorId() { return vendorId; } // returns vendor id

    public Integer getAddressId() { return addressId; } // returns address id

    public BigDecimal getQuantity() { return quantity; } // returns gold quantity

    // ---- SETTERS ----
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; } // sets vendor id

    public void setAddressId(Integer addressId) { this.addressId = addressId; } // sets address id

    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; } // sets gold quantity
}