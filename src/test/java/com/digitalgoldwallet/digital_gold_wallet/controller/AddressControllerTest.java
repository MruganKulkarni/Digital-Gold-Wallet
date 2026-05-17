package com.digitalgoldwallet.digital_gold_wallet.controller;

// importing response DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

// importing service layer
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;

// JUnit imports
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

// Mockito
import static org.mockito.Mockito.when;

// request builders
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// response matchers
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * ============================================================
 * Address Controller Layer Testing
 * ============================================================
 */

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    /*
     * MockMvc for API testing
     */
    @Autowired
    private MockMvc mockMvc;

    /*
     * Mock service layer
     */
    @MockBean
    private AddressService addressService;

    /*
     * ============================================================
     * TEST GET ADDRESS BY ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Address API")
    public void testGetAddress()
            throws Exception {

        AddressResponseDto responseDto =
                new AddressResponseDto(
                        1,
                        "Anna Nagar",
                        "Chennai",
                        "Tamil Nadu",
                        "600040",
                        "India"
                );

        when(addressService.getAddressById(1))
                .thenReturn(responseDto);

        mockMvc.perform(
                        get("/api/v1/addresses/1")
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$.city")
                                .value("Chennai")
                );

        System.out.println(
                "GET ADDRESS API TEST PASSED"
        );
    }
}