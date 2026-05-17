package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * DTO used for sending payment response
 */
public class PaymentResponseDto {

    /*
     * Payment ID
     */
    private Integer paymentId;

    /*
     * User ID linked to payment
     */
    private Integer userId;

    /*
     * Payment amount
     */
    private BigDecimal amount;

    /*
     * Payment method
     */
    private String paymentMethod;

    /*
     * Transaction type
     */
    private String transactionType;

    /*
     * Payment status
     */
    private String paymentStatus;

    /*
     * Payment creation timestamp
     */
    private LocalDateTime createdAt;

    // Default Constructor
    public PaymentResponseDto() {
    }

    // Parameterized Constructor
    public PaymentResponseDto(
            Integer paymentId,
            Integer userId,
            BigDecimal amount,
            String paymentMethod,
            String transactionType,
            String paymentStatus,
            LocalDateTime createdAt
    ) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    // Getter and Setter for paymentId
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    // Getter and Setter for userId
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // Getter and Setter for amount
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Getter and Setter for paymentMethod
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Getter and Setter for transactionType
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    // Getter and Setter for paymentStatus
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Getter and Setter for createdAt
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}