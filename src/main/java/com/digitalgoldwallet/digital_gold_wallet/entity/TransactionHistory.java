package com.digitalgoldwallet.digital_gold_wallet.entity;

import com.digitalgoldwallet.digital_gold_wallet.constants.TransactionStatus;
import com.digitalgoldwallet.digital_gold_wallet.constants.TransactionType;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/*
 * Stores all transaction activity
 */

@Entity
@Table(name="transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(
            strategy=GenerationType.IDENTITY
    )
    @Column(name="transaction_id")
    private Integer transactionId;


    @NotNull(message="User reference is required")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="user_id",
            nullable=false
    )
    private User user;


    @NotNull(message="Vendor branch reference is required")
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(
            name="branch_id",
            nullable=false
    )
    private VendorBranch branch;


    @Enumerated(EnumType.STRING)
    @NotNull(message="Transaction type required")
    private TransactionType transactionType;


    @Enumerated(EnumType.STRING)
    @NotNull(message="Transaction status required")
    private TransactionStatus transactionStatus;


    @NotNull(message="Quantity is required")
    @DecimalMin(
            value="0.01",
            message="Quantity should be greater than 0"
    )
    private BigDecimal quantity;


    @NotNull(message="Amount is required")
    @DecimalMin(
            value="0.01",
            message="Amount should be greater than 0"
    )
    private BigDecimal amount;


    @Column(name="created_at")
    private LocalDateTime createdAt;


    public TransactionHistory(){}


    public TransactionHistory(
            Integer transactionId,
            User user,
            VendorBranch branch,
            TransactionType transactionType,
            TransactionStatus transactionStatus,
            BigDecimal quantity,
            BigDecimal amount,
            LocalDateTime createdAt){

        this.transactionId=transactionId;
        this.user=user;
        this.branch=branch;
        this.transactionType=transactionType;
        this.transactionStatus=transactionStatus;
        this.quantity=quantity;
        this.amount=amount;
        this.createdAt=createdAt;
    }



    // GETTERS

    public Integer getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public VendorBranch getBranch() {
        return branch;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // SETTERS

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBranch(VendorBranch branch) {
        this.branch = branch;
    }

    public void setTransactionType(
            TransactionType transactionType) {

        this.transactionType = transactionType;
    }

    public void setTransactionStatus(
            TransactionStatus transactionStatus) {

        this.transactionStatus = transactionStatus;
    }

    public void setQuantity(
            BigDecimal quantity) {

        this.quantity = quantity;
    }

    public void setAmount(
            BigDecimal amount) {

        this.amount = amount;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }



    @Override
    public boolean equals(Object o){

        if(this==o)
            return true;

        if(o==null ||
                getClass()!=o.getClass())
            return false;

        TransactionHistory that=
                (TransactionHistory)o;

        return Objects.equals(
                transactionId,
                that.transactionId
        );
    }


    @Override
    public int hashCode(){

        return Objects.hash(
                transactionId
        );
    }


    @Override
    public String toString(){

        return "TransactionHistory{" +
                "transactionId=" + transactionId +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                '}';
    }
}