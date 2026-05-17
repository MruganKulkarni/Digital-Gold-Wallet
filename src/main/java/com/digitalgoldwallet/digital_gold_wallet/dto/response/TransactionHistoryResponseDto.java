package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * ============================================================
 * Transaction History Response DTO
 * ============================================================
 */

public class TransactionHistoryResponseDto {

    private Integer transactionId;

    private Integer userId;

    private Integer branchId;

    private String transactionType;

    private String transactionStatus;

    private BigDecimal quantity;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    public TransactionHistoryResponseDto(){}

    public TransactionHistoryResponseDto(

            Integer transactionId,

            Integer userId,

            Integer branchId,

            String transactionType,

            String transactionStatus,

            BigDecimal quantity,

            BigDecimal amount,

            LocalDateTime createdAt

    ){

        this.transactionId=transactionId;

        this.userId=userId;

        this.branchId=branchId;

        this.transactionType=transactionType;

        this.transactionStatus=transactionStatus;

        this.quantity=quantity;

        this.amount=amount;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt=createdAt;
    }

}