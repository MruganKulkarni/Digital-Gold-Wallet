package com.digitalgoldwallet.digital_gold_wallet.dto.response; // package declaration for response DTOs

import java.math.BigDecimal; // used for precise decimal numbers
import java.time.LocalDateTime; // used for timestamp field

/*
 * DTO used for returning VendorBranch data in API responses
 * Used in GET responses — never exposes the entity directly
 */
public class VendorBranchResponseDto {

    private Integer branchId; // branch's unique ID

    private Integer vendorId; // ID of the vendor this branch belongs to

    private String vendorName; // name of the vendor — included for convenience in response

    private Integer addressId; // ID of the address for this branch

    private String city; // city of the branch — included for convenience in response

    private String state; // state of the branch — included for convenience in response

    private BigDecimal quantity; // gold quantity available at this branch

    private LocalDateTime createdAt; // timestamp when branch was created

    // ---- NO-ARGS CONSTRUCTOR ----
    public VendorBranchResponseDto() { // default empty constructor
    }

    // ---- ALL-ARGS CONSTRUCTOR ----
    public VendorBranchResponseDto(Integer branchId, // branch id
                                   Integer vendorId, // vendor id
                                   String vendorName, // vendor name
                                   Integer addressId, // address id
                                   String city, // city
                                   String state, // state
                                   BigDecimal quantity, // gold quantity
                                   LocalDateTime createdAt) { // creation timestamp
        this.branchId = branchId; // sets branch id
        this.vendorId = vendorId; // sets vendor id
        this.vendorName = vendorName; // sets vendor name
        this.addressId = addressId; // sets address id
        this.city = city; // sets city
        this.state = state; // sets state
        this.quantity = quantity; // sets gold quantity
        this.createdAt = createdAt; // sets creation timestamp
    }

    // ---- GETTERS ----
    public Integer getBranchId() { return branchId; } // returns branch id

    public Integer getVendorId() { return vendorId; } // returns vendor id

    public String getVendorName() { return vendorName; } // returns vendor name

    public Integer getAddressId() { return addressId; } // returns address id

    public String getCity() { return city; } // returns city

    public String getState() { return state; } // returns state

    public BigDecimal getQuantity() { return quantity; } // returns gold quantity

    public LocalDateTime getCreatedAt() { return createdAt; } // returns creation timestamp

    // ---- SETTERS ----
    public void setBranchId(Integer branchId) { this.branchId = branchId; } // sets branch id

    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; } // sets vendor id

    public void setVendorName(String vendorName) { this.vendorName = vendorName; } // sets vendor name

    public void setAddressId(Integer addressId) { this.addressId = addressId; } // sets address id

    public void setCity(String city) { this.city = city; } // sets city

    public void setState(String state) { this.state = state; } // sets state

    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; } // sets gold quantity

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // sets creation timestamp
}