package com.digitalgoldwallet.digital_gold_wallet.entity; // package declaration

import jakarta.persistence.*; // imports all JPA annotations
import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotNull; // validation: field cannot be null

import java.math.BigDecimal; // used for precise decimal
import java.time.LocalDateTime; // used for timestamp
import java.util.Objects; // used for equals and hashCode

@Entity // marks this as a JPA entity
@Table(name = "vendor_branches") // maps to "vendor_branches" table in MySQL
public class VendorBranch {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "branch_id") // maps to branch_id column
    private Integer branchId;

    @NotNull(message = "Vendor is required") // vendor cannot be null
    @ManyToOne(fetch = FetchType.LAZY) // many branches belong to one vendor
    @JoinColumn(name = "vendor_id", nullable = false) // foreign key vendor_id
    private Vendor vendor; // reference to parent Vendor entity

    @ManyToOne(fetch = FetchType.LAZY) // many branches can have one address
    @JoinColumn(name = "address_id") // foreign key address_id from addresses table
    private Address address; // reference to Varsha's Address entity

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
    public VendorBranch(Integer branchId, Vendor vendor, Address address, BigDecimal quantity, LocalDateTime createdAt) { // all fields constructor
        this.branchId = branchId;
        this.vendor = vendor;
        this.address = address; // sets address
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

    public Address getAddress() { // returns branch address
        return address;
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

    public void setAddress(Address address) { // sets branch address
        this.address = address;
    }

    public void setQuantity(BigDecimal quantity) { // sets gold quantity
        this.quantity = quantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) { // sets creation timestamp
        this.createdAt = createdAt;
    }

    // ---- EQUALS ----
    @Override
    public boolean equals(Object o) { // checks if two VendorBranch objects are equal
        if (this == o) return true; // same object reference
        if (o == null || getClass() != o.getClass()) return false; // null or different class
        VendorBranch that = (VendorBranch) o; // cast to VendorBranch
        return Objects.equals(branchId, that.branchId); // compare by branchId only
    }

    // ---- HASHCODE ----
    @Override
    public int hashCode() { // generates hash based on branchId
        return Objects.hash(branchId); // uses branchId for hash
    }

    // ---- TOSTRING ----
    @Override
    public String toString() { // returns string representation of VendorBranch
        return "VendorBranch{" +
                "branchId=" + branchId + // branch id
                ", vendor=" + (vendor != null ? vendor.getVendorId() : null) + // vendor id to avoid circular reference
                ", address=" + (address != null ? address.getAddressId() : null) + // address id to avoid circular reference
                ", quantity=" + quantity + // gold quantity
                ", createdAt=" + createdAt + // creation timestamp
                '}';
    }

    @PrePersist // runs automatically before saving to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // sets current timestamp on creation
    }
}