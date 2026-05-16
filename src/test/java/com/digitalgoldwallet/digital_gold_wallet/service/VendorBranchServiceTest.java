package com.digitalgoldwallet.digital_gold_wallet.service; // package declaration for service tests

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto; // request DTO used to create branches
import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO used to create vendors
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO returned by branch service methods
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO returned by vendor service methods
import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // Address entity — needed to create branch location
import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorBranchException; // thrown when branch already exists at same vendor + address
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException; // thrown when branch is not found by ID
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException; // thrown when vendor is not found by ID
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository; // used to save test address directly
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository; // used for direct DB cleanup after each test
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // used for direct DB cleanup after each test

import org.junit.jupiter.api.DisplayName; // used to give readable names to test cases
import org.junit.jupiter.api.Test; // marks a method as a JUnit 5 test case

import org.springframework.beans.factory.annotation.Autowired; // tells Spring to inject the dependency automatically
import org.springframework.boot.test.context.SpringBootTest; // loads full Spring context with real MySQL DB

import java.math.BigDecimal; // used for gold quantity values
import java.util.List; // used for list of branch responses

import static org.junit.jupiter.api.Assertions.*; // imports all assertion methods

/*
 * Service layer tests for VendorBranchServiceImpl
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
public class VendorBranchServiceTest {

    @Autowired
    private VendorBranchService vendorBranchService; // Spring injects VendorBranchServiceImpl — used to call branch service methods

    @Autowired
    private VendorService vendorService; // Spring injects VendorServiceImpl — used to create test vendors

    @Autowired
    private VendorRepository vendorRepository; // used for direct DB cleanup after each test

    @Autowired
    private VendorBranchRepository vendorBranchRepository; // used for direct DB cleanup after each test

    @Autowired
    private AddressRepository addressRepository; // used to save test addresses directly — from Varsha's module

    // ================================================================
    //  HELPER METHODS
    //  Each test calls only the helpers it needs — no shared state
    // ================================================================

    private Address createAndSaveAddress() {
        // creates a test Address directly via repository and returns saved object with generated ID

        Address address = new Address(); // creates new Address object
        address.setStreet("MG Road"); // sets street
        address.setCity("Bangalore"); // sets city
        address.setState("Karnataka"); // sets state
        address.setPostalCode("560001"); // sets postal code
        address.setCountry("India"); // sets country
        return addressRepository.save(address); // saves to DB — returned object has auto-generated addressId
    }

    private VendorResponseDto createAndSaveVendor() {
        // creates a vendor via service and returns the saved response DTO

        VendorRequestDto dto = new VendorRequestDto(); // creates new vendor request DTO
        dto.setVendorName("Branch Test Vendor " + System.currentTimeMillis()); // unique name using timestamp
        dto.setContactEmail("branch" + System.currentTimeMillis() + "@test.com"); // unique email using timestamp
        dto.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets valid quantity
        dto.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets valid price
        return vendorService.createVendor(dto); // saves vendor via service and returns response DTO
    }

    private VendorBranchResponseDto createAndSaveBranch(Integer vendorId, Integer addressId) {
        // creates a branch via service and returns the saved response DTO

        VendorBranchRequestDto dto = new VendorBranchRequestDto(); // creates new branch request DTO
        dto.setVendorId(vendorId); // sets vendor ID
        dto.setAddressId(addressId); // sets address ID
        dto.setQuantity(new BigDecimal("500.00")); // sets gold quantity at this branch
        return vendorBranchService.addBranch(vendorId, dto); // saves branch via service and returns response DTO
    }

    private void cleanup(Integer branchId, Integer vendorId) {
        // deletes branch and vendor from DB — used for cleanup after each test

        if (branchId != null && vendorBranchRepository.existsById(branchId)) { // checks if branch exists
            vendorBranchRepository.deleteById(branchId); // deletes branch first — FK constraint requires this order
        }

        if (vendorId != null && vendorRepository.existsById(vendorId)) { // checks if vendor exists
            vendorRepository.deleteById(vendorId); // deletes vendor after branch is removed
        }
    }

    // ================================================================
    //  ADD BRANCH TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch - Success") // readable name shown in test report
    public void testAddBranch_Success() {
        // verifies that a branch can be added to a vendor successfully via service

        VendorResponseDto vendor = createAndSaveVendor(); // saves a test vendor
        Address address = createAndSaveAddress(); // saves a test address

        VendorBranchRequestDto dto = new VendorBranchRequestDto(); // creates branch request DTO
        dto.setVendorId(vendor.getVendorId()); // sets vendor ID
        dto.setAddressId(address.getAddressId()); // sets address ID
        dto.setQuantity(new BigDecimal("500.00")); // sets gold quantity

        VendorBranchResponseDto response = vendorBranchService.addBranch(vendor.getVendorId(), dto); // calls service to add branch

        assertNotNull(response); // confirms response is not null
        assertNotNull(response.getBranchId()); // confirms branch ID was generated by MySQL
        assertEquals(vendor.getVendorId(), response.getVendorId()); // confirms branch is linked to correct vendor
        assertEquals(address.getAddressId(), response.getAddressId()); // confirms branch is linked to correct address
        assertEquals(new BigDecimal("500.00"), response.getQuantity()); // confirms quantity matches

        cleanup(response.getBranchId(), vendor.getVendorId()); // cleanup — deletes branch and vendor

        System.out.println("TEST PASSED: testAddBranch_Success - Branch added with ID = " + response.getBranchId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch - Vendor Not Found") // readable name shown in test report
    public void testAddBranch_VendorNotFound() {
        // verifies that adding a branch to a non-existent vendor throws VendorNotFoundException

        Address address = createAndSaveAddress(); // saves a test address

        VendorBranchRequestDto dto = new VendorBranchRequestDto(); // creates branch request DTO
        dto.setVendorId(99999); // non-existent vendor ID — should trigger VendorNotFoundException
        dto.setAddressId(address.getAddressId()); // valid address ID
        dto.setQuantity(new BigDecimal("500.00")); // valid quantity

        assertThrows(VendorNotFoundException.class, () -> vendorBranchService.addBranch(99999, dto));
        // assertThrows — confirms VendorNotFoundException is thrown for non-existent vendor ID

        System.out.println("TEST PASSED: testAddBranch_VendorNotFound - VendorNotFoundException thrown as expected");
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch - Duplicate Branch") // readable name shown in test report
    public void testAddBranch_DuplicateBranch() {
        // verifies that adding a duplicate branch at the same vendor + address throws DuplicateVendorBranchException

        VendorResponseDto vendor = createAndSaveVendor(); // saves a test vendor
        Address address = createAndSaveAddress(); // saves a test address

        VendorBranchResponseDto branch = createAndSaveBranch(vendor.getVendorId(), address.getAddressId()); // saves first branch

        VendorBranchRequestDto duplicate = new VendorBranchRequestDto(); // creates duplicate branch request DTO
        duplicate.setVendorId(vendor.getVendorId()); // same vendor ID
        duplicate.setAddressId(address.getAddressId()); // same address ID — should trigger duplicate check
        duplicate.setQuantity(new BigDecimal("200.00")); // different quantity — but location is duplicate

        assertThrows(DuplicateVendorBranchException.class,
                () -> vendorBranchService.addBranch(vendor.getVendorId(), duplicate));
        // assertThrows — confirms DuplicateVendorBranchException is thrown for same vendor + address combo

        cleanup(branch.getBranchId(), vendor.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testAddBranch_DuplicateBranch - DuplicateVendorBranchException thrown as expected");
    }

    // ================================================================
    //  GET BRANCH BY ID TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branch By ID - Success") // readable name shown in test report
    public void testGetBranchById_Success() {
        // verifies that a saved branch can be fetched by its ID via service

        VendorResponseDto vendor = createAndSaveVendor(); // saves a test vendor
        Address address = createAndSaveAddress(); // saves a test address
        VendorBranchResponseDto branch = createAndSaveBranch(vendor.getVendorId(), address.getAddressId()); // saves a branch

        VendorBranchResponseDto found = vendorBranchService.getBranchById(branch.getBranchId()); // calls service to fetch branch by ID

        assertNotNull(found); // confirms response is not null
        assertEquals(branch.getBranchId(), found.getBranchId()); // confirms branch ID matches
        assertEquals(vendor.getVendorId(), found.getVendorId()); // confirms vendor ID matches

        cleanup(branch.getBranchId(), vendor.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testGetBranchById_Success - Branch found with ID = " + found.getBranchId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branch By ID - Not Found") // readable name shown in test report
    public void testGetBranchById_NotFound() {
        // verifies that fetching a non-existent branch throws VendorBranchNotFoundException

        assertThrows(VendorBranchNotFoundException.class, () -> vendorBranchService.getBranchById(99999));
        // assertThrows — confirms VendorBranchNotFoundException is thrown for non-existent branch ID 99999

        System.out.println("TEST PASSED: testGetBranchById_NotFound - VendorBranchNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET BRANCHES BY VENDOR ID TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branches By Vendor ID - Success") // readable name shown in test report
    public void testGetBranchesByVendorId_Success() {
        // verifies that all branches of a vendor can be fetched via service

        VendorResponseDto vendor = createAndSaveVendor(); // saves a test vendor
        Address address = createAndSaveAddress(); // saves a test address
        VendorBranchResponseDto branch = createAndSaveBranch(vendor.getVendorId(), address.getAddressId()); // saves one branch

        List<VendorBranchResponseDto> branches = vendorBranchService.getBranchesByVendorId(vendor.getVendorId()); // calls service to get all branches of vendor

        assertNotNull(branches); // confirms list is not null
        assertFalse(branches.isEmpty()); // confirms list has at least one branch

        cleanup(branch.getBranchId(), vendor.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testGetBranchesByVendorId_Success - Branches found = " + branches.size());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branches By Vendor ID - Vendor Not Found") // readable name shown in test report
    public void testGetBranchesByVendorId_VendorNotFound() {
        // verifies that fetching branches for a non-existent vendor throws VendorNotFoundException

        assertThrows(VendorNotFoundException.class, () -> vendorBranchService.getBranchesByVendorId(99999));
        // assertThrows — confirms VendorNotFoundException is thrown for non-existent vendor ID

        System.out.println("TEST PASSED: testGetBranchesByVendorId_VendorNotFound - VendorNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET INVENTORY TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Inventory By Branch ID - Success") // readable name shown in test report
    public void testGetInventoryByBranchId_Success() {
        // verifies that the gold inventory at a branch can be fetched via service

        VendorResponseDto vendor = createAndSaveVendor(); // saves a test vendor
        Address address = createAndSaveAddress(); // saves a test address
        VendorBranchResponseDto branch = createAndSaveBranch(vendor.getVendorId(), address.getAddressId()); // saves branch with quantity 500.00

        BigDecimal inventory = vendorBranchService.getInventoryByBranchId(branch.getBranchId()); // calls service to get inventory

        assertNotNull(inventory); // confirms inventory is not null
        assertEquals(new BigDecimal("500.00"), inventory); // confirms quantity matches what was saved

        cleanup(branch.getBranchId(), vendor.getVendorId()); // cleanup

        System.out.println("TEST PASSED: testGetInventoryByBranchId_Success - Inventory = " + inventory);
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Inventory By Branch ID - Not Found") // readable name shown in test report
    public void testGetInventoryByBranchId_NotFound() {
        // verifies that fetching inventory for a non-existent branch throws VendorBranchNotFoundException

        assertThrows(VendorBranchNotFoundException.class, () -> vendorBranchService.getInventoryByBranchId(99999));
        // assertThrows — confirms VendorBranchNotFoundException is thrown for non-existent branch ID

        System.out.println("TEST PASSED: testGetInventoryByBranchId_NotFound - VendorBranchNotFoundException thrown as expected");
    }
}