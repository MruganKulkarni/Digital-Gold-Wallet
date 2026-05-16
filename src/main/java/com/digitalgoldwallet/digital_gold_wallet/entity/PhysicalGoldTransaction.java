package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "physical_gold_transactions")
public class PhysicalGoldTransaction {

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

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0")
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    /*
     * Foreign Key -> addresses.address_id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private Address deliveryAddress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public PhysicalGoldTransaction() {
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}