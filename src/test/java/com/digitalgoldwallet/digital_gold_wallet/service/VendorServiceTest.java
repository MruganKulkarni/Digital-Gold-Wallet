package com.digitalgoldwallet.digital_gold_wallet.service; // package declaration for service tests

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // Vendor entity
import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorException; // thrown for duplicate vendor
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException; // thrown when vendor not found
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // vendor repository — will be mocked
import com.digitalgoldwallet.digital_gold_wallet.service.impl.VendorServiceImpl; // concrete service implementation

import org.junit.jupiter.api.DisplayName; // used to give readable names to test cases
import org.junit.jupiter.api.Test; // marks method as a JUnit 5 test case
import org.junit.jupiter.api.extension.ExtendWith; // used to register extensions with JUnit 5

import org.mockito.InjectMocks; // injects mocks into the class under test
import org.mockito.Mock; // creates a mock object
import org.mockito.junit.jupiter.MockitoExtension; // enables Mockito annotations in JUnit 5

import org.springframework.data.domain.Page; // used for paginated results
import org.springframework.data.domain.PageImpl; // used to create mock Page object
import org.springframework.data.domain.PageRequest; // used to create pagination parameters

import java.math.BigDecimal; // used for gold price and quantity values
import java.time.LocalDateTime; // used for timestamp field
import java.util.List; // used for list of vendors
import java.util.Optional; // used for optional vendor result

import static org.junit.jupiter.api.Assertions.*; // imports all assertion methods
import static org.mockito.ArgumentMatchers.any; // matches any argument of given type
import static org.mockito.Mockito.when; // used to define mock behaviour

/*
 * Mockito-based unit tests for VendorServiceImpl
 *
 * WHY @ExtendWith(MockitoExtension.class):
 * Enables Mockito annotations — no Spring context, no DB
 * Pure unit tests — fast execution, no network/DB dependency
 *
 * WHY @Mock:
 * Creates a mock VendorRepository — no real DB calls
 * We control what the repository returns
 *
 * WHY @InjectMocks:
 * Creates a real VendorServiceImpl and injects all @Mock fields into it
 */
@ExtendWith(MockitoExtension.class) // enables Mockito annotations for JUnit 5
public class VendorServiceTest {

    @Mock // creates a mock VendorRepository — no real DB calls
    private VendorRepository vendorRepository;

    @InjectMocks // creates VendorServiceImpl and injects vendorRepository mock into it
    private VendorServiceImpl vendorService;

    // ================================================================
    //  HELPER METHODS
    // ================================================================

    private Vendor createMockVendor() {
        // creates and returns a mock Vendor entity for use in tests

        Vendor vendor = new Vendor(); // creates new Vendor entity
        vendor.setVendorId(1); // sets vendor id
        vendor.setVendorName("Test Gold Traders"); // sets vendor name
        vendor.setDescription("Test description"); // sets description
        vendor.setContactPersonName("Sparsh Garg"); // sets contact person
        vendor.setContactEmail("sparsh@test.com"); // sets contact email
        vendor.setContactPhone("9999999999"); // sets contact phone
        vendor.setWebsiteUrl("https://testgold.com"); // sets website url
        vendor.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets total gold quantity
        vendor.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets current gold price
        vendor.setCreatedAt(LocalDateTime.now()); // sets creation timestamp
        return vendor; // returns fully populated mock vendor
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
    //  CREATE VENDOR TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor - Success") // readable name shown in test report
    public void testCreateVendor_Success() {
        // verifies that createVendor returns correct response when vendor is created successfully

        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO
        Vendor mockVendor = createMockVendor(); // creates mock vendor entity

        when(vendorRepository.existsByVendorName("Test Gold Traders")) // when repository checks for duplicate name
                .thenReturn(false); // return false — no duplicate

        when(vendorRepository.existsByContactEmail("sparsh@test.com")) // when repository checks for duplicate email
                .thenReturn(false); // return false — no duplicate

        when(vendorRepository.save(any(Vendor.class))) // when repository saves any vendor
                .thenReturn(mockVendor); // return mock vendor with generated ID

        VendorResponseDto response = vendorService.createVendor(requestDto); // calls service method

        assertNotNull(response); // confirms response is not null
        assertEquals(1, response.getVendorId()); // confirms vendor ID is set
        assertEquals("Test Gold Traders", response.getVendorName()); // confirms vendor name matches

        System.out.println("TEST PASSED: testCreateVendor_Success - Vendor created with ID = " + response.getVendorId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor - Duplicate Name") // readable name shown in test report
    public void testCreateVendor_DuplicateName() {
        // verifies that createVendor throws DuplicateVendorException when name already exists

        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO

        when(vendorRepository.existsByVendorName("Test Gold Traders")) // when repository checks for duplicate name
                .thenReturn(true); // return true — duplicate exists

        assertThrows(DuplicateVendorException.class, // expects DuplicateVendorException to be thrown
                () -> vendorService.createVendor(requestDto)); // lambda that calls service method

        System.out.println("TEST PASSED: testCreateVendor_DuplicateName - DuplicateVendorException thrown as expected");
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Create Vendor - Duplicate Email") // readable name shown in test report
    public void testCreateVendor_DuplicateEmail() {
        // verifies that createVendor throws DuplicateVendorException when email already exists

        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO

        when(vendorRepository.existsByVendorName("Test Gold Traders")) // when repository checks for duplicate name
                .thenReturn(false); // return false — name is unique

        when(vendorRepository.existsByContactEmail("sparsh@test.com")) // when repository checks for duplicate email
                .thenReturn(true); // return true — duplicate email exists

        assertThrows(DuplicateVendorException.class, // expects DuplicateVendorException to be thrown
                () -> vendorService.createVendor(requestDto)); // lambda that calls service method

        System.out.println("TEST PASSED: testCreateVendor_DuplicateEmail - DuplicateVendorException thrown as expected");
    }

    // ================================================================
    //  GET VENDOR BY ID TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Vendor By ID - Success") // readable name shown in test report
    public void testGetVendorById_Success() {
        // verifies that getVendorById returns correct response when vendor exists

        Vendor mockVendor = createMockVendor(); // creates mock vendor entity

        when(vendorRepository.findById(1)) // when repository finds vendor by ID 1
                .thenReturn(Optional.of(mockVendor)); // return mock vendor wrapped in Optional

        VendorResponseDto response = vendorService.getVendorById(1); // calls service method

        assertNotNull(response); // confirms response is not null
        assertEquals(1, response.getVendorId()); // confirms vendor ID matches
        assertEquals("Test Gold Traders", response.getVendorName()); // confirms vendor name matches

        System.out.println("TEST PASSED: testGetVendorById_Success - Vendor found with ID = " + response.getVendorId());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Vendor By ID - Not Found") // readable name shown in test report
    public void testGetVendorById_NotFound() {
        // verifies that getVendorById throws VendorNotFoundException when vendor does not exist

        when(vendorRepository.findById(99999)) // when repository tries to find vendor by ID 99999
                .thenReturn(Optional.empty()); // return empty Optional — vendor not found

        assertThrows(VendorNotFoundException.class, // expects VendorNotFoundException to be thrown
                () -> vendorService.getVendorById(99999)); // lambda that calls service method

        System.out.println("TEST PASSED: testGetVendorById_NotFound - VendorNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET ALL VENDORS TEST
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get All Vendors - Success") // readable name shown in test report
    public void testGetAllVendors_Success() {
        // verifies that getAllVendors returns paginated list of vendors

        Vendor mockVendor = createMockVendor(); // creates mock vendor entity

        Page<Vendor> mockPage = new PageImpl<>( // creates mock Page object
                List.of(mockVendor), // list with one vendor
                PageRequest.of(0, 10), // page 0, size 10
                1 // total elements
        );

        when(vendorRepository.findAll(any(PageRequest.class))) // when repository fetches all vendors with any pageable
                .thenReturn(mockPage); // return mock page

        Page<VendorResponseDto> response = vendorService.getAllVendors(PageRequest.of(0, 10)); // calls service method

        assertNotNull(response); // confirms response is not null
        assertFalse(response.getContent().isEmpty()); // confirms page has at least one vendor
        assertEquals("Test Gold Traders", response.getContent().get(0).getVendorName()); // confirms first vendor name

        System.out.println("TEST PASSED: testGetAllVendors_Success - Total vendors = " + response.getTotalElements());
    }

    // ================================================================
    //  UPDATE VENDOR TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Update Vendor - Success") // readable name shown in test report
    public void testUpdateVendor_Success() {
        // verifies that updateVendor returns updated response when vendor exists

        Vendor mockVendor = createMockVendor(); // creates mock vendor entity
        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO
        requestDto.setCurrentGoldPrice(new BigDecimal("7000.00")); // updates gold price in request DTO

        when(vendorRepository.findById(1)) // when repository finds vendor by ID 1
                .thenReturn(Optional.of(mockVendor)); // return mock vendor

        // NOTE: existsByVendorName stub removed — name is not changing so this check is never triggered

        mockVendor.setCurrentGoldPrice(new BigDecimal("7000.00")); // simulates updated price on entity

        when(vendorRepository.save(any(Vendor.class))) // when repository saves updated vendor
                .thenReturn(mockVendor); // return updated mock vendor

        VendorResponseDto response = vendorService.updateVendor(1, requestDto); // calls service method

        assertNotNull(response); // confirms response is not null
        assertEquals(new BigDecimal("7000.00"), response.getCurrentGoldPrice()); // confirms price was updated

        System.out.println("TEST PASSED: testUpdateVendor_Success - Gold price updated to = " + response.getCurrentGoldPrice());
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Update Vendor - Not Found") // readable name shown in test report
    public void testUpdateVendor_NotFound() {
        // verifies that updateVendor throws VendorNotFoundException when vendor does not exist

        VendorRequestDto requestDto = createMockRequestDto(); // creates mock request DTO

        when(vendorRepository.findById(99999)) // when repository tries to find vendor by ID 99999
                .thenReturn(Optional.empty()); // return empty Optional — vendor not found

        assertThrows(VendorNotFoundException.class, // expects VendorNotFoundException to be thrown
                () -> vendorService.updateVendor(99999, requestDto)); // lambda that calls service method

        System.out.println("TEST PASSED: testUpdateVendor_NotFound - VendorNotFoundException thrown as expected");
    }

    // ================================================================
    //  GET GOLD PRICE TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Gold Price - Success") // readable name shown in test report
    public void testGetGoldPriceByVendorId_Success() {
        // verifies that getGoldPriceByVendorId returns correct price when vendor exists

        when(vendorRepository.existsById(1)) // when repository checks if vendor exists
                .thenReturn(true); // return true — vendor exists

        when(vendorRepository.findGoldPriceByVendorId(1)) // when repository fetches gold price
                .thenReturn(new BigDecimal("6400.00")); // return mock gold price

        BigDecimal price = vendorService.getGoldPriceByVendorId(1); // calls service method

        assertNotNull(price); // confirms price is not null
        assertEquals(new BigDecimal("6400.00"), price); // confirms price matches

        System.out.println("TEST PASSED: testGetGoldPriceByVendorId_Success - Gold price = " + price);
    }

    @Test // tells JUnit to run this method as a test case
    @DisplayName("Test Get Gold Price - Not Found") // readable name shown in test report
    public void testGetGoldPriceByVendorId_NotFound() {
        // verifies that getGoldPriceByVendorId throws VendorNotFoundException when vendor does not exist

        when(vendorRepository.existsById(99999)) // when repository checks if vendor 99999 exists
                .thenReturn(false); // return false — vendor does not exist

        assertThrows(VendorNotFoundException.class, // expects VendorNotFoundException to be thrown
                () -> vendorService.getGoldPriceByVendorId(99999)); // lambda that calls service method

        System.out.println("TEST PASSED: testGetGoldPriceByVendorId_NotFound - VendorNotFoundException thrown as expected");
    }
}