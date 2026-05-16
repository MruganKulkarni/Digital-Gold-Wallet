package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * ============================================================
 * VirtualGoldHolding Response DTO
 * ============================================================
 */

public class VirtualGoldHoldingResponseDto {

    private Integer holdingId;

    private Integer userId;

    private Integer branchId;

    private BigDecimal quantity;

    private LocalDateTime createdAt;

    public VirtualGoldHoldingResponseDto() {
    }

    public VirtualGoldHoldingResponseDto(
            Integer holdingId,
            Integer userId,
            Integer branchId,
            BigDecimal quantity,
            LocalDateTime createdAt
    ) {

        this.holdingId=holdingId;
        this.userId=userId;
        this.branchId=branchId;
        this.quantity=quantity;
        this.createdAt=createdAt;
    }

    public Integer getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(Integer holdingId) {
        this.holdingId = holdingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId=userId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt=createdAt;
    }
}