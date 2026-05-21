package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.exception.InsufficientBalanceException;
import com.digitalgoldwallet.digital_gold_wallet.exception.PaymentNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.repository.PaymentRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.PaymentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * PAYMENT SERVICE TEST USING MOCKITO
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
     */
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private PaymentServiceImpl paymentService;

    /*
     * ============================================================
     * COMMON ENTITIES
     * ============================================================
     */
    private User user;

    private Payment payment;

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

        payment = new Payment();

        payment.setPaymentId(1);

        payment.setUser(user);

        payment.setAmount(
                new BigDecimal("1000")
        );

        payment.setPaymentMethod(
                "Google Pay"
        );

        payment.setTransactionType(
                "Credited to wallet"
        );

        payment.setPaymentStatus(
                "Success"
        );
    }

    /*
     * ============================================================
     * COMMON REQUEST DTO
     * ============================================================
     */
    private PaymentRequestDto buildRequestDto() {

        PaymentRequestDto dto =
                new PaymentRequestDto();

        dto.setUserId(1);

        dto.setAmount(
                new BigDecimal("1000")
        );

        dto.setPaymentMethod(
                "Google Pay"
        );

        dto.setTransactionType(
                "Credited to wallet"
        );

        dto.setPaymentStatus(
                "Success"
        );

        return dto;
    }

    /*
     * ============================================================
     * TEST CREATE PAYMENT SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Payment Success")
    void testCreatePaymentSuccess() {

        PaymentRequestDto dto =
                buildRequestDto();

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(paymentRepository.save(
                any(Payment.class)
        )).thenReturn(payment);

        PaymentResponseDto response =
                paymentService.createPayment(dto);

        assertNotNull(response);

        assertEquals(
                "Success",
                response.getPaymentStatus()
        );

        assertEquals(
                new BigDecimal("1000"),
                response.getAmount()
        );
    }

    /*
     * ============================================================
     * TEST INSUFFICIENT BALANCE
     * ============================================================
     */
    @Test
    @DisplayName("Test Insufficient Balance")
    void testInsufficientBalance() {

        PaymentRequestDto dto =
                buildRequestDto();

        dto.setAmount(
                new BigDecimal("50000")
        );

        dto.setTransactionType(
                "Debited from wallet"
        );

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        assertThrows(
                InsufficientBalanceException.class,

                () ->
                        paymentService
                                .createPayment(dto)
        );
    }

    /*
     * ============================================================
     * TEST GET PAYMENT BY ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Payment By ID")
    void testGetPaymentById() {

        when(paymentRepository.findById(1))
                .thenReturn(Optional.of(payment));

        PaymentResponseDto response =
                paymentService.getPaymentById(1);

        assertNotNull(response);

        assertEquals(
                1,
                response.getPaymentId()
        );

        assertEquals(
                "Google Pay",
                response.getPaymentMethod()
        );
    }

    /*
     * ============================================================
     * TEST PAYMENT NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Payment Not Found")
    void testPaymentNotFound() {

        when(paymentRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentNotFoundException.class,

                () ->
                        paymentService
                                .getPaymentById(1)
        );
    }

    /*
     * ============================================================
     * TEST GET PAYMENTS BY USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Payments By User ID")
    void testGetPaymentsByUserId() {

        when(userRepository.existsById(1))
                .thenReturn(true);

        when(paymentRepository.findPaymentsByUserId(1))
                .thenReturn(
                        Arrays.asList(payment)
                );

        assertEquals(
                1,
                paymentService
                        .getPaymentsByUserId(1)
                        .size()
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

        PaymentRequestDto dto =
                buildRequestDto();

        dto.setPaymentMethod(null);

        assertNull(
                dto.getPaymentMethod()
        );
    }

    /*
     * ============================================================
     * TEST NULL TRANSACTION TYPE
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Transaction Type")
    void testNullTransactionType() {

        PaymentRequestDto dto =
                buildRequestDto();

        dto.setTransactionType(null);

        assertNull(
                dto.getTransactionType()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE PAYMENT AMOUNT
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Payment Amount")
    void testNegativePaymentAmount() {

        PaymentRequestDto dto =
                buildRequestDto();

        dto.setAmount(
                new BigDecimal("-1000")
        );

        assertEquals(
                new BigDecimal("-1000"),
                dto.getAmount()
        );
    }

    /*
     * ============================================================
     * TEST ZERO PAYMENT AMOUNT
     * ============================================================
     */
    @Test
    @DisplayName("Test Zero Payment Amount")
    void testZeroPaymentAmount() {

        PaymentRequestDto dto =
                buildRequestDto();

        dto.setAmount(
                BigDecimal.ZERO
        );

        assertEquals(
                BigDecimal.ZERO,
                dto.getAmount()
        );
    }

    /*
     * ============================================================
     * TEST NULL USER ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Null User Id")
    void testNullUserId() {

        PaymentRequestDto dto =
                buildRequestDto();

        dto.setUserId(null);

        assertNull(
                dto.getUserId()
        );
    }

    /*
     * ============================================================
     * TEST SAVE METHOD CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Save Method Called")
    void testSaveMethodCalled() {

        PaymentRequestDto dto =
                buildRequestDto();

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(paymentRepository.save(
                any(Payment.class)
        )).thenReturn(payment);

        paymentService.createPayment(dto);

        verify(paymentRepository, times(1))
                .save(any(Payment.class));
    }

    /*
     * ============================================================
     * TEST FIND PAYMENT BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find Payment By Id Called")
    void testFindPaymentByIdCalled() {

        when(paymentRepository.findById(1))
                .thenReturn(Optional.of(payment));

        paymentService.getPaymentById(1);

        verify(paymentRepository, times(1))
                .findById(1);
    }

    /*
     * ============================================================
     * TEST FIND PAYMENTS BY USER ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find Payments By User Id Called")
    void testFindPaymentsByUserIdCalled() {

        when(userRepository.existsById(1))
                .thenReturn(true);

        when(paymentRepository.findPaymentsByUserId(1))
                .thenReturn(
                        Arrays.asList(payment)
                );

        paymentService.getPaymentsByUserId(1);

        verify(paymentRepository, times(1))
                .findPaymentsByUserId(1);
    }

}