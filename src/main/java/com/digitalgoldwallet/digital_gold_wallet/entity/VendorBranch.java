package com.digitalgoldwallet.digital_gold_wallet.entity; // package declaration

import jakarta.persistence.*; // imports all JPA annotations
import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotNull; // validation: field cannot be null
import lombok.AllArgsConstructor; // lombok: generates all-args constructor
import lombok.Data; // lombok: generates getters, setters, toString etc
import lombok.NoArgsConstructor; // lombok: generates no-args constructor

import java.math.BigDecimal; // used for precise decimal (gold quantity)
import java.time.LocalDateTime; // used for created_at timestamp

@Entity // marks this class as a JPA entity
@Table(name = "vendor_branches") // maps to "vendor_branches" table in MySQL
@Data // lombok generates all getters and setters
@NoArgsConstructor // lombok generates empty constructor
@AllArgsConstructor // lombok generates all-fields constructor
public class VendorBranch {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "branch_id") // maps to "branch_id" column
    private Integer branchId;

    @NotNull(message = "Vendor is required") // vendor cannot be null
    @ManyToOne(fetch = FetchType.LAZY) // many branches belong to one vendor, lazy loaded
    @JoinColumn(name = "vendor_id", nullable = false) // foreign key column "vendor_id"
    private Vendor vendor; // reference to parent Vendor entity

    @ManyToOne(fetch = FetchType.LAZY) // many branches can share an address, lazy loaded
    @JoinColumn(name = "address_id") // foreign key column "address_id" (Varsha's entity)
    private Address address; // reference to Address entity (owned by Varsha's module)

    @NotNull(message = "Quantity is required") // quantity cannot be null
    @DecimalMin(value = "0.0", message = "Quantity cannot be negative") // min value 0
    @Column(name = "quantity", nullable = false, precision = 18, scale = 2) // 18 digits, 2 decimal
    private BigDecimal quantity = BigDecimal.ZERO; // default gold quantity is 0

    @Column(name = "created_at", updatable = false) // never updated after first insert
    private LocalDateTime createdAt; // timestamp of branch creation

    @PrePersist // runs automatically before saving to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // sets current timestamp on creation
    }
}