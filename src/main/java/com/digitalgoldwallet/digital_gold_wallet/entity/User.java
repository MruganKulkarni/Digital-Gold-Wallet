package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

/*
 * User Entity Class
 *
 * This class maps to the "users" table in database.
 */

@Entity
@Table(name = "users")
public class User implements Comparable<User> {

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
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Wallet balance
     */
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    /*
     * Many users can belong to one address
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    /*
     * Default Constructor
     */
    public User() {
    }

    /*
     * Parameterized Constructor
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

    /*
     * equals()
     *
     * Used to compare two User objects logically
     * using userId.
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
        User user = (User) obj;

        // compare user IDs
        return userId != null && userId.equals(user.userId);
    }

    /*
     * hashCode()
     *
     * Generates hash value for User object.
     */
    @Override
    public int hashCode() {

        // generate hash using userId
        return userId != null ? userId.hashCode() : 0;
    }

    /*
     * compareTo()
     *
     * Used for sorting User objects alphabetically by name.
     */
    @Override
    public int compareTo(User otherUser) {

        // compare user names
        return this.name.compareTo(otherUser.name);
    }
}