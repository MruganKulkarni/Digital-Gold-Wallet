package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.PhysicalGoldTransactionService;

import com.fasterxml.jackson.databind.ObjectMapper;

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



/*
==========================================================
Physical Gold Controller Tests
==========================================================
*/

@WebMvcTest(PhysicalGoldTransactionController.class)
class PhysicalGoldTransactionControllerTest {


    @Autowired
    MockMvc mockMvc;


    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    PhysicalGoldTransactionService service;



    @Test
    void convertToPhysical_Success()
            throws Exception {


        PhysicalGoldTransactionRequestDto request=

                new PhysicalGoldTransactionRequestDto(
                        1,
                        1,
                        1,
                        BigDecimal.valueOf(2)
                );


        PhysicalGoldTransactionResponseDto response=

                new PhysicalGoldTransactionResponseDto(
                        1,
                        1,
                        1,
                        1,
                        BigDecimal.valueOf(2),
                        LocalDateTime.now()
                );



        when(
                service.convertToPhysical(request)
        )

                .thenReturn(
                        response
                );



        mockMvc.perform(

                        post(
                                "/api/v1/gold/physical/convert"
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



    @Test
    void getUserOrders()
            throws Exception {


        List<PhysicalGoldTransactionResponseDto> list=

                List.of(

                        new PhysicalGoldTransactionResponseDto(
                                1,
                                1,
                                1,
                                1,
                                BigDecimal.TEN,
                                LocalDateTime.now()
                        )
                );



        when(
                service.getByUser(1)
        )

                .thenReturn(list);



        mockMvc.perform(
                        get("/api/v1/users/1/gold/physical")
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$[0].quantity")
                                .value(10)
                );

    }




    @Test
    void getById()
            throws Exception {


        PhysicalGoldTransactionResponseDto response =

                new PhysicalGoldTransactionResponseDto(
                        1,
                        1,
                        1,
                        1,
                        BigDecimal.ONE,
                        LocalDateTime.now()
                );


        when(service.getById(1))
                .thenReturn(response);



        mockMvc.perform(

                        get(
                                "/api/v1/physical-transactions/1"
                        )
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.transactionId")
                                .value(1)
                );

    }

    /*
     * ==========================================================
     * CONVERT VALIDATION FAILURE
     * ==========================================================
     */

    @Test
    void convert_InvalidRequest()
            throws Exception {

        PhysicalGoldTransactionRequestDto request =
                new PhysicalGoldTransactionRequestDto(
                        null,
                        null,
                        null,
                        null
                );

        mockMvc.perform(

                        post("/api/v1/gold/physical/convert")

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
     * GET USER PHYSICAL ORDERS SUCCESS
     * ==========================================================
     */

    @Test
    void getUserOrders_Success()
            throws Exception {

        List<PhysicalGoldTransactionResponseDto> list =

                List.of(

                        new PhysicalGoldTransactionResponseDto(
                                1,
                                1,
                                1,
                                1,
                                BigDecimal.valueOf(2),
                                LocalDateTime.now()
                        )
                );



        when(
                service.getByUser(1)
        )

                .thenReturn(
                        list
                );



        mockMvc.perform(

                        get(
                                "/api/v1/users/1/gold/physical"
                        )
                )

                .andExpect(
                        status().isOk()
                )

                .andExpect(
                        jsonPath("$[0].transactionId")
                                .value(1)
                );

    }



    /*
     * ==========================================================
     * GET USER PHYSICAL EMPTY
     * ==========================================================
     */

    @Test
    void getUserOrders_Empty()
            throws Exception {

        when(
                service.getByUser(1)
        )

                .thenReturn(
                        Collections.emptyList()
                );


        mockMvc.perform(

                        get(
                                "/api/v1/users/1/gold/physical"
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
     * GET DELIVERY DETAILS SUCCESS
     * ==========================================================
     */

    @Test
    void getDeliveryDetails_Success()
            throws Exception {

        PhysicalGoldTransactionResponseDto dto =

                new PhysicalGoldTransactionResponseDto(
                        1,
                        1,
                        1,
                        1,
                        BigDecimal.valueOf(2),
                        LocalDateTime.now()
                );


        when(
                service.getById(1)
        )

                .thenReturn(
                        dto
                );


        mockMvc.perform(

                        get(
                                "/api/v1/physical-transactions/1"
                        )
                )

                .andExpect(
                        status().isOk()
                )

                .andExpect(
                        jsonPath("$.transactionId")
                                .value(1)
                );

    }

}