package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.NotBlank; // validates field is not null, empty, or blank
import jakarta.validation.constraints.Size; // validates field length does not exceed max characters

/*
 * AddressRequestDto
 *
 * Used to receive address request data from client.
 *
 * @Size added to every field to match the column lengths
 * defined in the Address entity and MySQL schema.
 * This prevents DataTruncationException (500) and returns
 * a proper 400 Bad Request with a clear error message instead.
 */
public class AddressRequestDto {

    /*
     * Street name
     * DB column: VARCHAR(150) — matches Address entity @Column(length = 150)
     */
    @NotBlank(message = "Street is required") // field must not be null, empty, or whitespace-only
    @Size(max = 150, message = "Street cannot exceed 150 characters") // matches DB column length 150
    private String street;

    /*
     * City name
     * DB column: VARCHAR(100) — matches Address entity @Column(length = 100)
     */
    @NotBlank(message = "City is required") // field must not be null, empty, or whitespace-only
    @Size(max = 100, message = "City cannot exceed 100 characters") // matches DB column length 100
    private String city;

    /*
     * State name
     * DB column: VARCHAR(100) — matches Address entity @Column(length = 100)
     */
    @NotBlank(message = "State is required") // field must not be null, empty, or whitespace-only
    @Size(max = 100, message = "State cannot exceed 100 characters") // matches DB column length 100
    private String state;

    /*
     * Postal code
     * DB column: VARCHAR(20) — matches Address entity @Column(length = 20)
     */
    @NotBlank(message = "Postal code is required") // field must not be null, empty, or whitespace-only
    @Size(max = 20, message = "Postal code cannot exceed 20 characters") // matches DB column length 20
    private String postalCode;

    /*
     * Country name
     * DB column: VARCHAR(100) — matches Address entity @Column(length = 100)
     */
    @NotBlank(message = "Country is required") // field must not be null, empty, or whitespace-only
    @Size(max = 100, message = "Country cannot exceed 100 characters") // matches DB column length 100
    private String country;

    /*
     * Default Constructor
     */
    public AddressRequestDto() {
    }

    /*
     * Parameterized Constructor
     */
    public AddressRequestDto(String street,
                             String city,
                             String state,
                             String postalCode,
                             String country) {

        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    /*
     * Getter for street
     */
    public String getStreet() {
        return street;
    }

    /*
     * Setter for street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /*
     * Getter for city
     */
    public String getCity() {
        return city;
    }

    /*
     * Setter for city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /*
     * Getter for state
     */
    public String getState() {
        return state;
    }

    /*
     * Setter for state
     */
    public void setState(String state) {
        this.state = state;
    }

    /*
     * Getter for postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /*
     * Setter for postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /*
     * Getter for country
     */
    public String getCountry() {
        return country;
    }

    /*
     * Setter for country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}