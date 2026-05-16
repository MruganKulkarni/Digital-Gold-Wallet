package com.digitalgoldwallet.digital_gold_wallet.service; // package declaration for service tests

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO used to create/update vendors
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO returned by service methods
import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorException; // thrown when vendor name or email already exists
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException; // thrown when vendor is not found by ID
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // used for direct DB cleanup after each test

import org.junit.jupiter.api.DisplayName; // used to give readable names to test cases
import org.junit.jupiter.api.Test; // marks a method as a JUnit 5 test case

import org.springframework.beans.factory.annotation.Autowired; // tells Spring to inject the dependency automatically
import org.springframework.boot.test.context.SpringBootTest; // loads full Spring context with real MySQL DB
import org.springframework.data.domain.Page; // used for paginated results
import org.springframework.data.domain.PageRequest; // used to create pagination parameters

import java.math.BigDecimal; // used for gold price and quantity values

import static org.junit.jupiter.api.Assertions.*; // imports all assertion methods

/*
 * Service layer tests for VendorServiceImpl
 *
 * WHY @SpringBootTest:
 * We use the full Spring context with real MySQL so service + repository
 * work together exactly as they do in production.
 * No mocking — real DB calls verify real business logic.
 *
 * WHY NO @BeforeEach / @AfterEach:
 * Each test creates its own data using helper methods and
 * cleans up only what it created — no shared state between tests.
 * This avoids transaction conflicts and deadlocks.
 */

@SpringBootTest // loads full Spring application context — uses real MySQL from application.yaml
public class VendorServiceTest {

    @Autowired
    private VendorService vendorService; // Spring injects VendorServiceImpl — used to call service methods

    @Autowired
    private VendorRepository vendorRepository; // Spring injects VendorRepository — used for cleanup after tests

    // ================================================================
    //  HELPER METHODS
    //  Each test calls only the helpers it needs — no shared state
    // ================================================================

    private VendorRequestDto createVendorRequestDto() {
        // creates and returns a VendorRequestDto with valid test data

        VendorRequestDto dto = new VendorRequestDto(); // creates new request DTO object
        dto.setVendorName("Test Gold Traders " + System.currentTimeMillis()); // unique name using timestamp to avoid duplicates
        dto.setDescription("Test vendor description"); // sets description
        dto.setContactPersonName("Sparsh Garg"); // sets contact person
        dto.setContactEmail("sparsh" + System.currentTimeMillis() + "@test.com"); // unique email using timestamp
        dto.setContactPhone("9999999999"); // sets contact phone
        dto.setWebsiteUrl("https://testgold.com"); // sets website url
        dto.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets total gold quantity
        dto.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets current gold price
        return dto; // returns fully populated request DTO
    }

    private VendorResponseDto createAndSaveVendor() {
        // creates a vendor via service and returns the saved response DTO

        VendorRequestDto dto = createVendorRequestDto(); // creates a valid request DTO
        return vendorService.createVendor(dto); // calls service to save vendor — returns response DTO with generated ID
    }

    private void deleteVendorById(Integer vendorId) {
        // deletes vendor directly from DB using repository — used for cleanup after each test

        if (vendorRepository.existsById(vendorId)) { // checks if vendor still exists before deleting
            vendorRepository.deleteById(vendorId); // deletes vendor from DB
        }
    }

    // ================================================================
    //  CREATE VENDOR TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor - Success") // readable name shown in test report
    public void testCreateVendor_Success() {
        // verifies that a vendor can be created successfully via service

        VendorRequestDto dto = createVendorRequestDto(); // creates a valid request DTO

        VendorResponseDto response = vendorService.createVendor(dto); // calls service to create vendor

        assertNotNull(response); // confirms response is not null
        assertNotNull(response.getVendorId()); // confirms vendor ID was generated by MySQL
        assertEquals(dto.getVendorName(), response.getVendorName()); // confirms vendor name matches what was sent
        assertEquals(dto.getContactEmail(), response.getContactEmail()); // confirms email matches
        assertEquals(dto.getCurrentGoldPrice(), response.getCurrentGoldPrice()); // confirms gold price matches

        deleteVendorById(response.getVendorId()); // cleanup — deletes vendor created during this test

        System.out.println("TEST PASSED: testCreateVendor_Success - Vendor created with ID = " + response.getVendorId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor - Duplicate Name") // readable name shown in test report
    public void testCreateVendor_DuplicateName() {
        // verifies that creating a vendor with a duplicate name throws DuplicateVendorException

        VendorResponseDto saved = createAndSaveVendor(); // saves a vendor first

        VendorRequestDto duplicate = new VendorRequestDto(); // creates a new request DTO
        duplicate.setVendorName(saved.getVendorName()); // sets same name as already saved vendor — should trigger duplicate check
        duplicate.setContactEmail("different" + System.currentTimeMillis() + "@test.com"); // different email — only name is duplicate
        duplicate.setTotalGoldQuantity(new BigDecimal("500.00")); // sets valid quantity
        duplicate.setCurrentGoldPrice(new BigDecimal("6000.00")); // sets valid price

        assertThrows(DuplicateVendorException.class, () -> vendorService.createVendor(duplicate));
        // assertThrows — confirms that calling createVendor throws DuplicateVendorException
        // lambda () -> vendorService.createVendor(duplicate) is the code that should throw

        deleteVendorById(saved.getVendorId()); // cleanup — deletes vendor created during this test

        System.out.println("TEST PASSED: testCreateVendor_DuplicateName - DuplicateVendorException thrown as expected");
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor - Duplicate Email") // readable name shown in test report
    public void testCreateVendor_DuplicateEmail() {
        // verifies that creating a vendor with a duplicate email throws DuplicateVendorException

        VendorResponseDto saved = createAndSaveVendor(); // saves a vendor first

        VendorRequestDto duplicate = new VendorRequestDto(); // creates a new request DTO
        duplicate.setVendorName("Completely Different Name " + System.currentTimeMillis()); // different name — only email is duplicate
        duplicate.setContactEmail(saved.getContactEmail()); // sets same email as already saved vendor — should trigger duplicate check
        duplicate.setTotalGoldQuantity(new BigDecimal("500.00")); // sets valid quantity
        duplicate.setCurrentGoldPrice(new BigDecimal("6000.00")); // sets valid price

        assertThrows(DuplicateVendorException.class, () -> vendorService.createVendor(duplicate));
        // assertThrows — confirms DuplicateVendorException is thrown for duplicate email

        deleteVendorById(saved.getVendorId()); // cleanup — deletes vendor created during this test

        System.out.println("TEST PASSED: testCreateVendor_DuplicateEmail - DuplicateVendorException thrown as expected");
    }

    // ================================================================
    //  GET VENDOR BY ID TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Vendor By ID - Success") // readable name shown in test report
    public void testGetVendorById_Success() {
        // verifies that a saved vendor can be fetched by its ID via service

        VendorResponseDto saved = createAndSaveVendor(); // saves a vendor and gets its ID

        VendorResponseDto found = vendorService.getVendorById(saved.getVendorId()); // calls service to fetch vendor by ID

        assertNotNull(found); // confirms response is not null
        assertEquals(saved.getVendorId(), found.getVendorId()); // confirms ID matches
        assertEquals(saved.getVendorName(), found.getVendorName()); // confirms name matches

        deleteVendorById(saved.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testGetVendorById_Success - Vendor found with ID = " + found.getVendorId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Vendor By ID - Not Found") // readable name shown in test report
    public void testGetVendorById_NotFound() {
        // verifies that fetching a non-existent vendor throws VendorNotFoundException

        assertThrows(VendorNotFoundException.class, () -> vendorService.getVendorById(99999));
        // assertThrows — confirms VendorNotFoundException is thrown for ID 99999
        // ID 99999 does not exist in the database

        System.out.println("TEST PASSED: testGetVendorById_NotFound - VendorNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET ALL VENDORS TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get All Vendors - Success") // readable name shown in test report
    public void testGetAllVendors_Success() {
        // verifies that getAllVendors returns a paginated result with at least one vendor

        VendorResponseDto saved = createAndSaveVendor(); // saves a vendor to ensure list is not empty

        Page<VendorResponseDto> page = vendorService.getAllVendors(PageRequest.of(0, 10));
        // PageRequest.of(0, 10) — page 0, size 10 (first 10 results)
        // getAllVendors returns a Page object with content, totalElements, totalPages

        assertNotNull(page); // confirms page object is not null
        assertFalse(page.getContent().isEmpty()); // confirms page has at least one vendor

        deleteVendorById(saved.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testGetAllVendors_Success - Total vendors = " + page.getTotalElements());
    }

    // ================================================================
    //  UPDATE VENDOR TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Update Vendor - Success") // readable name shown in test report
    public void testUpdateVendor_Success() {
        // verifies that an existing vendor can be updated via service

        VendorResponseDto saved = createAndSaveVendor(); // saves a vendor first

        VendorRequestDto updateDto = new VendorRequestDto(); // creates update request DTO
        updateDto.setVendorName(saved.getVendorName()); // keeps same name — only updating price
        updateDto.setContactEmail(saved.getContactEmail()); // keeps same email
        updateDto.setTotalGoldQuantity(new BigDecimal("2000.00")); // updates gold quantity
        updateDto.setCurrentGoldPrice(new BigDecimal("7000.00")); // updates gold price to new value

        VendorResponseDto updated = vendorService.updateVendor(saved.getVendorId(), updateDto); // calls service to update vendor

        assertNotNull(updated); // confirms response is not null
        assertEquals(new BigDecimal("7000.00"), updated.getCurrentGoldPrice()); // confirms gold price was updated
        assertEquals(new BigDecimal("2000.00"), updated.getTotalGoldQuantity()); // confirms quantity was updated

        deleteVendorById(saved.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testUpdateVendor_Success - Gold price updated to = " + updated.getCurrentGoldPrice());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Update Vendor - Not Found") // readable name shown in test report
    public void testUpdateVendor_NotFound() {
        // verifies that updating a non-existent vendor throws VendorNotFoundException

        VendorRequestDto updateDto = createVendorRequestDto(); // creates a valid update request DTO

        assertThrows(VendorNotFoundException.class, () -> vendorService.updateVendor(99999, updateDto));
        // assertThrows — confirms VendorNotFoundException is thrown for non-existent vendor ID 99999

        System.out.println("TEST PASSED: testUpdateVendor_NotFound - VendorNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET GOLD PRICE TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Gold Price By Vendor ID - Success") // readable name shown in test report
    public void testGetGoldPriceByVendorId_Success() {
        // verifies that the current gold price for a vendor can be fetched via service

        VendorResponseDto saved = createAndSaveVendor(); // saves a vendor with gold price 6400.00

        BigDecimal price = vendorService.getGoldPriceByVendorId(saved.getVendorId()); // calls service to get gold price

        assertNotNull(price); // confirms price is not null
        assertEquals(new BigDecimal("6400.00"), price); // confirms price matches what was saved

        deleteVendorById(saved.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testGetGoldPriceByVendorId_Success - Gold price = " + price);
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Gold Price By Vendor ID - Not Found") // readable name shown in test report
    public void testGetGoldPriceByVendorId_NotFound() {
        // verifies that fetching gold price for non-existent vendor throws VendorNotFoundException

        assertThrows(VendorNotFoundException.class, () -> vendorService.getGoldPriceByVendorId(99999));
        // assertThrows — confirms VendorNotFoundException is thrown for non-existent vendor ID

        System.out.println("TEST PASSED: testGetGoldPriceByVendorId_NotFound - VendorNotFoundException thrown as expected");
    }
}