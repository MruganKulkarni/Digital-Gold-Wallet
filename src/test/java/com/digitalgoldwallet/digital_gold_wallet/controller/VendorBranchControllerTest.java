package com.digitalgoldwallet.digital_gold_wallet.controller; // package declaration for controller tests

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto; // request DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.service.VendorBranchService; // service interface — will be mocked

import com.fasterxml.jackson.databind.ObjectMapper; // converts Java objects to JSON strings

import org.junit.jupiter.api.DisplayName; // used to give readable names to test cases
import org.junit.jupiter.api.Test; // marks method as a JUnit 5 test case

import org.springframework.beans.factory.annotation.Autowired; // injects Spring beans
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // loads only controller layer — no DB, no service
import org.springframework.boot.test.mock.mockito.MockBean; // creates a mock of the service bean
import org.springframework.http.MediaType; // used to set content type to JSON
import org.springframework.test.web.servlet.MockMvc; // used to simulate HTTP requests

import java.math.BigDecimal; // used for gold quantity values
import java.time.LocalDateTime; // used for timestamp field
import java.util.List; // used for list of branches

import static org.mockito.ArgumentMatchers.any; // matches any argument of given type
import static org.mockito.ArgumentMatchers.eq; // matches exact argument value
import static org.mockito.Mockito.when; // used to define mock behaviour

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; // builds GET request
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // builds POST request

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // verifies JSON response fields
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // verifies HTTP status code

/*
 * Controller layer tests for VendorBranchController
 *
 * WHY @WebMvcTest:
 * Loads only the controller layer — no real DB, no real service
 * Service is mocked using @MockBean — we only test HTTP layer here
 *
 * WHY MockMvc:
 * Simulates HTTP requests without starting a real server
 */
@WebMvcTest(VendorBranchController.class) // loads only VendorBranchController for testing
public class VendorBranchControllerTest {

    @Autowired
    private MockMvc mockMvc; // used to simulate HTTP API calls

    @Autowired
    private ObjectMapper objectMapper; // converts Java objects to JSON strings for request body

    @MockBean
    private VendorBranchService vendorBranchService; // mocks VendorBranchService — no real DB calls

    // ================================================================
    //  HELPER METHOD
    // ================================================================

    private VendorBranchResponseDto createMockResponseDto() {
        // creates and returns a mock VendorBranchResponseDto for use in tests

        VendorBranchResponseDto dto = new VendorBranchResponseDto(); // creates new response DTO
        dto.setBranchId(1); // sets branch id
        dto.setVendorId(1); // sets vendor id
        dto.setVendorName("Test Gold Traders"); // sets vendor name
        dto.setAddressId(1); // sets address id
        dto.setCity("Bangalore"); // sets city
        dto.setState("Karnataka"); // sets state
        dto.setQuantity(new BigDecimal("500.00")); // sets gold quantity
        dto.setCreatedAt(LocalDateTime.now()); // sets creation timestamp
        return dto; // returns fully populated mock response DTO
    }

    // ================================================================
    //  ADD BRANCH TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch API") // readable name shown in test report
    public void testAddBranch() throws Exception {
        // verifies POST /api/v1/vendors/{vendorId}/branches returns 201 CREATED

        VendorBranchRequestDto requestDto = new VendorBranchRequestDto(); // creates request DTO
        requestDto.setVendorId(1); // sets vendor id
        requestDto.setAddressId(1); // sets address id
        requestDto.setQuantity(new BigDecimal("500.00")); // sets gold quantity

        VendorBranchResponseDto responseDto = createMockResponseDto(); // creates mock response DTO

        when(vendorBranchService.addBranch(eq(1), any(VendorBranchRequestDto.class))) // when service is called with vendorId 1 and any request DTO
                .thenReturn(responseDto); // return mock response DTO

        mockMvc.perform(
                        post("/api/v1/vendors/1/branches") // performs POST request
                                .contentType(MediaType.APPLICATION_JSON) // sets content type to JSON
                                .content(objectMapper.writeValueAsString(requestDto)) // sets request body as JSON
                )
                .andExpect(status().isCreated()) // expects 201 CREATED
                .andExpect(jsonPath("$.branchId").value(1)) // verifies branchId in response
                .andExpect(jsonPath("$.vendorId").value(1)); // verifies vendorId in response

        System.out.println("TEST PASSED: testAddBranch - POST /api/v1/vendors/1/branches returned 201");
    }

    // ================================================================
    //  GET BRANCH BY ID TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branch By ID API") // readable name shown in test report
    public void testGetBranchById() throws Exception {
        // verifies GET /api/v1/branches/{branchId} returns 200 OK with correct branch

        VendorBranchResponseDto responseDto = createMockResponseDto(); // creates mock response DTO

        when(vendorBranchService.getBranchById(1)) // when service is called with branchId 1
                .thenReturn(responseDto); // return mock response DTO

        mockMvc.perform(
                        get("/api/v1/branches/1") // performs GET request to /api/v1/branches/1
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$.branchId").value(1)) // verifies branchId in response
                .andExpect(jsonPath("$.vendorName").value("Test Gold Traders")); // verifies vendorName in response

        System.out.println("TEST PASSED: testGetBranchById - GET /api/v1/branches/1 returned 200");
    }

    // ================================================================
    //  GET BRANCHES BY VENDOR ID TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branches By Vendor ID API") // readable name shown in test report
    public void testGetBranchesByVendorId() throws Exception {
        // verifies GET /api/v1/vendors/{vendorId}/branches returns 200 OK with list of branches

        VendorBranchResponseDto responseDto = createMockResponseDto(); // creates mock response DTO

        when(vendorBranchService.getBranchesByVendorId(1)) // when service is called with vendorId 1
                .thenReturn(List.of(responseDto)); // return list with one mock branch

        mockMvc.perform(
                        get("/api/v1/vendors/1/branches") // performs GET request
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$[0].branchId").value(1)) // verifies first branch id in list
                .andExpect(jsonPath("$[0].vendorId").value(1)); // verifies first vendor id in list

        System.out.println("TEST PASSED: testGetBranchesByVendorId - GET /api/v1/vendors/1/branches returned 200");
    }

    // ================================================================
    //  GET INVENTORY TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Inventory By Branch ID API") // readable name shown in test report
    public void testGetInventoryByBranchId() throws Exception {
        // verifies GET /api/v1/branches/{branchId}/inventory returns 200 OK with inventory

        when(vendorBranchService.getInventoryByBranchId(1)) // when service is called with branchId 1
                .thenReturn(new BigDecimal("500.00")); // return mock inventory quantity

        mockMvc.perform(
                        get("/api/v1/branches/1/inventory") // performs GET request
                )
                .andExpect(status().isOk()) // expects 200 OK
                .andExpect(jsonPath("$").value(500.00)); // verifies inventory quantity in response

        System.out.println("TEST PASSED: testGetInventoryByBranchId - GET /api/v1/branches/1/inventory returned 200");
    }

    // ================================================================
    //  VALIDATION TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Branch Validation API") // readable name shown in test report
    public void testValidation() throws Exception {
        // verifies POST /api/v1/vendors/{vendorId}/branches returns 400 for invalid input

        String invalidJson = """
                {
                  "vendorId": null,
                  "addressId": null,
                  "quantity": -1
                }
                """; // invalid JSON — null vendorId, null addressId, negative quantity

        mockMvc.perform(
                        post("/api/v1/vendors/1/branches") // performs POST request with invalid body
                                .contentType(MediaType.APPLICATION_JSON) // sets content type to JSON
                                .content(invalidJson) // sets invalid request body
                )
                .andExpect(status().isBadRequest()); // expects 400 BAD REQUEST — validation should fail

        System.out.println("TEST PASSED: testValidation - POST /api/v1/vendors/1/branches returned 400 for invalid input");
    }
}