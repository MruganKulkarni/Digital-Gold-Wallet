package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;

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

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Wallet Service Test Using Mockito
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
     */
    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentService paymentService;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private WalletServiceImpl walletService;

    /*
     * ============================================================
     * COMMON USER
     * ============================================================
     */
    private User user;

    /*
     * ============================================================
     * SETUP
     * ============================================================
     */
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
     * TEST USER NOT FOUND IN BALANCE
     * ============================================================
     */
    @Test
    @DisplayName("Test User Not Found In Balance")
    void testGetWalletBalance_UserNotFound() {

        when(userRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,

                () ->
                        walletService
                                .getWalletBalance(999)
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

        when(paymentService.createPayment(any()))
                .thenReturn(paymentResponse);

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
     * TEST CREDIT WALLET USER NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Credit Wallet User Not Found")
    void testCreditWallet_UserNotFound() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("5000")
        );

        when(userRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,

                () ->
                        walletService
                                .creditWallet(999,request)
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

        when(paymentService.createPayment(any()))
                .thenReturn(paymentResponse);

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

    /*
     * ============================================================
     * TEST DEBIT WALLET USER NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Debit Wallet User Not Found")
    void testDebitWallet_UserNotFound() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("1000")
        );

        when(userRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,

                () ->
                        walletService
                                .debitWallet(999,request)
        );
    }

    /*
     * ============================================================
     * TEST NULL PAYMENT METHOD
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Payment Method")
    void testNullPaymentMethod() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setPaymentMethod(null);

        assertNull(
                request.getPaymentMethod()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE AMOUNT
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Amount")
    void testNegativeAmount() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("-1000")
        );

        assertEquals(
                new BigDecimal("-1000"),
                request.getAmount()
        );
    }

    /*
     * ============================================================
     * TEST ZERO AMOUNT
     * ============================================================
     */
    @Test
    @DisplayName("Test Zero Amount")
    void testZeroAmount() {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                BigDecimal.ZERO
        );

        assertEquals(
                BigDecimal.ZERO,
                request.getAmount()
        );
    }

    /*
     * ============================================================
     * TEST SAVE PAYMENT METHOD CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Payment Service Called")
    void testPaymentServiceCalled() {

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

        when(paymentService.createPayment(any()))
                .thenReturn(paymentResponse);

        walletService.debitWallet(
                1,
                request
        );

        verify(paymentService, times(1))
                .createPayment(any());
    }

    /*
     * ============================================================
     * TEST FIND USER BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find User By Id Called")
    void testFindUserByIdCalled() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        walletService.getWalletBalance(1);

        verify(userRepository, times(1))
                .findById(1);
    }

}