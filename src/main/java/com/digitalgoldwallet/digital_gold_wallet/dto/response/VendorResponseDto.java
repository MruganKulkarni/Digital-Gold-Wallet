package com.digitalgoldwallet.digital_gold_wallet.dto.response; // package declaration for response DTOs

import java.math.BigDecimal; // used for precise decimal numbers
import java.time.LocalDateTime; // used for timestamp field

/*
 * DTO used for returning Vendor data in API responses
 * Used in GET responses — never exposes the entity directly
 */
public class VendorResponseDto {

    private Integer vendorId; // vendor's unique ID

    private String vendorName; // vendor business name

    private String description; // vendor description

    private String contactPersonName; // contact person name

    private String contactEmail; // contact email

    private String contactPhone; // contact phone

    private String websiteUrl; // website url

    private BigDecimal totalGoldQuantity; // total gold quantity held by this vendor

    private BigDecimal currentGoldPrice; // current gold price per unit

    private LocalDateTime createdAt; // timestamp when vendor was created

    // ---- NO-ARGS CONSTRUCTOR ----
    public VendorResponseDto() { // default empty constructor
    }

    // ---- ALL-ARGS CONSTRUCTOR ----
    public VendorResponseDto(Integer vendorId, // vendor id
                             String vendorName, // vendor name
                             String description, // description
                             String contactPersonName, // contact person
                             String contactEmail, // contact email
                             String contactPhone, // contact phone
                             String websiteUrl, // website url
                             BigDecimal totalGoldQuantity, // total gold quantity
                             BigDecimal currentGoldPrice, // current gold price
                             LocalDateTime createdAt) { // creation timestamp
        this.vendorId = vendorId; // sets vendor id
        this.vendorName = vendorName; // sets vendor name
        this.description = description; // sets description
        this.contactPersonName = contactPersonName; // sets contact person name
        this.contactEmail = contactEmail; // sets contact email
        this.contactPhone = contactPhone; // sets contact phone
        this.websiteUrl = websiteUrl; // sets website url
        this.totalGoldQuantity = totalGoldQuantity; // sets total gold quantity
        this.currentGoldPrice = currentGoldPrice; // sets current gold price
        this.createdAt = createdAt; // sets creation timestamp
    }

    // ---- GETTERS ----
    public Integer getVendorId() { return vendorId; } // returns vendor id

    public String getVendorName() { return vendorName; } // returns vendor name

    public String getDescription() { return description; } // returns description

    public String getContactPersonName() { return contactPersonName; } // returns contact person name

    public String getContactEmail() { return contactEmail; } // returns contact email

    public String getContactPhone() { return contactPhone; } // returns contact phone

    public String getWebsiteUrl() { return websiteUrl; } // returns website url

    public BigDecimal getTotalGoldQuantity() { return totalGoldQuantity; } // returns total gold quantity

    public BigDecimal getCurrentGoldPrice() { return currentGoldPrice; } // returns current gold price

    public LocalDateTime getCreatedAt() { return createdAt; } // returns creation timestamp

    // ---- SETTERS ----
    public void setVendorId(Integer vendorId) { this.vendorId = vendorId; } // sets vendor id

    public void setVendorName(String vendorName) { this.vendorName = vendorName; } // sets vendor name

    public void setDescription(String description) { this.description = description; } // sets description

    public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; } // sets contact person name

    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; } // sets contact email

    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; } // sets contact phone

    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; } // sets website url

    public void setTotalGoldQuantity(BigDecimal totalGoldQuantity) { this.totalGoldQuantity = totalGoldQuantity; } // sets total gold quantity

    public void setCurrentGoldPrice(BigDecimal currentGoldPrice) { this.currentGoldPrice = currentGoldPrice; } // sets current gold price

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } // sets creation timestamp
}