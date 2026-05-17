package com.digitalgoldwallet.digital_gold_wallet.controller;

// importing response DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

// importing service layer
import com.digitalgoldwallet.digital_gold_wallet.service.UserService;

// converts Java object -> JSON
import com.fasterxml.jackson.databind.ObjectMapper;

// JUnit imports
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Spring imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// BigDecimal
import java.math.BigDecimal;
import java.util.List;

// Mockito
import static org.mockito.Mockito.when;

// MockMvc request builders
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

// MockMvc response matchers
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
 * ============================================================
 * User Controller Layer Testing
 * ============================================================
 */

@WebMvcTest(UserController.class)
public class UserControllerTest {

    /*
     * MockMvc used to simulate API calls
     */
    @Autowired
    private MockMvc mockMvc;

    /*
     * Converts Java objects to JSON
     */
    @Autowired
    private ObjectMapper objectMapper;

    /*
     * Mock service layer
     */
    @MockBean
    private UserService userService;

    /*
     * ============================================================
     * TEST GET USER BY ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get User API")
    public void testGetUserById()
            throws Exception {

        /*
         * Mock response DTO
         */
        UserResponseDto responseDto =
                new UserResponseDto(
                        1,
                        "Varsha",
                        "varsha@test.com",
                        new BigDecimal("5000")
                );

        /*
         * Mock service response
         */
        when(userService.getUserById(1))
                .thenReturn(responseDto);

        /*
         * Perform GET API call
         */
        mockMvc.perform(
                        get("/api/v1/users/1")
                )

                /*
                 * Verify HTTP status
                 */
                .andExpect(status().isOk())

                /*
                 * Verify JSON response
                 */
                .andExpect(
                        jsonPath("$.name")
                                .value("Varsha")
                );

        System.out.println(
                "GET USER API TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST GET ALL USERS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get All Users API")
    public void testGetAllUsers()
            throws Exception {

        List<UserResponseDto> users =
                List.of(
                        new UserResponseDto(
                                1,
                                "Varsha",
                                "varsha@test.com",
                                new BigDecimal("5000")
                        )
                );

        when(userService.getAllUsers())
                .thenReturn(users);

        mockMvc.perform(
                        get("/api/v1/users")
                )

                .andExpect(status().isOk())

                .andExpect(
                        jsonPath("$[0].name")
                                .value("Varsha")
                );

        System.out.println(
                "GET ALL USERS API TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST VALIDATION
     * ============================================================
     */
    @Test
    @DisplayName("Test User Validation API")
    public void testValidation()
            throws Exception {

        /*
         * Invalid JSON request
         */
        String invalidJson = """
                {
                  \"name\": \"\",
                  \"email\": \"wrong\",
                  \"balance\": -1,
                  \"addressId\": null
                }
                """;

        mockMvc.perform(
                        post("/api/v1/users")

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content(invalidJson)
                )

                /*
                 * Validation should fail
                 */
                .andExpect(status().isBadRequest());

        System.out.println(
                "VALIDATION TEST PASSED"
        );
    }
}