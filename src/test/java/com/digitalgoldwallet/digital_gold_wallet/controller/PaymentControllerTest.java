package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.PaymentService;

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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
 * ============================================================
 * PAYMENT CONTROLLER TEST USING MOCKITO
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private PaymentRequestDto requestDto;
    private PaymentResponseDto responseDto;

    @BeforeEach
    void setUp() {

        requestDto = new PaymentRequestDto();

        requestDto.setUserId(1);

        requestDto.setAmount(
                new BigDecimal("5000")
        );

        requestDto.setPaymentMethod(
                "Google Pay"
        );

        requestDto.setTransactionType(
                "Credited to wallet"
        );

        requestDto.setPaymentStatus(
                "Success"
        );

        responseDto = new PaymentResponseDto();

        responseDto.setPaymentId(1);

        responseDto.setUserId(1);

        responseDto.setAmount(
                new BigDecimal("5000")
        );

        responseDto.setPaymentMethod(
                "Google Pay"
        );

        responseDto.setTransactionType(
                "Credited to wallet"
        );

        responseDto.setPaymentStatus(
                "Success"
        );

        responseDto.setCreatedAt(
                LocalDateTime.now()
        );
    }

    /*
     * ============================================================
     * TEST CREATE PAYMENT
     * ============================================================
     */

    @Test
    @DisplayName("Test Create Payment API")
    public void testCreatePayment() {

        when(paymentService.createPayment(
                any(PaymentRequestDto.class)
        )).thenReturn(responseDto);

        ResponseEntity<PaymentResponseDto> response =
                paymentController.createPayment(requestDto);

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
     * TEST GET PAYMENT BY ID
     * ============================================================
     */

    @Test
    @DisplayName("Test Get Payment By ID API")
    public void testGetPaymentById() {

        when(paymentService.getPaymentById(1))
                .thenReturn(responseDto);

        ResponseEntity<PaymentResponseDto> response =
                paymentController.getPaymentById(1);

        assertNotNull(response);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertEquals(
                1,
                response.getBody().getPaymentId()
        );
    }

    /*
     * ============================================================
     * TEST GET PAYMENTS BY USER ID
     * ============================================================
     */

    @Test
    @DisplayName("Test Get Payments By User ID API")
    public void testGetPaymentsByUserId() {

        when(paymentService.getPaymentsByUserId(1))
                .thenReturn(
                        Arrays.asList(responseDto)
                );

        ResponseEntity<List<PaymentResponseDto>> response =
                paymentController.getPaymentsByUserId(1);

        assertNotNull(response);

        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        assertEquals(
                1,
                response.getBody().get(0).getPaymentId()
        );
    }
}