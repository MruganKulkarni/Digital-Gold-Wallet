package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.WalletServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * ============================================================
 * WALLET SERVICE TEST USING MOCKITO
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private WalletServiceImpl walletService;

    private User user;

    @BeforeEach
    void setUp() {

        user = new User();

        user.setUserId(1);

        user.setName(
                "Tanmay"
        );

        user.setBalance(
                new BigDecimal("10000")
        );
    }

    /*
     * ============================================================
     * TEST GET WALLET BALANCE
     * ============================================================
     */

    @Test
    @DisplayName("Test Get Wallet Balance")
    void testGetWalletBalance() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        WalletBalanceResponseDto response =
                walletService.getWalletBalance(1);

        assertNotNull(response);

        assertEquals(
                new BigDecimal("10000"),
                response.getBalance()
        );

        assertEquals(
                "Tanmay",
                response.getUserName()
        );
    }

    /*
     * ============================================================
     * TEST CREDIT WALLET
     * ============================================================
     */

    @Test
    @DisplayName("Test Credit Wallet")
    void testCreditWallet() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("5000")
        );

        request.setPaymentMethod(
                "Google Pay"
        );

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        PaymentResponseDto paymentResponse =
                new PaymentResponseDto();

        paymentResponse.setPaymentId(1);

        when(paymentService.createPayment(
                any()
        )).thenReturn(paymentResponse);

        PaymentResponseDto response =
                walletService.creditWallet(
                        1,
                        request
                );

        assertNotNull(response);

        assertEquals(
                1,
                response.getPaymentId()
        );

        assertEquals(
                new BigDecimal("15000"),
                user.getBalance()
        );
    }

    /*
     * ============================================================
     * TEST DEBIT WALLET
     * ============================================================
     */

    @Test
    @DisplayName("Test Debit Wallet")
    void testDebitWallet() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("1000")
        );

        request.setPaymentMethod(
                "PhonePe"
        );

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        PaymentResponseDto paymentResponse =
                new PaymentResponseDto();

        paymentResponse.setPaymentId(2);

        when(paymentService.createPayment(
                any()
        )).thenReturn(paymentResponse);

        PaymentResponseDto response =
                walletService.debitWallet(
                        1,
                        request
                );

        assertNotNull(response);

        assertEquals(
                2,
                response.getPaymentId()
        );

        assertEquals(
                new BigDecimal("9000"),
                user.getBalance()
        );
    }
}