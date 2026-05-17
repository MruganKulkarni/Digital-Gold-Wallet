package com.digitalgoldwallet.digital_gold_wallet.service; // package declaration for service tests

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto; // request DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // Address entity — needed to link branch to address
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // Vendor entity — needed to link branch to vendor
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // VendorBranch entity
import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorBranchException; // thrown for duplicate branch
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException; // thrown when branch not found
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException; // thrown when vendor not found
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository; // address repository — will be mocked
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository; // vendor branch repository — will be mocked
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // vendor repository — will be mocked
import com.digitalgoldwallet.digital_gold_wallet.service.impl.VendorBranchServiceImpl; // concrete service implementation

import org.junit.jupiter.api.DisplayName; // used to give readable names to test cases
import org.junit.jupiter.api.Test; // marks method as a JUnit 5 test case
import org.junit.jupiter.api.extension.ExtendWith; // used to register extensions with JUnit 5

import org.mockito.InjectMocks; // injects mocks into the class under test
import org.mockito.Mock; // creates a mock object
import org.mockito.junit.jupiter.MockitoExtension; // enables Mockito annotations in JUnit 5

import java.math.BigDecimal; // used for gold quantity values
import java.time.LocalDateTime; // used for timestamp field
import java.util.List; // used for list of branches
import java.util.Optional; // used for optional result

import static org.junit.jupiter.api.Assertions.*; // imports all assertion methods
import static org.mockito.ArgumentMatchers.any; // matches any argument of given type
import static org.mockito.Mockito.when; // used to define mock behaviour

/*
 * Mockito-based unit tests for VendorBranchServiceImpl
 *
 * WHY @ExtendWith(MockitoExtension.class):
 * Enables Mockito annotations — no Spring context, no DB
 * Pure unit tests — fast execution, no network/DB dependency
 *
 * WHY @Mock:
 * Creates mock repositories — no real DB calls
 * We control what each repository returns
 *
 * WHY @InjectMocks:
 * Creates a real VendorBranchServiceImpl and injects all @Mock fields into it
 */
@ExtendWith(MockitoExtension.class) // enables Mockito annotations for JUnit 5
public class VendorBranchServiceMockTest {

    @Mock // creates a mock VendorBranchRepository — no real DB calls
    private VendorBranchRepository vendorBranchRepository;

    @Mock // creates a mock VendorRepository — no real DB calls
    private VendorRepository vendorRepository;

    @Mock // creates a mock AddressRepository — no real DB calls
    private AddressRepository addressRepository;

    @InjectMocks // creates VendorBranchServiceImpl and injects all mock repositories into it
    private VendorBranchServiceImpl vendorBranchService;

    // ================================================================
    //  HELPER METHODS
    // ================================================================

    private Vendor createMockVendor() {
        // creates and returns a mock Vendor entity

        Vendor vendor = new Vendor(); // creates new Vendor entity
        vendor.setVendorId(1); // sets vendor id
        vendor.setVendorName("Test Gold Traders"); // sets vendor name
        vendor.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets total gold quantity
        vendor.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets current gold price
        return vendor; // returns mock vendor
    }

    private Address createMockAddress() {
        // creates and returns a mock Address entity

        Address address = new Address(); // creates new Address entity
        address.setAddressId(1); // sets address id
        address.setStreet("MG Road"); // sets street
        address.setCity("Bangalore"); // sets city
        address.setState("Karnataka"); // sets state
        address.setPostalCode("560001"); // sets postal code
        address.setCountry("India"); // sets country
        return address; // returns mock address
    }

    private VendorBranch createMockBranch(Vendor vendor, Address address) {
        // creates and returns a mock VendorBranch entity

        VendorBranch branch = new VendorBranch(); // creates new VendorBranch entity
        branch.setBranchId(1); // sets branch id
        branch.setVendor(vendor); // links to mock vendor
        branch.setAddress(address); // links to mock address
        branch.setQuantity(new BigDecimal("500.00")); // sets gold quantity
        branch.setCreatedAt(LocalDateTime.now()); // sets creation timestamp
        return branch; // returns mock branch
    }

    // ================================================================
    //  ADD BRANCH TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch - Success") // readable name shown in test report
    public void testAddBranch_Success() {
        // verifies that addBranch returns correct response when branch is created successfully

        Vendor mockVendor = createMockVendor(); // creates mock vendor
        Address mockAddress = createMockAddress(); // creates mock address
        VendorBranch mockBranch = createMockBranch(mockVendor, mockAddress); // creates mock branch

        VendorBranchRequestDto requestDto = new VendorBranchRequestDto(); // creates request DTO
        requestDto.setVendorId(1); // sets vendor id
        requestDto.setAddressId(1); // sets address id
        requestDto.setQuantity(new BigDecimal("500.00")); // sets quantity

        when(vendorRepository.findById(1)) // when repository finds vendor by ID 1
                .thenReturn(Optional.of(mockVendor)); // return mock vendor

        when(addressRepository.findById(1)) // when repository finds address by ID 1
                .thenReturn(Optional.of(mockAddress)); // return mock address

        when(vendorBranchRepository.existsByVendorVendorIdAndAddressAddressId(1, 1)) // when checking for duplicate branch
                .thenReturn(false); // return false — no duplicate

        when(vendorBranchRepository.save(any(VendorBranch.class))) // when repository saves any branch
                .thenReturn(mockBranch); // return mock branch with generated ID

        VendorBranchResponseDto response = vendorBranchService.addBranch(1, requestDto); // calls service method

        assertNotNull(response); // confirms response is not null
        assertEquals(1, response.getBranchId()); // confirms branch ID is set
        assertEquals(1, response.getVendorId()); // confirms vendor ID matches
        assertEquals(new BigDecimal("500.00"), response.getQuantity()); // confirms quantity matches

        System.out.println("TEST PASSED: testAddBranch_Success - Branch added with ID = " + response.getBranchId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch - Vendor Not Found") // readable name shown in test report
    public void testAddBranch_VendorNotFound() {
        // verifies that addBranch throws VendorNotFoundException when vendor does not exist

        VendorBranchRequestDto requestDto = new VendorBranchRequestDto(); // creates request DTO
        requestDto.setVendorId(99999); // sets non-existent vendor id
        requestDto.setAddressId(1); // sets valid address id
        requestDto.setQuantity(new BigDecimal("500.00")); // sets valid quantity

        when(vendorRepository.findById(99999)) // when repository tries to find vendor by ID 99999
                .thenReturn(Optional.empty()); // return empty Optional — vendor not found

        assertThrows(VendorNotFoundException.class, // expects VendorNotFoundException to be thrown
                () -> vendorBranchService.addBranch(99999, requestDto)); // lambda that calls service method

        System.out.println("TEST PASSED: testAddBranch_VendorNotFound - VendorNotFoundException thrown as expected");
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Add Branch - Duplicate Branch") // readable name shown in test report
    public void testAddBranch_DuplicateBranch() {
        // verifies that addBranch throws DuplicateVendorBranchException when branch already exists

        Vendor mockVendor = createMockVendor(); // creates mock vendor
        Address mockAddress = createMockAddress(); // creates mock address

        VendorBranchRequestDto requestDto = new VendorBranchRequestDto(); // creates request DTO
        requestDto.setVendorId(1); // sets vendor id
        requestDto.setAddressId(1); // sets address id
        requestDto.setQuantity(new BigDecimal("500.00")); // sets quantity

        when(vendorRepository.findById(1)) // when repository finds vendor by ID 1
                .thenReturn(Optional.of(mockVendor)); // return mock vendor

        when(addressRepository.findById(1)) // when repository finds address by ID 1
                .thenReturn(Optional.of(mockAddress)); // return mock address

        when(vendorBranchRepository.existsByVendorVendorIdAndAddressAddressId(1, 1)) // when checking for duplicate branch
                .thenReturn(true); // return true — duplicate exists

        assertThrows(DuplicateVendorBranchException.class, // expects DuplicateVendorBranchException to be thrown
                () -> vendorBranchService.addBranch(1, requestDto)); // lambda that calls service method

        System.out.println("TEST PASSED: testAddBranch_DuplicateBranch - DuplicateVendorBranchException thrown as expected");
    }

    // ================================================================
    //  GET BRANCH BY ID TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branch By ID - Success") // readable name shown in test report
    public void testGetBranchById_Success() {
        // verifies that getBranchById returns correct response when branch exists

        Vendor mockVendor = createMockVendor(); // creates mock vendor
        Address mockAddress = createMockAddress(); // creates mock address
        VendorBranch mockBranch = createMockBranch(mockVendor, mockAddress); // creates mock branch

        when(vendorBranchRepository.findById(1)) // when repository finds branch by ID 1
                .thenReturn(Optional.of(mockBranch)); // return mock branch wrapped in Optional

        VendorBranchResponseDto response = vendorBranchService.getBranchById(1); // calls service method

        assertNotNull(response); // confirms response is not null
        assertEquals(1, response.getBranchId()); // confirms branch ID matches
        assertEquals(1, response.getVendorId()); // confirms vendor ID matches

        System.out.println("TEST PASSED: testGetBranchById_Success - Branch found with ID = " + response.getBranchId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branch By ID - Not Found") // readable name shown in test report
    public void testGetBranchById_NotFound() {
        // verifies that getBranchById throws VendorBranchNotFoundException when branch does not exist

        when(vendorBranchRepository.findById(99999)) // when repository tries to find branch by ID 99999
                .thenReturn(Optional.empty()); // return empty Optional — branch not found

        assertThrows(VendorBranchNotFoundException.class, // expects VendorBranchNotFoundException to be thrown
                () -> vendorBranchService.getBranchById(99999)); // lambda that calls service method

        System.out.println("TEST PASSED: testGetBranchById_NotFound - VendorBranchNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET BRANCHES BY VENDOR ID TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branches By Vendor ID - Success") // readable name shown in test report
    public void testGetBranchesByVendorId_Success() {
        // verifies that getBranchesByVendorId returns list of branches when vendor exists

        Vendor mockVendor = createMockVendor(); // creates mock vendor
        Address mockAddress = createMockAddress(); // creates mock address
        VendorBranch mockBranch = createMockBranch(mockVendor, mockAddress); // creates mock branch

        when(vendorRepository.existsById(1)) // when repository checks if vendor exists
                .thenReturn(true); // return true — vendor exists

        when(vendorBranchRepository.findByVendorVendorId(1)) // when repository fetches branches by vendor ID
                .thenReturn(List.of(mockBranch)); // return list with one mock branch

        List<VendorBranchResponseDto> response = vendorBranchService.getBranchesByVendorId(1); // calls service method

        assertNotNull(response); // confirms response is not null
        assertFalse(response.isEmpty()); // confirms list has at least one branch
        assertEquals(1, response.get(0).getBranchId()); // confirms first branch ID matches

        System.out.println("TEST PASSED: testGetBranchesByVendorId_Success - Branches found = " + response.size());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Branches By Vendor ID - Not Found") // readable name shown in test report
    public void testGetBranchesByVendorId_NotFound() {
        // verifies that getBranchesByVendorId throws VendorNotFoundException when vendor does not exist

        when(vendorRepository.existsById(99999)) // when repository checks if vendor 99999 exists
                .thenReturn(false); // return false — vendor does not exist

        assertThrows(VendorNotFoundException.class, // expects VendorNotFoundException to be thrown
                () -> vendorBranchService.getBranchesByVendorId(99999)); // lambda that calls service method

        System.out.println("TEST PASSED: testGetBranchesByVendorId_NotFound - VendorNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET INVENTORY TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Inventory By Branch ID - Success") // readable name shown in test report
    public void testGetInventoryByBranchId_Success() {
        // verifies that getInventoryByBranchId returns correct inventory when branch exists

        when(vendorBranchRepository.existsById(1)) // when repository checks if branch exists
                .thenReturn(true); // return true — branch exists

        when(vendorBranchRepository.findInventoryByBranchId(1)) // when repository fetches inventory
                .thenReturn(new BigDecimal("500.00")); // return mock inventory quantity

        BigDecimal inventory = vendorBranchService.getInventoryByBranchId(1); // calls service method

        assertNotNull(inventory); // confirms inventory is not null
        assertEquals(new BigDecimal("500.00"), inventory); // confirms inventory matches

        System.out.println("TEST PASSED: testGetInventoryByBranchId_Success - Inventory = " + inventory);
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Inventory By Branch ID - Not Found") // readable name shown in test report
    public void testGetInventoryByBranchId_NotFound() {
        // verifies that getInventoryByBranchId throws VendorBranchNotFoundException when branch does not exist

        when(vendorBranchRepository.existsById(99999)) // when repository checks if branch 99999 exists
                .thenReturn(false); // return false — branch does not exist

        assertThrows(VendorBranchNotFoundException.class, // expects VendorBranchNotFoundException to be thrown
                () -> vendorBranchService.getInventoryByBranchId(99999)); // lambda that calls service method

        System.out.println("TEST PASSED: testGetInventoryByBranchId_NotFound - VendorBranchNotFoundException thrown as expected");
    }
}