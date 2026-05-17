package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

/*
 * Mapper class for Wallet module
 */
public class WalletMapper {

    /*
     * Convert User entity -> WalletBalanceResponseDto
     */
    public static WalletBalanceResponseDto
    mapToWalletBalanceResponseDto(
            User user
    ) {

        WalletBalanceResponseDto response =
                new WalletBalanceResponseDto();

        response.setUserId(
                user.getUserId()
        );

        response.setUserName(
                user.getName()
        );

        response.setBalance(
                user.getBalance()
        );

        return response;
    }
}