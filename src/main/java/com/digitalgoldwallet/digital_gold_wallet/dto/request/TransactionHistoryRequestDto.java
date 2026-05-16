package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 * ============================================================
 * Transaction History Request DTO
 * ============================================================
 *
 * Used to receive transaction data from client
 *
 * ============================================================
 */

public class TransactionHistoryRequestDto {

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
     * Transaction type
     */
    @NotBlank(message = "Transaction type required")
    private String transactionType;

    /*
     * Transaction status
     */
    @NotBlank(message = "Transaction status required")
    private String transactionStatus;

    /*
     * Quantity
     */
    @NotNull(message = "Quantity required")
    @DecimalMin(
            value="0.0",
            message="Quantity cannot be negative"
    )
    private BigDecimal quantity;

    /*
     * Amount
     */
    @NotNull(message = "Amount required")
    @DecimalMin(
            value="0.0",
            message="Amount cannot be negative"
    )
    private BigDecimal amount;

    public TransactionHistoryRequestDto(){}

    public TransactionHistoryRequestDto(
            Integer userId,
            Integer branchId,
            String transactionType,
            String transactionStatus,
            BigDecimal quantity,
            BigDecimal amount
    ){

        this.userId=userId;
        this.branchId=branchId;
        this.transactionType=transactionType;
        this.transactionStatus=transactionStatus;
        this.quantity=quantity;
        this.amount=amount;

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(
            String transactionType
    ) {
        this.transactionType=transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(
            String transactionStatus
    ) {
        this.transactionStatus=transactionStatus;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(
            BigDecimal quantity
    ) {
        this.quantity=quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(
            BigDecimal amount
    ) {
        this.amount=amount;
    }

}