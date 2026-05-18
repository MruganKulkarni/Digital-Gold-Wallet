package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.service.WalletService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/*
 * ============================================================
 * WALLET CONTROLLER TEST USING MOCKITO
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class WalletControllerTest {

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private WalletBalanceResponseDto balanceResponse;
    private WalletTransactionRequestDto requestDto;
    private PaymentResponseDto paymentResponse;

    @BeforeEach
    void setUp() {

        balanceResponse =
                new WalletBalanceResponseDto();

        balanceResponse.setUserId(1);

        balanceResponse.setUserName(
                "Tanmay"
        );

        balanceResponse.setBalance(
                new BigDecimal("10000")
        );

        requestDto =
                new WalletTransactionRequestDto();

        requestDto.setAmount(
                new BigDecimal("5000")
        );

        requestDto.setPaymentMethod(
                "Google Pay"
        );

        paymentResponse =
                new PaymentResponseDto();

        paymentResponse.setPaymentId(1);
    }

    /*
     * ============================================================
     * TEST GET BALANCE API
     * ============================================================
     */

    @Test
    @DisplayName("Test Get Wallet Balance API")
    void testGetWalletBalance() {

        when(walletService.getWalletBalance(1))
                .thenReturn(balanceResponse);

        ResponseEntity<WalletBalanceResponseDto> response =
                walletController.getWalletBalance(1);

        assertNotNull(response);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertEquals(
                new BigDecimal("10000"),
                response.getBody().getBalance()
        );
    }

    /*
     * ============================================================
     * TEST CREDIT WALLET API
     * ============================================================
     */

    @Test
    @DisplayName("Test Credit Wallet API")
    void testCreditWallet() {

        when(walletService.creditWallet(
                anyInt(),
                any(WalletTransactionRequestDto.class)
        )).thenReturn(paymentResponse);

        ResponseEntity<PaymentResponseDto> response =
                walletController.creditWallet(
                        1,
                        requestDto
                );

        assertNotNull(response);

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertEquals(
                1,
                response.getBody().getPaymentId()
        );
    }

    /*
     * ============================================================
     * TEST DEBIT WALLET API
     * ============================================================
     */

    @Test
    @DisplayName("Test Debit Wallet API")
    void testDebitWallet() {

        PaymentResponseDto debitResponse =
                new PaymentResponseDto();

        debitResponse.setPaymentId(2);

        when(walletService.debitWallet(
                anyInt(),
                any(WalletTransactionRequestDto.class)
        )).thenReturn(debitResponse);

        ResponseEntity<PaymentResponseDto> response =
                walletController.debitWallet(
                        1,
                        requestDto
                );

        assertNotNull(response);

        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );

        assertEquals(
                2,
                response.getBody().getPaymentId()
        );
    }
}