package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * ============================================================
 * Physical Gold Transaction Response DTO
 * ============================================================
 */

public class PhysicalGoldTransactionResponseDto {

    private Integer transactionId;

    private Integer userId;

    private Integer branchId;

    private Integer addressId;

    private BigDecimal quantity;

    private LocalDateTime createdAt;

    public PhysicalGoldTransactionResponseDto(){}

    public PhysicalGoldTransactionResponseDto(

            Integer transactionId,

            Integer userId,

            Integer branchId,

            Integer addressId,

            BigDecimal quantity,

            LocalDateTime createdAt
    ){

        this.transactionId=transactionId;

        this.userId=userId;

        this.branchId=branchId;

        this.addressId=addressId;

        this.quantity=quantity;

        this.createdAt=createdAt;

    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(
            Integer transactionId
    ) {
        this.transactionId=transactionId;
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

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(
            Integer addressId
    ) {
        this.addressId=addressId;
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