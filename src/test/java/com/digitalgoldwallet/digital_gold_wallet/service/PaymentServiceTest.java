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
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private User user;

    private Payment payment;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        user = new User();

        user.setUserId(1);

        user.setName("Tanmay");

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
     * ==========================================
     * TEST CREATE PAYMENT SUCCESS
     * ==========================================
     */
    @Test
    void testCreatePaymentSuccess() {

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

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(paymentRepository.save(any(
                Payment.class)))
                .thenReturn(payment);

        PaymentResponseDto response =
                paymentService.createPayment(dto);

        assertNotNull(response);

        assertEquals(
                "Success",
                response.getPaymentStatus()
        );
    }

    /*
     * ==========================================
     * TEST INSUFFICIENT BALANCE
     * ==========================================
     */
    @Test
    void testInsufficientBalance() {

        PaymentRequestDto dto =
                new PaymentRequestDto();

        dto.setUserId(1);

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
                () -> paymentService
                        .createPayment(dto)
        );
    }

    /*
     * ==========================================
     * TEST GET PAYMENT BY ID
     * ==========================================
     */
    @Test
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
    }

    /*
     * ==========================================
     * TEST PAYMENT NOT FOUND
     * ==========================================
     */
    @Test
    void testPaymentNotFound() {

        when(paymentRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                PaymentNotFoundException.class,
                () -> paymentService
                        .getPaymentById(1)
        );
    }

    /*
     * ==========================================
     * TEST GET PAYMENTS BY USER
     * ==========================================
     */
    @Test
    void testGetPaymentsByUserId() {

        when(userRepository.existsById(1))
                .thenReturn(true);

        when(paymentRepository
                .findPaymentsByUserId(1))
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
}