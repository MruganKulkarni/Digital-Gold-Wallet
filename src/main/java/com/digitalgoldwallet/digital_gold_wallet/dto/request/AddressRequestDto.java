package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/*
 * DTO used while creating/updating address
 */
@Data
public class AddressRequestDto {

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;
}
