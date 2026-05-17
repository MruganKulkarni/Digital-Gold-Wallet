package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 * ============================================================
 * Physical Gold Transaction Request DTO
 * ============================================================
 */

public class PhysicalGoldTransactionRequestDto {

    /*
     * User ID
     */

    @NotNull(message="User ID required")
    private Integer userId;

    /*
     * Branch ID
     */

    @NotNull(message="Branch ID required")
    private Integer branchId;

    /*
     * Delivery address
     */

    @NotNull(message="Address required")
    private Integer addressId;

    /*
     * Quantity
     */

    @NotNull(message="Quantity required")

    @DecimalMin(
            value="0.0",
            message="Quantity cannot be negative"
    )
    private BigDecimal quantity;

    public PhysicalGoldTransactionRequestDto(){}

    public PhysicalGoldTransactionRequestDto(

            Integer userId,

            Integer branchId,

            Integer addressId,

            BigDecimal quantity
    ){

        this.userId=userId;

        this.branchId=branchId;

        this.addressId=addressId;

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

}