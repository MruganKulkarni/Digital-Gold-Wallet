package com.digitalgoldwallet.digital_gold_wallet.controller; // package declaration for controller tests

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.service.VendorService; // service interface — will be mocked

import com.fasterxml.jackson.databind.ObjectMapper; // converts Java objects to JSON strings

import org.junit.jupiter.api.DisplayName; // used to give readable names to test cases
import org.junit.jupiter.api.Test; // marks method as a JUnit 5 test case

import org.springframework.beans.factory.annotation.Autowired; // injects Spring beans
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // loads only controller layer — no DB, no service
import org.springframework.boot.test.mock.mockito.MockBean; // creates a mock of the service bean
import org.springframework.data.domain.Page; // used for paginated response
import org.springframework.data.domain.PageImpl; // used to create a Page object from list
import org.springframework.data.domain.PageRequest; // used to create pagination parameters
import org.springframework.http.MediaType; // used to set content type to JSON
import org.springframework.test.web.servlet.MockMvc; // used to simulate HTTP requests

import java.math.BigDecimal; // used for gold price and quantity values
import java.time.LocalDateTime; // used for timestamp field
import java.util.List; // used for list of vendors

import static org.mockito.ArgumentMatchers.any; // matches any argument of given type
import static org.mockito.ArgumentMatchers.eq; // matches exact argument value
import static org.mockito.Mockito.when; // used to define mock behaviour

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // builds GET request
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // builds POST request
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // builds PUT request

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // verifies JSON response fields
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // verifies HTTP status code

/*
 * Controller layer tests for VendorController
 *
 * WHY @WebMvcTest:
 * Loads only the controller layer — no real DB, no real service
 * Service is mocked using @MockBean — we only test HTTP layer here
 *
 * WHY MockMvc:
 * Simulates HTTP requests without starting a real server
 */
@WebMvcTest(VendorController.class) // loads only VendorController for testing
public class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc; // used to simulate HTTP API calls

    @Autowired
    private ObjectMapper objectMapper; // converts Java objects to JSON strings for request body

    @MockBean
    private VendorService vendorService; // mocks VendorService — no real DB calls

    // ================================================================
    //  HELPER METHOD
    // ================================================================

    private VendorResponseDto createMockResponseDto() {
        // creates and returns a mock VendorResponseDto for use in tests

        VendorResponseDto dto = new VendorResponseDto(); // creates new response DTO
        dto.setVendorId(1); // sets vendor id
        dto.setVendorName("Test Gold Traders"); // sets vendor name
        dto.setDescription("Test description"); // sets description
        dto.setContactPersonName("Sparsh Garg"); // sets contact person
        dto.setContactEmail("sparsh@test.com"); // sets contact email
        dto.setContactPhone("9999999999"); // sets contact phone
        dto.setWebsiteUrl("https://testgold.com"); // sets website url
        dto.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets total gold quantity
        dto.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets current gold price
        dto.setCreatedAt(LocalDateTime.now()); // sets creation timestamp
        return dto; // returns fully populated mock response DTO
    }

    private VendorRequestDto createMockRequestDto() {
        // creates and returns a mock VendorRequestDto for use in tests

        VendorRequestDto dto = new VendorRequestDto(); // creates new request DTO
        dto.setVendorName("Test Gold Traders"); // sets vendor name
        dto.setDescription("Test description"); // sets description
        dto.setContactPersonName("Sparsh Garg"); // sets contact person
        dto.setContactEmail("sparsh@test.com"); // sets contact email
        dto.setContactPhone("9999999999"); // sets contact phone
        dto.setWebsiteUrl("https://testgold.com"); // sets website url
        dto.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets total gold quantity
        dto.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets current gold price
        return dto; // returns fully populated mock request DTO
    }

    // ================================================================
    //  CREATE VENDOR TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor API") // readable name shown in test report
    public void testCreateVendor() throws Exception {
        // verifies POST /api/v1/vendors returns 201 CREATED with correct response

        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO
        VendorResponseDto responseDto = createMockResponseDto(); // creates mock response DTO

        when(vendorService.createVendor(any(VendorRequestDto.class))) // when service is called with any request DTO
                .thenReturn(responseDto); // return mock response DTO

        mockMvc.perform(
                        post("/api/v1/vendors") // performs POST request to /api/v1/vendors
                                .contentType(MediaType.APPLICATION_JSON) // sets content type to JSON
                                .content(objectMapper.writeValueAsString(requestDto)) // sets request body as JSON
                )
                .andExpect(status().isCreated()) // expects 201 CREATED
                .andExpect(jsonPath("$.vendorId").value(1)) // verifies vendorId in response
                .andExpect(jsonPath("$.vendorName").value("Test Gold Traders")); // verifies vendorName in response

        System.out.println("TEST PASSED: testCreateVendor - POST /api/v1/vendors returned 201");
    }

    // ================================================================
    //  GET VENDOR BY ID TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Vendor By ID API") // readable name shown in test report
    public void testGetVendorById() throws Exception {
        // verifies GET /api/v1/vendors/{vendorId} returns 200 OK with correct vendor

        VendorResponseDto responseDto = createMockResponseDto(); // creates mock response DTO

        when(vendorService.getVendorById(1)) // when service is called with vendorId 1
                .thenReturn(responseDto); // return mock response DTO

        mockMvc.perform(
                        get("/api/v1/vendors/1") // performs GET request to /api/v1/vendors/1
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$.vendorId").value(1)) // verifies vendorId in response
                .andExpect(jsonPath("$.vendorName").value("Test Gold Traders")); // verifies vendorName in response

        System.out.println("TEST PASSED: testGetVendorById - GET /api/v1/vendors/1 returned 200");
    }

    // ================================================================
    //  GET ALL VENDORS TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get All Vendors API") // readable name shown in test report
    public void testGetAllVendors() throws Exception {
        // verifies GET /api/v1/vendors returns 200 OK with vendor list

        VendorResponseDto responseDto = createMockResponseDto(); // creates mock response DTO

        when(vendorService.getAllVendors()) // when service is called
                .thenReturn(List.of(responseDto)); // return mock list

        mockMvc.perform(
                        get("/api/v1/vendors") // performs GET request to /api/v1/vendors
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$[0].vendorName").value("Test Gold Traders")); // verifies first vendor name in array

        System.out.println("TEST PASSED: testGetAllVendors - GET /api/v1/vendors returned 200");
    }

    // ================================================================
    //  UPDATE VENDOR TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Update Vendor API") // readable name shown in test report
    public void testUpdateVendor() throws Exception {
        // verifies PUT /api/v1/vendors/{vendorId} returns 200 OK with updated vendor

        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO
        VendorResponseDto responseDto = createMockResponseDto(); // creates mock response DTO
        responseDto.setCurrentGoldPrice(new BigDecimal("7000.00")); // simulates updated gold price

        when(vendorService.updateVendor(eq(1), any(VendorRequestDto.class))) // when service is called with vendorId 1 and any request DTO
                .thenReturn(responseDto); // return mock response DTO

        mockMvc.perform(
                        put("/api/v1/vendors/1") // performs PUT request to /api/v1/vendors/1
                                .contentType(MediaType.APPLICATION_JSON) // sets content type to JSON
                                .content(objectMapper.writeValueAsString(requestDto)) // sets request body as JSON
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$.vendorId").value(1)) // verifies vendorId in response
                .andExpect(jsonPath("$.currentGoldPrice").value(7000.00)); // verifies updated gold price in response

        System.out.println("TEST PASSED: testUpdateVendor - PUT /api/v1/vendors/1 returned 200");
    }

    // ================================================================
    //  GET GOLD PRICE TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Gold Price By Vendor ID API") // readable name shown in test report
    public void testGetGoldPriceByVendorId() throws Exception {
        // verifies GET /api/v1/vendors/{vendorId}/price returns 200 OK with gold price

        when(vendorService.getGoldPriceByVendorId(1)) // when service is called with vendorId 1
                .thenReturn(new BigDecimal("6400.00")); // return mock gold price

        mockMvc.perform(
                        get("/api/v1/vendors/1/price") // performs GET request to /api/v1/vendors/1/price
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$").value(6400.00)); // verifies gold price in response

        System.out.println("TEST PASSED: testGetGoldPriceByVendorId - GET /api/v1/vendors/1/price returned 200");
    }

    // ================================================================
    //  VALIDATION TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Vendor Validation API") // readable name shown in test report
    public void testValidation() throws Exception {
        // verifies POST /api/v1/vendors returns 400 BAD REQUEST for invalid input

        String invalidJson = """
                {
                  "vendorName": "",
                  "totalGoldQuantity": -1,
                  "currentGoldPrice": 0
                }
                """; // invalid JSON — blank name, negative quantity, zero price

        mockMvc.perform(
                        post("/api/v1/vendors") // performs POST request with invalid body
                                .contentType(MediaType.APPLICATION_JSON) // sets content type to JSON
                                .content(invalidJson) // sets invalid request body
                )
                .andExpect(status().isBadRequest()); // expects 400 BAD REQUEST — validation should fail

        System.out.println("TEST PASSED: testValidation - POST /api/v1/vendors returned 400 for invalid input");
    }
}