package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * ============================================================
 * User Entity
 * ============================================================
 *
 * Maps to users table in MySQL database.
 * ============================================================
 */

@Entity
@Table(name = "users")
public class User implements Comparable<User> {

    /*
     * Primary Key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /*
     * User name
     */
    @NotBlank(message = "User name is required")
    @Column(name = "name",
            nullable = false,
            length = 100)
    private String name;

    /*
     * User email
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email",
            nullable = false,
            unique = true,
            length = 100)
    private String email;

    /*
     * Wallet balance
     */
    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.0",
            message = "Balance cannot be negative")
    @Column(name = "balance",
            nullable = false,
            precision = 18,
            scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    /*
     * Foreign Key -> addresses(address_id)
     *
     * Many users can belong to one address.
     */
    @NotNull(message = "Address is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id",
            nullable = false)
    private Address address;

    /*
     * User account creation timestamp
     */
    @Column(name = "created_at",
            updatable = false)
    private LocalDateTime createdAt;

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
                Address address,
                LocalDateTime createdAt) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.address = address;
        this.createdAt = createdAt;
    }

    /*
     * Getter for userId
     */
    public Integer getUserId() {
        return userId;
    }

    /*
     * Setter for userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /*
     * Getter for name
     */
    public String getName() {
        return name;
    }

    /*
     * Setter for name
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * Getter for email
     */
    public String getEmail() {
        return email;
    }

    /*
     * Setter for email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Getter for balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /*
     * Setter for balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /*
     * Getter for address
     */
    public Address getAddress() {
        return address;
    }

    /*
     * Setter for address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /*
     * Getter for createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /*
     * Setter for createdAt
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /*
     * equals()
     *
     * Two users are equal if userId matches.
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

        User user = (User) obj;

        return userId != null
                && userId.equals(user.userId);
    }

    /*
     * hashCode()
     */
    @Override
    public int hashCode() {

        return userId != null
                ? userId.hashCode()
                : 0;
    }

    /*
     * compareTo()
     *
     * Sort users alphabetically by name.
     */
    @Override
    public int compareTo(User otherUser) {

        return this.name.compareTo(otherUser.name);
    }

    /*
     * Automatically sets creation timestamp
     * before inserting into DB.
     */
    @PrePersist
    protected void onCreate() {

        this.createdAt = LocalDateTime.now();
    }
}