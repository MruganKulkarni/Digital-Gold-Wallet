package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 * DTO used while creating/updating user
 */
@Data
public class UserRequestDto {

    // User full name
    @NotBlank(message = "Name is required")
    private String name;

    // User email
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    // Address ID linked to user
    @NotNull(message = "Address ID is required")
    private Integer addressId;
}
