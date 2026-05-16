package com.digitalgoldwallet.digital_gold_wallet.dto.response;

/*
 * AddressResponseDto
 *
 * Used to send address response data
 * back to client.
 */

public class AddressResponseDto {

    /*
     * Address ID
     */
    private Integer addressId;

    /*
     * Street name
     */
    private String street;

    /*
     * City name
     */
    private String city;

    /*
     * State name
     */
    private String state;

    /*
     * Postal code
     */
    private String postalCode;

    /*
     * Country name
     */
    private String country;

    /*
     * Default Constructor
     */
    public AddressResponseDto() {
    }

    /*
     * Parameterized Constructor
     */
    public AddressResponseDto(Integer addressId,
                              String street,
                              String city,
                              String state,
                              String postalCode,
                              String country) {

        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    /*
     * Getter for addressId
     */
    public Integer getAddressId() {
        return addressId;
    }

    /*
     * Setter for addressId
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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