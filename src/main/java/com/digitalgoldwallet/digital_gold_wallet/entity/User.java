package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 * User Entity Class
 *
 * Maps to users table in database.
 */

@Entity
@Table(name = "users")
public class User implements Comparable<User> {

    /*
     * Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /*
     * User full name
     */
    @NotBlank(message = "User name is required")
    @Column(nullable = false)
    private String name;

    /*
     * User email
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Wallet balance
     */
    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.0", message = "Balance cannot be negative")
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    /*
     * Many users can belong to one address
     */
    @NotNull(message = "Address is required")
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

    public @Email(message = "Invalid email format") String getEmail() {
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
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        User user = (User) obj;

        return userId != null && userId.equals(user.userId);
    }

    /*
     * hashCode()
     */
    @Override
    public int hashCode() {

        return userId != null ? userId.hashCode() : 0;
    }

    /*
     * compareTo()
     */
    @Override
    public int compareTo(User otherUser) {

        return this.name.compareTo(otherUser.name);
    }
}