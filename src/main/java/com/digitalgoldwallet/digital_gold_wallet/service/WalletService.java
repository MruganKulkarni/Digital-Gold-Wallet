package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

/*
 * ============================================================
 * WALLET SERVICE
 * ============================================================
 */
public interface WalletService {

    /*
     * Get wallet balance
     */
    WalletBalanceResponseDto
    getWalletBalance(
            Integer userId
    );

    /*
     * Credit wallet
     */
    PaymentResponseDto
    creditWallet(
            Integer userId,
            WalletTransactionRequestDto requestDto
    );

    /*
     * Debit wallet
     */
    PaymentResponseDto
    debitWallet(
            Integer userId,
            WalletTransactionRequestDto requestDto
    );
}