package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    /*
     * Foreign Key -> users.user_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
     * Foreign Key -> vendor_branches.branch_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private VendorBranch branch;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "transaction_status", nullable = false)
    private String transactionStatus;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0")
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0")
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public TransactionHistory() {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VendorBranch getBranch() {
        return branch;
    }

    public void setBranch(VendorBranch branch) {
        this.branch = branch;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}