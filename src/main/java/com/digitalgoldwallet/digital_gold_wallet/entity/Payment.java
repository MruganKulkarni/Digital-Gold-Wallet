package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * Entity class for payments table
 */
@Entity

/*
 * Maps this entity to payments table in MySQL
 */
@Table(name = "payments")
public class Payment {

    /*
     * Primary key for payments table
     * Auto incremented by MySQL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    /*
     * Many payments belong to one user
     * user_id is foreign key
     */
    @NotNull(message = "User is required")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
     * Payment amount
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.0", message = "Amount must be greater than 0")
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    /*
     * Payment method
     */
    @NotBlank(message = "Payment method is required")
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    /*
     * Transaction type
     */
    @NotBlank(message = "Transaction type is required")
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    /*
     * Payment status
     */
    @NotBlank(message = "Payment status is required")
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    /*
     * Payment creation time
     */
    @Column(name = "created_at", updatable = false) // updatable = false means never changes after insert
    private LocalDateTime createdAt;

    /*
     * Default constructor
     */
    public Payment() {
    }

    /*
     * Parameterized constructor
     */
    public Payment(Integer paymentId, User user, BigDecimal amount,
                   String paymentMethod, String transactionType,
                   String paymentStatus, LocalDateTime createdAt) {

        this.paymentId = paymentId;
        this.user = user;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionType = transactionType;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /*
     * Automatically sets createdAt timestamp
     * before saving to database
     */
    @PrePersist // runs automatically just before saving to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // sets current timestamp on creation
    }
}