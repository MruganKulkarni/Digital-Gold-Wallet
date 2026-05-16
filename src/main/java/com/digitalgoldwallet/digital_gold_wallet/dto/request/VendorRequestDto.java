package com.digitalgoldwallet.digital_gold_wallet.dto.request; // package declaration for request DTOs

import jakarta.validation.constraints.DecimalMin; // validation: minimum decimal value
import jakarta.validation.constraints.NotBlank; // validation: field cannot be blank
import jakarta.validation.constraints.NotNull; // validation: field cannot be null

import java.math.BigDecimal; // used for precise decimal numbers

/*
 * DTO used for validating Vendor request data
 * Used in POST (create) and PUT (update) API calls
 * Mirrors all validations from Vendor entity
 */
public class VendorRequestDto {

    @NotBlank(message = "Vendor name is required") // cannot be blank — mirrors Vendor entity
    private String vendorName; // vendor business name

    private String description; // optional — no validation needed

    private String contactPersonName; // optional — no validation needed

    private String contactEmail; // optional — no validation needed

    private String contactPhone; // optional — no validation needed

    private String websiteUrl; // optional — no validation needed

    @NotNull(message = "Total gold quantity is required") // cannot be null — mirrors Vendor entity
    @DecimalMin(value = "0.0", message = "Total gold quantity cannot be negative") // min 0 — mirrors Vendor entity
    private BigDecimal totalGoldQuantity; // total gold quantity held by this vendor

    @NotNull(message = "Current gold price is required") // cannot be null — mirrors Vendor entity
    @DecimalMin(value = "0.01", message = "Gold price must be greater than 0") // min 0.01 — mirrors Vendor entity
    private BigDecimal currentGoldPrice; // current gold price per unit set by this vendor

    // ---- NO-ARGS CONSTRUCTOR ----
    public VendorRequestDto() { // default empty constructor required for JSON deserialization
    }

    // ---- ALL-ARGS CONSTRUCTOR ----
    public VendorRequestDto(String vendorName, // vendor name
                            String description, // vendor description
                            String contactPersonName, // contact person
                            String contactEmail, // contact email
                            String contactPhone, // contact phone
                            String websiteUrl, // website url
                            BigDecimal totalGoldQuantity, // total gold quantity
                            BigDecimal currentGoldPrice) { // current gold price
        this.vendorName = vendorName; // sets vendor name
        this.description = description; // sets description
        this.contactPersonName = contactPersonName; // sets contact person name
        this.contactEmail = contactEmail; // sets contact email
        this.contactPhone = contactPhone; // sets contact phone
        this.websiteUrl = websiteUrl; // sets website url
        this.totalGoldQuantity = totalGoldQuantity; // sets total gold quantity
        this.currentGoldPrice = currentGoldPrice; // sets current gold price
    }

    // ---- GETTERS ----
    public String getVendorName() { return vendorName; } // returns vendor name

    public String getDescription() { return description; } // returns description

    public String getContactPersonName() { return contactPersonName; } // returns contact person name

    public String getContactEmail() { return contactEmail; } // returns contact email

    public String getContactPhone() { return contactPhone; } // returns contact phone

    public String getWebsiteUrl() { return websiteUrl; } // returns website url

    public BigDecimal getTotalGoldQuantity() { return totalGoldQuantity; } // returns total gold quantity

    public BigDecimal getCurrentGoldPrice() { return currentGoldPrice; } // returns current gold price

    // ---- SETTERS ----
    public void setVendorName(String vendorName) { this.vendorName = vendorName; } // sets vendor name

    public void setDescription(String description) { this.description = description; } // sets description

    public void setContactPersonName(String contactPersonName) { this.contactPersonName = contactPersonName; } // sets contact person name

    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; } // sets contact email

    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; } // sets contact phone

    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; } // sets website url

    public void setTotalGoldQuantity(BigDecimal totalGoldQuantity) { this.totalGoldQuantity = totalGoldQuantity; } // sets total gold quantity

    public void setCurrentGoldPrice(BigDecimal currentGoldPrice) { this.currentGoldPrice = currentGoldPrice; } // sets current gold price
}