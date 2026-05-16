package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 * DTO used for creating/updating payment
 * Contains validation rules
 */
public class PaymentRequestDTO {

    /*
     * User ID linked to payment
     */
    @NotNull(message = "User ID is required")
    private Integer userId;

    /*
     * Payment amount
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "1.0",
            message = "Amount must be greater than 0"
    )
    private BigDecimal amount;

    /*
     * Payment method
     */
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    /*
     * Transaction type
     */
    @NotBlank(message = "Transaction type is required")
    private String transactionType;

    /*
     * Payment status
     */
    @NotBlank(message = "Payment status is required")
    private String paymentStatus;

    // Default Constructor
    public PaymentRequestDTO() {
    }

    // Parameterized Constructor
    public PaymentRequestDTO(
            Integer userId,
            BigDecimal amount,
            String paymentMethod,
            String transactionType,
            String paymentStatus
    ) {
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.paymentStatus = paymentStatus;
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
}