package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;

/*
 * ============================================================
 * Branch Inventory Status Response DTO
 * ============================================================
 */

public class BranchInventoryStatusResponseDto {

    /*
     * Branch ID
     */
    private Integer branchId;

    /*
     * Vendor name
     */
    private String vendorName;

    /*
     * Available gold quantity
     */
    private BigDecimal quantity;

    public BranchInventoryStatusResponseDto() {
    }

    public BranchInventoryStatusResponseDto(
            Integer branchId,
            String vendorName,
            BigDecimal quantity
    ) {

        this.branchId = branchId;
        this.vendorName = vendorName;
        this.quantity = quantity;

    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(
            BigDecimal quantity
    ) {
        this.quantity = quantity;
    }
}