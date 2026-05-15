package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;

import java.util.List;

/*
 * Address Entity Class
 *
 * This class maps to the "addresses" table in database.
 */

@Entity
@Table(name = "addresses")
public class Address implements Comparable<Address> {

    /*
     * Primary Key
     * Auto incremented address ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    /*
     * Address fields
     */
    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    /*
     * One address can belong to many users
     */
    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
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
     * Getters and Setters
     */

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /*
     * equals()
     *
     * Used to compare two Address objects logically
     * using addressId.
     */
    @Override
    public boolean equals(Object obj) {

        // checks if same object reference
        if (this == obj) {
            return true;
        }

        // checks null and class type
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // type casting
        Address address = (Address) obj;

        // compare address IDs
        return addressId != null
                && addressId.equals(address.addressId);
    }

    /*
     * hashCode()
     *
     * Generates hash value for Address object.
     */
    @Override
    public int hashCode() {

        // generate hash using addressId
        return addressId != null ? addressId.hashCode() : 0;
    }

    /*
     * compareTo()
     *
     * Used for sorting Address objects alphabetically by city.
     */
    @Override
    public int compareTo(Address otherAddress) {

        // compare city names
        return this.city.compareTo(otherAddress.city);
    }
}