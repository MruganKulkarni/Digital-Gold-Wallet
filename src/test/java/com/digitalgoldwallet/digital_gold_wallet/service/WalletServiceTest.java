package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.WalletServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * ============================================================
 * WALLET SERVICE TEST
 * ============================================================
 */
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

        MockitoAnnotations.openMocks(this);

        user = new User();

        user.setUserId(1);

        user.setName("Tanmay");

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
    }

    /*
     * ============================================================
     * TEST CREDIT WALLET
     * ============================================================
     */
    @Test
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

        when(paymentService.createPayment(
                any()))
                .thenReturn(
                        new PaymentResponseDto()
                );

        PaymentResponseDto response =
                walletService.creditWallet(
                        1,
                        request
                );

        assertNotNull(response);
    }

    /*
     * ============================================================
     * TEST DEBIT WALLET
     * ============================================================
     */
    @Test
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

        when(paymentService.createPayment(
                any()))
                .thenReturn(
                        new PaymentResponseDto()
                );

        PaymentResponseDto response =
                walletService.debitWallet(
                        1,
                        request
                );

        assertNotNull(response);
    }
}