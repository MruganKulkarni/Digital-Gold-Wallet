package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank; // validates field is not null, empty, or blank
import jakarta.validation.constraints.Size; // validates field length does not exceed max characters

import java.util.List;

/*
 * ============================================================
 * Address Entity
 * ============================================================
 *
 * Maps to addresses table in MySQL database.
 *
 * @Size added to every String field to match the column
 * lengths defined in @Column(length = X).
 * This ensures entity-level validation catches oversized
 * values before they ever reach the DB layer.
 * ============================================================
 */
@Entity
@Table(name = "addresses")
public class Address implements Comparable<Address> {

    /*
     * Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    /*
     * Street
     * DB column: VARCHAR(150)
     */
    @NotBlank(message = "Street is required") // must not be null, empty, or whitespace-only
    @Size(max = 150, message = "Street cannot exceed 150 characters") // must match @Column(length = 150)
    @Column(name = "street",
            nullable = false,
            length = 150)
    private String street;

    /*
     * City
     * DB column: VARCHAR(100)
     */
    @NotBlank(message = "City is required") // must not be null, empty, or whitespace-only
    @Size(max = 100, message = "City cannot exceed 100 characters") // must match @Column(length = 100)
    @Column(name = "city",
            nullable = false,
            length = 100)
    private String city;

    /*
     * State
     * DB column: VARCHAR(100)
     */
    @NotBlank(message = "State is required") // must not be null, empty, or whitespace-only
    @Size(max = 100, message = "State cannot exceed 100 characters") // must match @Column(length = 100)
    @Column(name = "state",
            nullable = false,
            length = 100)
    private String state;

    /*
     * Postal Code
     * DB column: VARCHAR(20)
     */
    @NotBlank(message = "Postal code is required") // must not be null, empty, or whitespace-only
    @Size(max = 20, message = "Postal code cannot exceed 20 characters") // must match @Column(length = 20)
    @Column(name = "postal_code",
            nullable = false,
            length = 20)
    private String postalCode;

    /*
     * Country
     * DB column: VARCHAR(100)
     */
    @NotBlank(message = "Country is required") // must not be null, empty, or whitespace-only
    @Size(max = 100, message = "Country cannot exceed 100 characters") // must match @Column(length = 100)
    @Column(name = "country",
            nullable = false,
            length = 100)
    private String country;

    /*
     * One Address -> Many Users
     */
    @OneToMany(mappedBy = "address",
            fetch = FetchType.LAZY)
    private List<User> users;

    /*
     * Default Constructor
     */
    public Address() {
    }

    /*
     * Parameterized Constructor
     */
    public Address(Integer addressId,
                   String street,
                   String city,
                   String state,
                   String postalCode,
                   String country,
                   List<User> users) {

        this.addressId = addressId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.users = users;
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

    /*
     * Getter for users
     */
    public List<User> getUsers() {
        return users;
    }

    /*
     * Setter for users
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /*
     * equals()
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null
                || getClass() != obj.getClass()) {
            return false;
        }

        Address address = (Address) obj;

        return addressId != null
                && addressId.equals(address.addressId);
    }

    /*
     * hashCode()
     */
    @Override
    public int hashCode() {

        return addressId != null
                ? addressId.hashCode()
                : 0;
    }

    /*
     * compareTo()
     *
     * Sort addresses alphabetically by city.
     */
    @Override
    public int compareTo(Address otherAddress) {

        return this.city.compareTo(otherAddress.city);
    }
}