package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/*
 * Stores physical gold conversion requests
 */

@Entity
@Table(name = "physical_gold_transactions")
public class PhysicalGoldTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;


    @NotNull(message = "User reference is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    @NotNull(message = "Branch reference is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "branch_id",
            nullable = false
    )
    private VendorBranch branch;


    @NotNull(message = "Physical gold quantity is required")
    @DecimalMin(
            value = "0.01",
            message = "Physical gold quantity must be greater than 0"
    )
    private BigDecimal quantity;


    @NotNull(message = "Delivery address is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "delivery_address_id",
            nullable = false
    )
    private Address deliveryAddress;


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    // Default constructor
    public PhysicalGoldTransaction() {
    }


    // Parameterized constructor
    public PhysicalGoldTransaction(
            Integer transactionId,
            User user,
            VendorBranch branch,
            BigDecimal quantity,
            Address deliveryAddress,
            LocalDateTime createdAt
    ) {

        this.transactionId = transactionId;
        this.user = user;
        this.branch = branch;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.createdAt = createdAt;
    }


    // ================= GETTERS =================

    public Integer getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public VendorBranch getBranch() {
        return branch;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // ================= SETTERS =================

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBranch(VendorBranch branch) {
        this.branch = branch;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null ||
                getClass() != o.getClass())
            return false;

        PhysicalGoldTransaction that =
                (PhysicalGoldTransaction) o;

        return Objects.equals(
                transactionId,
                that.transactionId
        );
    }


    @Override
    public int hashCode() {

        return Objects.hash(
                transactionId
        );
    }


    @Override
    public String toString() {

        return "PhysicalGoldTransaction{" +
                "transactionId=" + transactionId +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';
    }
}