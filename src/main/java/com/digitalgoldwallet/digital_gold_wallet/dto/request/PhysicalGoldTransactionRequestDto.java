package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/*
 * ============================================================
 * Physical Gold Transaction Request DTO
 * ============================================================
 */
@Schema(description = "Request body for physical gold conversion")
public class PhysicalGoldTransactionRequestDto {

    /*
     * User ID
     */
    @Schema(description = "User ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message="User ID required")
    private Integer userId;

    /*
     * Branch ID
     */
    @Schema(description = "Branch ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message="Branch ID required")
    private Integer branchId;

    /*
     * Delivery address
     */
    @Schema(description = "Address ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message="Address required")
    private Integer addressId;

    /*
     * Quantity
     */
    @Schema(description = "Quantity of gold in grams", example = "10.0", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message="Quantity required")
    @DecimalMin(
            value="0.0",
            inclusive=false,
            message="Quantity must be greater than 0"
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