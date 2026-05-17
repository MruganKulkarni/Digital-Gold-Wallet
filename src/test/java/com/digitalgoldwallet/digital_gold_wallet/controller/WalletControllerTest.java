package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.service.WalletService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * ============================================================
 * WALLET CONTROLLER TEST
 * ============================================================
 */
@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * ============================================================
     * TEST GET BALANCE API
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Wallet Balance API")
    void testGetWalletBalance()
            throws Exception {

        WalletBalanceResponseDto response =
                new WalletBalanceResponseDto();

        response.setUserId(1);

        response.setUserName("Tanmay");

        response.setBalance(
                new BigDecimal("10000")
        );

        when(walletService.getWalletBalance(1))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/wallets/1/balance")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.balance")
                                .value(10000)
                );
    }

    /*
     * ============================================================
     * TEST CREDIT WALLET API
     * ============================================================
     */
    @Test
    @DisplayName("Test Credit Wallet API")
    void testCreditWallet()
            throws Exception {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("5000")
        );

        request.setPaymentMethod(
                "Google Pay"
        );

        PaymentResponseDto response =
                new PaymentResponseDto();

        response.setPaymentId(1);

        when(walletService.creditWallet(
                anyInt(),
                any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/wallets/1/credit")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        request
                                                )
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.paymentId")
                                .value(1)
                );
    }

    /*
     * ============================================================
     * TEST DEBIT WALLET API
     * ============================================================
     */
    @Test
    @DisplayName("Test Debit Wallet API")
    void testDebitWallet()
            throws Exception {

        WalletTransactionRequestDto request =
                new WalletTransactionRequestDto();

        request.setAmount(
                new BigDecimal("1000")
        );

        request.setPaymentMethod(
                "PhonePe"
        );

        PaymentResponseDto response =
                new PaymentResponseDto();

        response.setPaymentId(2);

        when(walletService.debitWallet(
                anyInt(),
                any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/v1/wallets/1/debit")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(
                                        objectMapper
                                                .writeValueAsString(
                                                        request
                                                )
                                )
                )
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.paymentId")
                                .value(2)
                );
    }
}