package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;

import java.util.List;

/*
 * Address Entity Class
 *
 * Maps to addresses table.
 */

@Entity
@Table(name = "addresses")
public class Address {

    /*
     * Primary Key
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
     * Default constructor
     */
    public Address() {
    }

    /*
     * Parameterized constructor
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
}