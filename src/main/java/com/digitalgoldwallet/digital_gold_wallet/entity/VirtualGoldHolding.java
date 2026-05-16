package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/*
 * Stores virtual gold owned by a user
 */

@Entity
@Table(name="virtual_gold_holdings")
public class VirtualGoldHolding {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name="holding_id")
    private Integer holdingId;


    @NotNull(
            message="User reference is required"
    )
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            nullable=false
    )
    private User user;


    @NotNull(
            message="Vendor branch reference is required"
    )
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="branch_id",
            nullable=false
    )
    private VendorBranch branch;


    @NotNull(
            message="Gold quantity is required"
    )
    @DecimalMin(
            value="0.01",
            message="Gold quantity must be greater than 0"
    )
    @Column(
            nullable=false
    )
    private BigDecimal quantity;


    @Column(name="created_at")
    private LocalDateTime createdAt;


    // Default constructor required by JPA
    public VirtualGoldHolding(){}


    // Parameterized constructor
    public VirtualGoldHolding(
            Integer holdingId,
            User user,
            VendorBranch branch,
            BigDecimal quantity,
            LocalDateTime createdAt
    ){

        this.holdingId=holdingId;
        this.user=user;
        this.branch=branch;
        this.quantity=quantity;
        this.createdAt=createdAt;
    }



    // =========================
    // GETTERS
    // =========================

    public Integer getHoldingId() {
        return holdingId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    // =========================
    // SETTERS
    // =========================

    public void setHoldingId(
            Integer holdingId
    ) {
        this.holdingId = holdingId;
    }

    public void setUser(
            User user
    ) {
        this.user = user;
    }

    public void setBranch(
            VendorBranch branch
    ) {
        this.branch = branch;
    }

    public void setQuantity(
            BigDecimal quantity
    ) {
        this.quantity = quantity;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }



    // =========================
    // equals
    // =========================

    @Override
    public boolean equals(
            Object o
    ){

        if(this==o)
            return true;

        if(o==null ||
                getClass()!=o.getClass())
            return false;

        VirtualGoldHolding that=
                (VirtualGoldHolding)o;

        return Objects.equals(
                holdingId,
                that.holdingId
        );
    }



    // =========================
    // hashCode
    // =========================

    @Override
    public int hashCode(){

        return Objects.hash(
                holdingId
        );
    }



    // =========================
    // toString
    // =========================

    @Override
    public String toString(){

        return "VirtualGoldHolding{" +
                "holdingId=" + holdingId +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';

    }

}