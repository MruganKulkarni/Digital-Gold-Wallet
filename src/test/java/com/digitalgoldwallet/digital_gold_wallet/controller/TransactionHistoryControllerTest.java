package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.TransactionHistoryService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
============================================================
Transaction History Controller Tests
============================================================
*/

@WebMvcTest(TransactionHistoryController.class)
class TransactionHistoryControllerTest {



    @Autowired
    MockMvc mockMvc;



    @MockBean
    TransactionHistoryService service;



    @Test
    void getUserTransactions()
            throws Exception {


        List<TransactionHistoryResponseDto> list=

                List.of(

                        new TransactionHistoryResponseDto(
                                1,
                                1,
                                1,
                                "BUY",
                                "SUCCESS",
                                BigDecimal.ONE,
                                BigDecimal.valueOf(1000),
                                LocalDateTime.now()
                        )
                );



        when(
                service.getTransactionsByUser(1)
        )

                .thenReturn(
                        list
                );



        mockMvc.perform(

                        get(
                                "/api/v1/users/1/transactions"
                        )
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$[0].transactionType")
                                .value("BUY")
                );

    }




    @Test
    void getBranchTransactions()
            throws Exception {


        List<TransactionHistoryResponseDto> list=

                List.of(

                        new TransactionHistoryResponseDto(
                                1,
                                1,
                                1,
                                "SELL",
                                "SUCCESS",
                                BigDecimal.ONE,
                                BigDecimal.valueOf(800),
                                LocalDateTime.now()
                        )
                );



        when(
                service.getTransactionsByBranch(1)
        )
                .thenReturn(list);



        mockMvc.perform(

                        get(
                                "/api/v1/branches/1/transactions"
                        )
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$[0].transactionType")
                                .value("SELL")
                );

    }

    /*
     * ==========================================================
     * USER TRANSACTIONS EMPTY
     * ==========================================================
     */

    @Test
    void getUserTransactions_Empty()
            throws Exception {

        when(
                service.getTransactionsByUser(1)
        )

                .thenReturn(
                        Collections.emptyList()
                );


        mockMvc.perform(

                        get(
                                "/api/v1/users/1/transactions"
                        )
                )

                .andExpect(
                        status().isOk()
                )

                .andExpect(
                        jsonPath("$")
                                .isEmpty()
                );

    }



    /*
     * ==========================================================
     * BRANCH TRANSACTIONS EMPTY
     * ==========================================================
     */

    @Test
    void getBranchTransactions_Empty()
            throws Exception {

        when(
                service.getTransactionsByBranch(1)
        )

                .thenReturn(
                        Collections.emptyList()
                );


        mockMvc.perform(

                        get(
                                "/api/v1/branches/1/transactions"
                        )
                )

                .andExpect(
                        status().isOk()
                )

                .andExpect(
                        jsonPath("$")
                                .isEmpty()
                );

    }

}