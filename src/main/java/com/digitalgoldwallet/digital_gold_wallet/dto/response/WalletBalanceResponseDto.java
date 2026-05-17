package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;

/*
 * Response DTO for wallet balance
 */
public class WalletBalanceResponseDto {

    private Integer userId;

    private String userName;

    private BigDecimal balance;

    public WalletBalanceResponseDto() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}