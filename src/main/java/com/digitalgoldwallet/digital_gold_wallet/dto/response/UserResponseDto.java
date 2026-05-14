package com.digitalgoldwallet.digital_gold_wallet.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/*
 * DTO returned after user operations
 */
@Data
@Builder
public class UserResponseDto {

    private Integer userId;

    private String name;

    private String email;

    private BigDecimal balance;
}
