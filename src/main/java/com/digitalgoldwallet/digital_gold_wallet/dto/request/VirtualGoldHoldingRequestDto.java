package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 * ============================================================
 * VirtualGoldHolding Request DTO
 * ============================================================
 *
 * Used to receive holding data from client
 *
 * ============================================================
 */

public class VirtualGoldHoldingRequestDto {

    /*
     * User ID
     */
    @NotNull(message = "User ID required")
    private Integer userId;

    /*
     * Branch ID
     */
    @NotNull(message = "Branch ID required")
    private Integer branchId;

    /*
     * Quantity
     */
    @NotNull(message = "Quantity required")
    @DecimalMin(
            value="0.0",
            inclusive=false,
            message="Quantity must be greater than 0"
    )
    private BigDecimal quantity;

    public VirtualGoldHoldingRequestDto() {
    }

    public VirtualGoldHoldingRequestDto(
            Integer userId,
            Integer branchId,
            BigDecimal quantity
    ) {
        this.userId=userId;
        this.branchId=branchId;
        this.quantity=quantity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(
            Integer userId
    ) {
        this.userId=userId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(
            Integer branchId
    ) {
        this.branchId=branchId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(
            BigDecimal quantity
    ) {
        this.quantity=quantity;
    }
}