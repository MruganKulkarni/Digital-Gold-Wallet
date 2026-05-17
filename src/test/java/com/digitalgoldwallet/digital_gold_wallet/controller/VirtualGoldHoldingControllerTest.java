package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VirtualGoldHoldingRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VirtualGoldHoldingResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.VirtualGoldHoldingService;
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
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;


/*
============================================================
Virtual Gold Controller Test
============================================================

Tests:

POST /gold/virtual/buy

POST /gold/virtual/sell

GET user holdings

GET branch holdings

============================================================
*/

@WebMvcTest(VirtualGoldHoldingController.class)
class VirtualGoldHoldingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private VirtualGoldHoldingService service;


    /*
    ------------------------------------------------------------
    BUY GOLD SUCCESS
    ------------------------------------------------------------
     */

    @Test
    @DisplayName("Buy virtual gold successfully")
    void buyGold_Success() throws Exception {

        VirtualGoldHoldingRequestDto request =
                new VirtualGoldHoldingRequestDto(
                        1,
                        1,
                        BigDecimal.valueOf(5)
                );

        VirtualGoldHoldingResponseDto response =
                new VirtualGoldHoldingResponseDto(
                        1,
                        1,
                        1,
                        BigDecimal.valueOf(5),
                        LocalDateTime.now()
                );


        when(service.buyGold(request))
                .thenReturn(response);


        mockMvc.perform(

                        post("/api/v1/gold/virtual/buy")

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )

                .andExpect(status().isOk());

    }



    /*
    ------------------------------------------------------------
    SELL GOLD SUCCESS
    ------------------------------------------------------------
     */

    @Test
    void sellGold_Success()
            throws Exception {


        VirtualGoldHoldingRequestDto request =
                new VirtualGoldHoldingRequestDto(
                        1,
                        1,
                        BigDecimal.valueOf(2)
                );


        VirtualGoldHoldingResponseDto response =
                new VirtualGoldHoldingResponseDto(
                        1,
                        1,
                        1,
                        BigDecimal.valueOf(3),
                        LocalDateTime.now()
                );


        when(
                service.sellGold(request)
        )

                .thenReturn(
                        response
                );


        mockMvc.perform(

                        post(
                                "/api/v1/gold/virtual/sell"
                        )

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )

                .andExpect(status().isOk());

    }



    /*
    ------------------------------------------------------------
    USER HOLDINGS
    ------------------------------------------------------------
     */

    @Test
    void getHoldingsByUser_Success()
            throws Exception {


        List<VirtualGoldHoldingResponseDto> holdings =

                List.of(

                        new VirtualGoldHoldingResponseDto(
                                1,
                                1,
                                1,
                                BigDecimal.valueOf(10),
                                LocalDateTime.now()
                        )
                );



        when(
                service.getHoldingsByUser(1)
        )

                .thenReturn(holdings);



        mockMvc.perform(

                        get(
                                "/api/v1/users/1/gold/virtual"
                        )
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$[0].holdingId")
                                .value(1)
                );

    }




    /*
    ------------------------------------------------------------
    BRANCH HOLDINGS
    ------------------------------------------------------------
     */

    @Test
    void getHoldingsByBranch_Success()
            throws Exception {


        List<VirtualGoldHoldingResponseDto> holdings =

                List.of(

                        new VirtualGoldHoldingResponseDto(
                                1,
                                1,
                                1,
                                BigDecimal.valueOf(20),
                                LocalDateTime.now()
                        )
                );



        when(
                service.getHoldingsByBranch(1)
        )

                .thenReturn(
                        holdings
                );



        mockMvc.perform(

                        get(
                                "/api/v1/branches/1/gold/virtual"
                        )
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$[0].quantity")
                                .value(20)
                );

    }

    /*
     * ==========================================================
     * BUY GOLD VALIDATION FAILURE
     * ==========================================================
     */

    @Test
    void buyGold_InvalidRequest()
            throws Exception {

        VirtualGoldHoldingRequestDto request =
                new VirtualGoldHoldingRequestDto(
                        null,
                        null,
                        null
                );


        mockMvc.perform(

                        post("/api/v1/gold/virtual/buy")

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content(
                                        objectMapper.writeValueAsString(
                                                request
                                        )
                                )
                )

                .andExpect(
                        status().isBadRequest()
                );

    }



    /*
     * ==========================================================
     * GET USER HOLDINGS SUCCESS
     * ==========================================================
     */

    @Test
    void getUserHoldings_Success()
            throws Exception {

        List<VirtualGoldHoldingResponseDto> list =

                List.of(

                        new VirtualGoldHoldingResponseDto(
                                1,
                                1,
                                1,
                                BigDecimal.valueOf(5),
                                LocalDateTime.now()
                        )
                );



        when(
                service.getHoldingsByUser(1)
        )

                .thenReturn(
                        list
                );



        mockMvc.perform(

                        get(
                                "/api/v1/users/1/gold/virtual"
                        )
                )

                .andExpect(
                        status().isOk()
                )

                .andExpect(
                        jsonPath("$[0].holdingId")
                                .value(1)
                );

    }



    /*
     * ==========================================================
     * GET USER HOLDINGS EMPTY
     * ==========================================================
     */

    @Test
    void getUserHoldings_Empty()
            throws Exception {

        when(
                service.getHoldingsByUser(1)
        )

                .thenReturn(
                        Collections.emptyList()
                );


        mockMvc.perform(

                        get(
                                "/api/v1/users/1/gold/virtual"
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
     * GET BRANCH HOLDINGS SUCCESS
     * ==========================================================
     */

    @Test
    void getBranchHoldings_Success()
            throws Exception {


        List<VirtualGoldHoldingResponseDto> list=

                List.of(

                        new VirtualGoldHoldingResponseDto(
                                1,
                                1,
                                1,
                                BigDecimal.valueOf(5),
                                LocalDateTime.now()
                        )
                );


        when(
                service.getHoldingsByBranch(1)
        )

                .thenReturn(
                        list
                );


        mockMvc.perform(

                        get(
                                "/api/v1/branches/1/gold/virtual"
                        )
                )

                .andExpect(
                        status().isOk()
                )

                .andExpect(
                        jsonPath("$[0].branchId")
                                .value(1)
                );

    }



    /*
     * ==========================================================
     * GET BRANCH HOLDINGS EMPTY
     * ==========================================================
     */

    @Test
    void getBranchHoldings_Empty()
            throws Exception {


        when(
                service.getHoldingsByBranch(1)
        )

                .thenReturn(
                        Collections.emptyList()
                );


        mockMvc.perform(

                        get(
                                "/api/v1/branches/1/gold/virtual"
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