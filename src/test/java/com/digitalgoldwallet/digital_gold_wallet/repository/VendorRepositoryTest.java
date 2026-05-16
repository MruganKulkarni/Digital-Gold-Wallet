package com.digitalgoldwallet.digital_gold_wallet.repository; // package declaration

import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // importing Address entity
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // importing Vendor entity
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // importing VendorBranch entity

import org.junit.jupiter.api.Test; // marks method as a test case
import org.junit.jupiter.api.BeforeEach; // runs before each test method
import org.junit.jupiter.api.AfterEach; // runs after each test method
import org.springframework.beans.factory.annotation.Autowired; // injects Spring beans
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; // loads only JPA layer for testing
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase; // configures test database
import org.springframework.test.context.ActiveProfiles; // sets active profile for test

import java.math.BigDecimal; // used for gold price and quantity
import java.util.List; // used for list of results
import java.util.Optional; // used for optional result

import static org.junit.jupiter.api.Assertions.*; // imports all assertion methods

@DataJpaTest // loads only JPA repositories, entities — no full Spring context needed
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // uses real MySQL DB instead of in-memory H2
@ActiveProfiles("test") // uses test profile configuration
public class VendorRepositoryTest {

    @Autowired // injects VendorRepository automatically
    private VendorRepository vendorRepository;

    @Autowired // injects VendorBranchRepository automatically
    private VendorBranchRepository vendorBranchRepository;

    @Autowired // injects AddressRepository automatically
    private AddressRepository addressRepository;

    private Vendor testVendor; // holds test vendor object used across tests
    private Address testAddress; // holds test address object used across tests

    @BeforeEach // runs before every single test method
    public void setUp() {

        // ---- create test address ----
        testAddress = new Address(); // creating new address object
        testAddress.setStreet("MG Road"); // setting street
        testAddress.setCity("Bangalore"); // setting city
        testAddress.setState("Karnataka"); // setting state
        testAddress.setPostalCode("560001"); // setting postal code
        testAddress.setCountry("India"); // setting country
        addressRepository.save(testAddress); // saving address to DB

        // ---- create test vendor ----
        testVendor = new Vendor(); // creating new vendor object
        testVendor.setVendorName("Test Gold Traders"); // setting vendor name
        testVendor.setDescription("Test vendor for JUnit"); // setting description
        testVendor.setContactPersonName("Sparsh Garg"); // setting contact person
        testVendor.setContactEmail("sparsh@test.com"); // setting contact email
        testVendor.setContactPhone("9999999999"); // setting phone
        testVendor.setTotalGoldQuantity(new BigDecimal("1000.00")); // setting total gold quantity
        testVendor.setCurrentGoldPrice(new BigDecimal("6400.00")); // setting gold price
        vendorRepository.save(testVendor); // saving vendor to DB
    }

    @Autowired // inject EntityManager to run native SQL
    private jakarta.persistence.EntityManager entityManager; // used to disable FK checks

    @AfterEach // runs after every single test method to clean up DB
    @org.springframework.transaction.annotation.Transactional // runs in transaction
    public void tearDown() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate(); // disable FK checks temporarily
        vendorBranchRepository.deleteAll(); // deletes all branches
        vendorRepository.deleteAll(); // deletes all vendors
        addressRepository.deleteAll(); // deletes all addresses
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate(); // re-enable FK checks
    }

    // ==================== VENDOR TESTS ====================

    @Test // marks this as a JUnit test case
    public void testSaveVendor_Success() {
        // tests that vendor is saved successfully and gets an ID
        assertNotNull(testVendor.getVendorId()); // vendor ID should not be null after save
        System.out.println("TEST PASSED: testSaveVendor_Success - Vendor saved with ID = " + testVendor.getVendorId());
    }

    @Test // marks this as a JUnit test case
    public void testFindVendorById_Success() {
        // tests that vendor can be fetched by ID
        Optional<Vendor> found = vendorRepository.findById(testVendor.getVendorId()); // fetch vendor by ID
        assertTrue(found.isPresent()); // vendor should exist
        assertEquals("Test Gold Traders", found.get().getVendorName()); // name should match
        System.out.println("TEST PASSED: testFindVendorById_Success - Vendor found = " + found.get().getVendorName());
    }

    @Test // marks this as a JUnit test case
    public void testFindVendorById_NotFound() {
        // tests that fetching non-existing vendor returns empty
        Optional<Vendor> found = vendorRepository.findById(99999); // ID that doesn't exist
        assertFalse(found.isPresent()); // should return empty optional
        System.out.println("TEST PASSED: testFindVendorById_NotFound - Vendor not found as expected");
    }

    @Test // marks this as a JUnit test case
    public void testUpdateVendor_Success() {
        // tests that vendor gold price can be updated
        testVendor.setCurrentGoldPrice(new BigDecimal("6500.00")); // updating gold price
        vendorRepository.save(testVendor); // saving updated vendor

        Optional<Vendor> updated = vendorRepository.findById(testVendor.getVendorId()); // fetch updated vendor
        assertEquals(new BigDecimal("6500.00"), updated.get().getCurrentGoldPrice()); // price should be updated
        System.out.println("TEST PASSED: testUpdateVendor_Success - Gold price updated to = " + updated.get().getCurrentGoldPrice());
    }

    @Test // marks this as a JUnit test case
    public void testDeleteVendor_Success() {
        // tests that vendor can be deleted
        Integer vendorId = testVendor.getVendorId(); // store vendor ID before delete
        vendorRepository.deleteById(vendorId); // delete vendor

        Optional<Vendor> deleted = vendorRepository.findById(vendorId); // try to fetch deleted vendor
        assertFalse(deleted.isPresent()); // should not exist anymore
        System.out.println("TEST PASSED: testDeleteVendor_Success - Vendor deleted successfully");
    }

    @Test // marks this as a JUnit test case
    public void testExistsByVendorName_Success() {
        // tests that vendor name existence check works
        boolean exists = vendorRepository.existsByVendorName("Test Gold Traders"); // check existing name
        assertTrue(exists); // should return true
        System.out.println("TEST PASSED: testExistsByVendorName_Success - Vendor name exists = " + exists);
    }

    @Test // marks this as a JUnit test case
    public void testExistsByVendorName_NotFound() {
        // tests that non-existing vendor name returns false
        boolean exists = vendorRepository.existsByVendorName("Non Existing Vendor"); // check non-existing name
        assertFalse(exists); // should return false
        System.out.println("TEST PASSED: testExistsByVendorName_NotFound - Vendor name not found as expected");
    }

    @Test // marks this as a JUnit test case
    public void testExistsByContactEmail_Success() {
        // tests that contact email existence check works
        boolean exists = vendorRepository.existsByContactEmail("sparsh@test.com"); // check existing email
        assertTrue(exists); // should return true
        System.out.println("TEST PASSED: testExistsByContactEmail_Success - Email exists = " + exists);
    }

    @Test // marks this as a JUnit test case
    public void testFindByVendorName_Success() {
        // tests custom query to find vendor by name
        Optional<Vendor> found = vendorRepository.findByVendorName("Test Gold Traders"); // find by name
        assertTrue(found.isPresent()); // should be found
        assertEquals("sparsh@test.com", found.get().getContactEmail()); // email should match
        System.out.println("TEST PASSED: testFindByVendorName_Success - Vendor found by name");
    }

    @Test // marks this as a JUnit test case
    public void testFindGoldPriceByVendorId_Success() {
        // tests native SQL query to get gold price by vendor ID
        BigDecimal price = vendorRepository.findGoldPriceByVendorId(testVendor.getVendorId()); // get price
        assertNotNull(price); // price should not be null
        assertEquals(new BigDecimal("6400.00"), price); // price should match
        System.out.println("TEST PASSED: testFindGoldPriceByVendorId_Success - Gold price = " + price);
    }

    @Test // marks this as a JUnit test case
    public void testFindAllVendors_Success() {
        // tests that findAll returns at least one vendor
        List<Vendor> vendors = vendorRepository.findAll(); // get all vendors
        assertFalse(vendors.isEmpty()); // list should not be empty
        System.out.println("TEST PASSED: testFindAllVendors_Success - Total vendors = " + vendors.size());
    }

    // ==================== VENDOR BRANCH TESTS ====================

    @Test // marks this as a JUnit test case
    public void testSaveVendorBranch_Success() {
        // tests that vendor branch is saved successfully
        VendorBranch branch = new VendorBranch(); // creating new branch object
        branch.setVendor(testVendor); // linking to test vendor
        branch.setAddress(testAddress); // linking to test address
        branch.setQuantity(new BigDecimal("500.00")); // setting gold quantity
        vendorBranchRepository.save(branch); // saving branch to DB

        assertNotNull(branch.getBranchId()); // branch ID should not be null after save
        System.out.println("TEST PASSED: testSaveVendorBranch_Success - Branch saved with ID = " + branch.getBranchId());
    }

    @Test // marks this as a JUnit test case
    public void testFindBranchesByVendorId_Success() {
        // tests that branches can be fetched by vendor ID
        VendorBranch branch = new VendorBranch(); // creating branch
        branch.setVendor(testVendor); // linking to vendor
        branch.setAddress(testAddress); // linking to address
        branch.setQuantity(new BigDecimal("500.00")); // setting quantity
        vendorBranchRepository.save(branch); // saving branch

        List<VendorBranch> branches = vendorBranchRepository.findByVendorVendorId(testVendor.getVendorId()); // fetch branches
        assertFalse(branches.isEmpty()); // list should not be empty
        assertEquals(1, branches.size()); // should have exactly 1 branch
        System.out.println("TEST PASSED: testFindBranchesByVendorId_Success - Branches found = " + branches.size());
    }

    @Test // marks this as a JUnit test case
    public void testFindInventoryByBranchId_Success() {
        // tests native SQL query to get inventory quantity by branch ID
        VendorBranch branch = new VendorBranch(); // creating branch
        branch.setVendor(testVendor); // linking to vendor
        branch.setAddress(testAddress); // linking to address
        branch.setQuantity(new BigDecimal("500.00")); // setting quantity
        vendorBranchRepository.save(branch); // saving branch

        BigDecimal inventory = vendorBranchRepository.findInventoryByBranchId(branch.getBranchId()); // get inventory
        assertNotNull(inventory); // inventory should not be null
        assertEquals(new BigDecimal("500.00"), inventory); // quantity should match
        System.out.println("TEST PASSED: testFindInventoryByBranchId_Success - Inventory = " + inventory);
    }

    @Test // marks this as a JUnit test case
    public void testExistsByVendorAndAddress_Success() {
        // tests that branch existence check by vendor and address works
        VendorBranch branch = new VendorBranch(); // creating branch
        branch.setVendor(testVendor); // linking to vendor
        branch.setAddress(testAddress); // linking to address
        branch.setQuantity(new BigDecimal("500.00")); // setting quantity
        vendorBranchRepository.save(branch); // saving branch

        boolean exists = vendorBranchRepository.existsByVendorVendorIdAndAddressAddressId(
                testVendor.getVendorId(), testAddress.getAddressId()); // check existence
        assertTrue(exists); // should return true
        System.out.println("TEST PASSED: testExistsByVendorAndAddress_Success - Branch exists = " + exists);
    }

    @Test // marks this as a JUnit test case
    public void testDeleteVendorBranch_Success() {
        // tests that vendor branch can be deleted
        VendorBranch branch = new VendorBranch(); // creating branch
        branch.setVendor(testVendor); // linking to vendor
        branch.setAddress(testAddress); // linking to address
        branch.setQuantity(new BigDecimal("500.00")); // setting quantity
        vendorBranchRepository.save(branch); // saving branch

        Integer branchId = branch.getBranchId(); // store branch ID before delete
        vendorBranchRepository.deleteById(branchId); // delete branch

        Optional<VendorBranch> deleted = vendorBranchRepository.findById(branchId); // try to fetch deleted branch
        assertFalse(deleted.isPresent()); // should not exist anymore
        System.out.println("TEST PASSED: testDeleteVendorBranch_Success - Branch deleted successfully");
    }
}