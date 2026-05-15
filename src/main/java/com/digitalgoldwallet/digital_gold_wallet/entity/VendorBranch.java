package com.digitalgoldwallet.digital_gold_wallet.entity; // package declaration

import jakarta.persistence.*; // imports all JPA annotations
import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotNull; // validation: field cannot be null

import java.math.BigDecimal; // used for precise decimal
import java.time.LocalDateTime; // used for timestamp

@Entity // marks this as a JPA entity
@Table(name = "vendor_branches") // maps to "vendor_branches" table in MySQL
public class VendorBranch { // no more Lombok annotations

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "branch_id") // maps to branch_id column
    private Integer branchId;

    @NotNull(message = "Vendor is required") // vendor cannot be null
    @ManyToOne(fetch = FetchType.LAZY) // many branches belong to one vendor
    @JoinColumn(name = "vendor_id", nullable = false) // foreign key vendor_id
    private Vendor vendor; // reference to parent Vendor

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "address_id")
    // private Address address; // commented out until Varsha pushes Address entity

    @NotNull(message = "Quantity is required") // quantity cannot be null
    @DecimalMin(value = "0.0", message = "Quantity cannot be negative") // min 0
    @Column(name = "quantity", nullable = false, precision = 18, scale = 2) // 18 digits, 2 decimal
    private BigDecimal quantity = BigDecimal.ZERO; // default gold quantity 0

    @Column(name = "created_at", updatable = false) // never updated after insert
    private LocalDateTime createdAt; // stores creation timestamp

    // ---- NO-ARGS CONSTRUCTOR ----
    public VendorBranch() { // default empty constructor required by JPA
    }

    // ---- ALL-ARGS CONSTRUCTOR ----
    public VendorBranch(Integer branchId, Vendor vendor, BigDecimal quantity, LocalDateTime createdAt) { // all fields constructor
        this.branchId = branchId;
        this.vendor = vendor;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    // ---- GETTERS ----
    public Integer getBranchId() { // returns branch id
        return branchId;
    }

    public Vendor getVendor() { // returns parent vendor
        return vendor;
    }

    public BigDecimal getQuantity() { // returns gold quantity
        return quantity;
    }

    public LocalDateTime getCreatedAt() { // returns creation timestamp
        return createdAt;
    }

    // ---- SETTERS ----
    public void setBranchId(Integer branchId) { // sets branch id
        this.branchId = branchId;
    }

    public void setVendor(Vendor vendor) { // sets parent vendor
        this.vendor = vendor;
    }

    public void setQuantity(BigDecimal quantity) { // sets gold quantity
        this.quantity = quantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) { // sets creation timestamp
        this.createdAt = createdAt;
    }

    @PrePersist // runs automatically before saving to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // sets current timestamp on creation
    }
}