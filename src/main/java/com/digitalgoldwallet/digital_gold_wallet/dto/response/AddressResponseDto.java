package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import lombok.Builder;
import lombok.Data;

/*
 * DTO returned after address operations
 */
@Data
@Builder
public class AddressResponseDto {

    private Integer addressId;

    private String street;

    private String city;

    private String state;

    private String postalCode;

    private String country;
}
