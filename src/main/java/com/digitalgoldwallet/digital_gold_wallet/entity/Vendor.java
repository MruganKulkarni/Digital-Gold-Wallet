package com.digitalgoldwallet.digital_gold_wallet.entity; // package declaration

import jakarta.persistence.*; // imports all JPA annotations like @Entity, @Table, @Id etc
import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotBlank; // validation: field cannot be blank or null
import jakarta.validation.constraints.NotNull; // validation: field cannot be null
import lombok.AllArgsConstructor; // lombok: generates constructor with all fields
import lombok.Data; // lombok: generates getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor; // lombok: generates empty constructor

import java.math.BigDecimal; // used for precise decimal numbers (money, gold quantity)
import java.time.LocalDateTime; // used for timestamp fields
import java.util.List; // used for one-to-many list of branches

@Entity // marks this class as a JPA entity (maps to a database table)
@Table(name = "vendors") // maps to the "vendors" table in MySQL
@Data // lombok generates all getters and setters automatically
@NoArgsConstructor // lombok generates default empty constructor
@AllArgsConstructor // lombok generates constructor with all fields
public class Vendor {

    @Id // marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment in MySQL
    @Column(name = "vendor_id") // maps to "vendor_id" column in DB
    private Integer vendorId;

    @NotBlank(message = "Vendor name is required") // validation: name cannot be empty
    @Column(name = "vendor_name", nullable = false, length = 100) // NOT NULL, max 100 chars
    private String vendorName;

    @Column(name = "description", columnDefinition = "TEXT") // TEXT type column, nullable
    private String description;

    @Column(name = "contact_person_name", length = 100) // nullable, max 100 chars
    private String contactPersonName;

    @Column(name = "contact_email", length = 100) // nullable, max 100 chars
    private String contactEmail;

    @Column(name = "contact_phone", length = 20) // nullable, max 20 chars
    private String contactPhone;

    @Column(name = "website_url", length = 255) // nullable, max 255 chars
    private String websiteUrl;

    @NotNull(message = "Total gold quantity is required") // cannot be null
    @DecimalMin(value = "0.0", message = "Total gold quantity cannot be negative") // min value 0
    @Column(name = "total_gold_quantity", nullable = false, precision = 18, scale = 2) // 18 digits, 2 decimal
    private BigDecimal totalGoldQuantity = BigDecimal.ZERO; // default value is 0.0

    @NotNull(message = "Current gold price is required") // cannot be null
    @DecimalMin(value = "0.01", message = "Gold price must be greater than 0") // min value 0.01
    @Column(name = "current_gold_price", nullable = false, precision = 18, scale = 2) // 18 digits, 2 decimal
    private BigDecimal currentGoldPrice = new BigDecimal("5700.00"); // default price 5700

    @Column(name = "created_at", updatable = false) // updatable=false means never changes after insert
    private LocalDateTime createdAt; // stores date and time of creation

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // one vendor has many branches
    private List<VendorBranch> branches; // list of all branches belonging to this vendor

    @PrePersist // runs automatically just before saving to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // sets current timestamp when vendor is created
    }
}