package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/*
 * ============================================================
 * Address Entity
 * ============================================================
 *
 * Maps to addresses table in MySQL database.
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
     */
    @NotBlank(message = "Street is required")
    @Column(name = "street",
            nullable = false,
            length = 150)
    private String street;

    /*
     * City
     */
    @NotBlank(message = "City is required")
    @Column(name = "city",
            nullable = false,
            length = 100)
    private String city;

    /*
     * State
     */
    @NotBlank(message = "State is required")
    @Column(name = "state",
            nullable = false,
            length = 100)
    private String state;

    /*
     * Postal Code
     */
    @NotBlank(message = "Postal code is required")
    @Column(name = "postal_code",
            nullable = false,
            length = 20)
    private String postalCode;

    /*
     * Country
     */
    @NotBlank(message = "Country is required")
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