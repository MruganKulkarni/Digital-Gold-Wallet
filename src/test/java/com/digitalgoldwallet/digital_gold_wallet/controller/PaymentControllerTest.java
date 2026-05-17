package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDTO;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDTO;
import com.digitalgoldwallet.digital_gold_wallet.service.PaymentService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * ============================================================
 * PAYMENT CONTROLLER TEST
 * ============================================================
 */

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * ============================================================
     * TEST CREATE PAYMENT
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Payment API")
    public void testCreatePayment()
            throws Exception {

        PaymentRequestDTO requestDto =
                new PaymentRequestDTO();

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

        PaymentResponseDTO responseDto =
                new PaymentResponseDTO();

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

        when(paymentService.createPayment(
                any(PaymentRequestDTO.class)))
                .thenReturn(responseDto);

        mockMvc.perform(
                        post("/api/v1/payments")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        requestDto
                                                )
                                )
                )
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        jsonPath("$.paymentId")
                                .value(1)
                );
    }

    /*
     * ============================================================
     * TEST GET PAYMENT BY ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Payment By ID API")
    public void testGetPaymentById()
            throws Exception {

        PaymentResponseDTO responseDto =
                new PaymentResponseDTO();

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

        when(paymentService.getPaymentById(1))
                .thenReturn(responseDto);

        mockMvc.perform(
                        get("/api/v1/payments/1")
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.paymentId")
                                .value(1)
                );
    }

    /*
     * ============================================================
     * TEST GET PAYMENTS BY USER ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Payments By User ID API")
    public void testGetPaymentsByUserId()
            throws Exception {

        PaymentResponseDTO responseDto =
                new PaymentResponseDTO();

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

        when(paymentService.getPaymentsByUserId(1))
                .thenReturn(
                        Arrays.asList(responseDto)
                );

        mockMvc.perform(
                        get("/api/v1/users/1/payments")
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$[0].paymentId")
                                .value(1)
                );
    }
}