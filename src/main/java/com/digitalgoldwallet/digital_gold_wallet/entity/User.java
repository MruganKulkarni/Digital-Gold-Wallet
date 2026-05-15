package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/*
 * User Entity Class
 *
 * This class maps to the "users" table in MySQL.
 * Each object represents one row in database.
 */

@Entity
@Table(name = "users")
public class User {

    /*
     * Primary Key
     * Auto incremented user ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /*
     * User full name
     */
    @Column(nullable = false)
    private String name;

    /*
     * User email
     * unique = true ensures duplicate emails are not allowed
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Wallet balance of user
     */
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    /*
     * Many users can belong to one address
     *
     * FetchType.LAZY:
     * Address loads only when needed
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    /*
     * Default constructor
     * Required by Hibernate/JPA
     */
    public User() {
    }

    /*
     * Parameterized constructor
     */
    public User(Integer userId,
                String name,
                String email,
                BigDecimal balance,
                Address address) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.address = address;
    }

    /*
     * Getters and Setters
     */

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}