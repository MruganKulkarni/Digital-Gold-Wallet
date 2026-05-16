package com.digitalgoldwallet.digital_gold_wallet.dto.response;

// Importing BigDecimal for wallet balance
import java.math.BigDecimal;

/*
 * UserResponseDto
 *
 * This DTO is used to send user data
 * back to client as response.
 *
 * Response DTO helps:
 * - avoid exposing entity directly
 * - control output fields
 * - improve API security
 */

public class UserResponseDto {

    /*
     * User ID
     */
    private Integer userId;

    /*
     * User name
     */
    private String name;

    /*
     * User email
     */
    private String email;

    /*
     * Wallet balance
     */
    private BigDecimal balance;

    /*
     * Default Constructor
     */
    public UserResponseDto() {
    }

    /*
     * Parameterized Constructor
     */
    public UserResponseDto(Integer userId,
                           String name,
                           String email,
                           BigDecimal balance) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.balance = balance;
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
}