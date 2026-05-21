package com.digitalgoldwallet.digital_gold_wallet.dto.request;

// Importing validation annotations
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Importing BigDecimal for wallet balance
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/*
 * UserRequestDto
 *
 * This DTO is used to receive user input data
 * from client requests.
 *
 * DTO = Data Transfer Object
 *
 * Request DTOs are used for:
 * - input validation
 * - clean architecture
 * - separating entity from API layer
 */
@Schema(description = "Request body for user creation/update")
public class UserRequestDto {

    /*
     * User name
     *
     * @NotBlank ensures:
     * - field is not null
     * - field is not empty
     * - field is not just spaces
     */
    @Schema(description = "User's full name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "User name is required")
    private String name;

    /*
     * User email
     *
     * @NotBlank ensures email is mandatory
     * @Email ensures proper email format
     */
    @Schema(description = "User's email address", example = "johndoe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    /*
     * Wallet balance
     *
     * @NotNull ensures value exists
     * @DecimalMin ensures balance is not negative
     */
    @Schema(description = "Initial wallet balance", example = "1000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Balance cannot be null")
    @DecimalMin(
            value = "0.0",
            message = "Balance cannot be negative"
    )
    private BigDecimal balance;

    /*
     * Address ID
     *
     * Used to map user with existing address.
     */
    @Schema(description = "Address ID to link with user", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Address ID is required")
    private Integer addressId;

    /*
     * Default Constructor
     *
     * Required by Spring Boot and Jackson.
     */
    public UserRequestDto() {
    }

    /*
     * Parameterized Constructor
     *
     * Used to initialize all fields together.
     */
    public UserRequestDto(String name,
                          String email,
                          BigDecimal balance,
                          Integer addressId) {

        this.name = name;
        this.email = email;
        this.balance = balance;
        this.addressId = addressId;
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
}