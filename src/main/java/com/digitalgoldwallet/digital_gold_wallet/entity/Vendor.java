package com.digitalgoldwallet.digital_gold_wallet.entity; // package declaration

import jakarta.persistence.*; // imports all JPA annotations
import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotBlank; // validation: field cannot be blank
import jakarta.validation.constraints.NotNull; // validation: field cannot be null

import java.math.BigDecimal; // used for precise decimal numbers
import java.time.LocalDateTime; // used for timestamp fields
import java.util.List; // used for one-to-many list of branches
import java.util.Objects; // used for equals and hashCode

@Entity // marks this class as a JPA entity
@Table(name = "vendors") // maps to "vendors" table in MySQL
public class Vendor {

    @Id // marks this as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "vendor_id") // maps to vendor_id column
    private Integer vendorId;

    @NotBlank(message = "Vendor name is required") // cannot be blank
    @Column(name = "vendor_name", nullable = false, length = 100) // NOT NULL, max 100 chars
    private String vendorName;

    @Column(name = "description", columnDefinition = "TEXT") // TEXT type, nullable
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
    @DecimalMin(value = "0.0", message = "Total gold quantity cannot be negative") // min 0
    @Column(name = "total_gold_quantity", nullable = false, precision = 18, scale = 2) // 18 digits, 2 decimal
    private BigDecimal totalGoldQuantity = BigDecimal.ZERO; // default 0.0

    @NotNull(message = "Current gold price is required") // cannot be null
    @DecimalMin(value = "0.01", message = "Gold price must be greater than 0") // min 0.01
    @Column(name = "current_gold_price", nullable = false, precision = 18, scale = 2) // 18 digits, 2 decimal
    private BigDecimal currentGoldPrice = new BigDecimal("5700.00"); // default price 5700

    @Column(name = "created_at", updatable = false) // never updated after insert
    private LocalDateTime createdAt; // stores creation timestamp

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // one vendor has many branches
    private List<VendorBranch> branches; // list of branches belonging to this vendor

    // ---- NO-ARGS CONSTRUCTOR ----
    public Vendor() { // default empty constructor required by JPA
    }

    // ---- ALL-ARGS CONSTRUCTOR ----
    public Vendor(Integer vendorId, String vendorName, String description, String contactPersonName,
                  String contactEmail, String contactPhone, String websiteUrl,
                  BigDecimal totalGoldQuantity, BigDecimal currentGoldPrice,
                  LocalDateTime createdAt, List<VendorBranch> branches) { // constructor with all fields
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.description = description;
        this.contactPersonName = contactPersonName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.websiteUrl = websiteUrl;
        this.totalGoldQuantity = totalGoldQuantity;
        this.currentGoldPrice = currentGoldPrice;
        this.createdAt = createdAt;
        this.branches = branches;
    }

    // ---- GETTERS ----
    public Integer getVendorId() { // returns vendor id
        return vendorId;
    }

    public String getVendorName() { // returns vendor name
        return vendorName;
    }

    public String getDescription() { // returns description
        return description;
    }

    public String getContactPersonName() { // returns contact person name
        return contactPersonName;
    }

    public String getContactEmail() { // returns contact email
        return contactEmail;
    }

    public String getContactPhone() { // returns contact phone
        return contactPhone;
    }

    public String getWebsiteUrl() { // returns website url
        return websiteUrl;
    }

    public BigDecimal getTotalGoldQuantity() { // returns total gold quantity
        return totalGoldQuantity;
    }

    public BigDecimal getCurrentGoldPrice() { // returns current gold price
        return currentGoldPrice;
    }

    public LocalDateTime getCreatedAt() { // returns creation timestamp
        return createdAt;
    }

    public List<VendorBranch> getBranches() { // returns list of branches
        return branches;
    }

    // ---- SETTERS ----
    public void setVendorId(Integer vendorId) { // sets vendor id
        this.vendorId = vendorId;
    }

    public void setVendorName(String vendorName) { // sets vendor name
        this.vendorName = vendorName;
    }

    public void setDescription(String description) { // sets description
        this.description = description;
    }

    public void setContactPersonName(String contactPersonName) { // sets contact person name
        this.contactPersonName = contactPersonName;
    }

    public void setContactEmail(String contactEmail) { // sets contact email
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(String contactPhone) { // sets contact phone
        this.contactPhone = contactPhone;
    }

    public void setWebsiteUrl(String websiteUrl) { // sets website url
        this.websiteUrl = websiteUrl;
    }

    public void setTotalGoldQuantity(BigDecimal totalGoldQuantity) { // sets total gold quantity
        this.totalGoldQuantity = totalGoldQuantity;
    }

    public void setCurrentGoldPrice(BigDecimal currentGoldPrice) { // sets current gold price
        this.currentGoldPrice = currentGoldPrice;
    }

    public void setCreatedAt(LocalDateTime createdAt) { // sets creation timestamp
        this.createdAt = createdAt;
    }

    public void setBranches(List<VendorBranch> branches) { // sets list of branches
        this.branches = branches;
    }

    // ---- EQUALS ----
    @Override
    public boolean equals(Object o) { // checks if two Vendor objects are equal based on vendorId
        if (this == o) return true; // same object reference
        if (o == null || getClass() != o.getClass()) return false; // null or different class
        Vendor vendor = (Vendor) o; // cast to Vendor
        return Objects.equals(vendorId, vendor.vendorId); // compare by vendorId only
    }

    // ---- HASHCODE ----
    @Override
    public int hashCode() { // generates hash based on vendorId
        return Objects.hash(vendorId); // uses vendorId for hash
    }

    // ---- TOSTRING ----
    @Override
    public String toString() { // returns string representation of Vendor
        return "Vendor{" +
                "vendorId=" + vendorId + // vendor id
                ", vendorName='" + vendorName + '\'' + // vendor name
                ", contactEmail='" + contactEmail + '\'' + // contact email
                ", totalGoldQuantity=" + totalGoldQuantity + // total gold quantity
                ", currentGoldPrice=" + currentGoldPrice + // current gold price
                ", createdAt=" + createdAt + // creation timestamp
                '}';
    }

    @PrePersist // runs automatically just before saving to DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // sets current timestamp on creation
    }
}