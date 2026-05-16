package com.digitalgoldwallet.digital_gold_wallet.repository; // declares the package this class belongs to

import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // imports Address entity
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // imports Vendor entity
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // imports VendorBranch entity

import org.junit.jupiter.api.Test; // marks a method as a JUnit 5 test case
import org.springframework.beans.factory.annotation.Autowired; // tells Spring to inject the dependency automatically
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase; // controls which DB is used during tests
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; // loads only the JPA layer for testing
import org.springframework.test.context.ActiveProfiles; // activates a specific Spring profile for the test

import java.math.BigDecimal; // used for precise decimal numbers like gold price and quantity
import java.util.List; // used when a query returns multiple results
import java.util.Optional; // used when a query may or may not return a result

import static org.junit.jupiter.api.Assertions.*; // imports all assertion methods

/*
 * WHY NO @BeforeEach / @AfterEach:
 *
 * The old tearDown used EntityManager to run SET FOREIGN_KEY_CHECKS = 0
 * as a native query. When Hibernate sees a native executeUpdate(), it first
 * flushes all pending JPA changes — this caused a DEADLOCK (MySQL Error 1213)
 * because JPA and native SQL were touching the same rows simultaneously.
 * That deadlock killed the DB connection pool, so every test after the first
 * failed with "HikariPool - Connection is not available".
 *
 * THE FIX:
 * @DataJpaTest automatically wraps EVERY test in a transaction that rolls
 * back after the test completes — any data saved is automatically removed.
 * No manual cleanup needed. Each test creates its own data via helper methods.
 */

@DataJpaTest
// loads only JPA beans: repositories + entities — NO controllers or services
// automatically wraps each test in a transaction that ROLLS BACK after the test
// this means data saved in one test never affects another test

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// by default @DataJpaTest replaces your DB with in-memory H2
// replace = NONE tells Spring: use the REAL MySQL from application.yaml
// needed because our native queries use MySQL-specific syntax H2 doesn't support

@ActiveProfiles("test")
// activates the "test" Spring profile
// falls back to application.yaml if no application-test.yaml exists

public class VendorRepositoryTest { // JUnit discovers and runs this class automatically

    @Autowired
    private VendorRepository vendorRepository; // Spring injects real VendorRepository — used to test vendor DB operations

    @Autowired
    private VendorBranchRepository vendorBranchRepository; // Spring injects real VendorBranchRepository — used to test branch DB operations

    @Autowired
    private AddressRepository addressRepository; // Spring injects AddressRepository — needed because VendorBranch has FK to addresses

    // ================================================================
    //  HELPER METHODS
    //  Each test calls only the helpers it needs — no shared state
    // ================================================================

    private Address createAndSaveAddress() {
        // creates a test Address, saves it to DB, returns saved object with generated ID

        Address address = new Address(); // creates a new empty Address object
        address.setStreet("MG Road"); // sets street field
        address.setCity("Bangalore"); // sets city field
        address.setState("Karnataka"); // sets state field
        address.setPostalCode("560001"); // sets postal code field
        address.setCountry("India"); // sets country field
        return addressRepository.save(address); // saves to DB — returned object has auto-generated addressId
    }

    private Vendor createAndSaveVendor() {
        // creates a test Vendor, saves it to DB, returns saved object with generated ID

        Vendor vendor = new Vendor(); // creates a new empty Vendor object
        vendor.setVendorName("Test Gold Traders"); // sets vendor name
        vendor.setDescription("Test vendor for JUnit"); // sets description
        vendor.setContactPersonName("Sparsh Garg"); // sets contact person name
        vendor.setContactEmail("sparsh@test.com"); // sets contact email
        vendor.setContactPhone("9999999999"); // sets contact phone
        vendor.setTotalGoldQuantity(new BigDecimal("1000.00")); // sets total gold quantity as precise decimal
        vendor.setCurrentGoldPrice(new BigDecimal("6400.00")); // sets gold price per gram as precise decimal
        return vendorRepository.save(vendor); // saves to DB — returned object has auto-generated vendorId
    }

    private VendorBranch createAndSaveBranch(Vendor vendor, Address address) {
        // creates a test VendorBranch linked to given vendor and address, saves it to DB

        VendorBranch branch = new VendorBranch(); // creates a new empty VendorBranch object
        branch.setVendor(vendor); // links branch to vendor — sets the vendor_id foreign key
        branch.setAddress(address); // links branch to address — sets the address_id foreign key
        branch.setQuantity(new BigDecimal("500.00")); // sets gold quantity stocked at this branch
        return vendorBranchRepository.save(branch); // saves to DB — returned object has auto-generated branchId
    }

    // ================================================================
    //  VENDOR TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    public void testSaveVendor_Success() {
        // verifies that a Vendor can be saved to the database successfully

        Vendor vendor = createAndSaveVendor(); // creates and saves a test vendor

        assertNotNull(vendor.getVendorId());
        // assertNotNull — checks vendorId is NOT null
        // MySQL auto-generates the ID on INSERT — if null, the save failed

        System.out.println("TEST PASSED: testSaveVendor_Success - Vendor saved with ID = " + vendor.getVendorId());
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindVendorById_Success() {
        // verifies that a saved Vendor can be retrieved from the DB by its ID

        Vendor vendor = createAndSaveVendor(); // saves vendor and gets its generated ID

        Optional<Vendor> found = vendorRepository.findById(vendor.getVendorId());
        // findById() searches the DB for a vendor with that ID
        // returns Optional<Vendor> — contains the vendor if found, empty if not

        assertTrue(found.isPresent());
        // assertTrue — checks that the condition is true
        // found.isPresent() is true when the Optional contains a value — vendor was found

        assertEquals("Test Gold Traders", found.get().getVendorName());
        // assertEquals — checks that both values are equal
        // found.get() retrieves the vendor from the Optional
        // verifies the name matches what we saved

        System.out.println("TEST PASSED: testFindVendorById_Success - Vendor found = " + found.get().getVendorName());
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindVendorById_NotFound() {
        // verifies that searching for a non-existent vendor returns empty — not an exception

        Optional<Vendor> found = vendorRepository.findById(99999);
        // ID 99999 does not exist in the database
        // findById() returns Optional.empty() when nothing is found — does NOT throw exception

        assertFalse(found.isPresent());
        // assertFalse — checks that the condition is false
        // found.isPresent() should be false — confirms "not found" is handled gracefully

        System.out.println("TEST PASSED: testFindVendorById_NotFound - Vendor not found as expected");
    }

    @Test // tells JUnit to run this method as a test case
    public void testUpdateVendor_Success() {
        // verifies that a saved Vendor's field can be updated in the database

        Vendor vendor = createAndSaveVendor(); // saves vendor with gold price 6400.00

        vendor.setCurrentGoldPrice(new BigDecimal("6500.00")); // changes the gold price to 6500.00
        vendorRepository.save(vendor);
        // save() on an existing entity (one that already has an ID) performs UPDATE not INSERT
        // Hibernate knows it's an update because vendorId is already set

        Optional<Vendor> updated = vendorRepository.findById(vendor.getVendorId());
        // fetches the vendor again fresh from the DB to verify the update was persisted

        assertTrue(updated.isPresent()); // confirms the vendor still exists after update
        assertEquals(new BigDecimal("6500.00"), updated.get().getCurrentGoldPrice());
        // verifies the gold price in DB is now 6500.00 — not the original 6400.00

        System.out.println("TEST PASSED: testUpdateVendor_Success - Gold price updated to = " + updated.get().getCurrentGoldPrice());
    }

    @Test // tells JUnit to run this method as a test case
    public void testDeleteVendor_Success() {
        // verifies that a saved Vendor can be deleted from the database

        Vendor vendor = createAndSaveVendor(); // saves a test vendor
        Integer vendorId = vendor.getVendorId(); // stores the ID before deleting

        vendorRepository.deleteById(vendorId); // deletes the vendor from DB using its ID

        Optional<Vendor> deleted = vendorRepository.findById(vendorId);
        // tries to find the vendor again — should return empty after deletion

        assertFalse(deleted.isPresent());
        // assertFalse — checks that the condition is false
        // deleted.isPresent() should be false — vendor no longer exists in DB

        System.out.println("TEST PASSED: testDeleteVendor_Success - Vendor deleted successfully");
    }

    @Test // tells JUnit to run this method as a test case
    public void testExistsByVendorName_Success() {
        // verifies the custom existsByVendorName query returns true for an existing name

        createAndSaveVendor(); // saves vendor with name "Test Gold Traders"

        boolean exists = vendorRepository.existsByVendorName("Test Gold Traders");
        // existsByVendorName is a Spring Data derived query — Spring auto-generates:
        // SELECT COUNT(*) > 0 FROM vendors WHERE vendor_name = ?
        // returns true if at least one vendor with that name exists

        assertTrue(exists); // confirms the name was found in the database

        System.out.println("TEST PASSED: testExistsByVendorName_Success - Vendor name exists = " + exists);
    }

    @Test // tells JUnit to run this method as a test case
    public void testExistsByVendorName_NotFound() {
        // verifies existsByVendorName returns false for a name that doesn't exist

        boolean exists = vendorRepository.existsByVendorName("Non Existing Vendor XYZ");
        // no vendor with this name was ever saved in this test — should return false

        assertFalse(exists); // confirms false is returned for a missing name

        System.out.println("TEST PASSED: testExistsByVendorName_NotFound - Vendor name not found as expected");
    }

    @Test // tells JUnit to run this method as a test case
    public void testExistsByContactEmail_Success() {
        // verifies the custom existsByContactEmail query returns true for an existing email

        createAndSaveVendor(); // saves vendor with email "sparsh@test.com"

        boolean exists = vendorRepository.existsByContactEmail("sparsh@test.com");
        // Spring Data auto-generates: SELECT COUNT(*) > 0 FROM vendors WHERE contact_email = ?

        assertTrue(exists); // confirms the email was found

        System.out.println("TEST PASSED: testExistsByContactEmail_Success - Email exists = " + exists);
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindByVendorName_Success() {
        // verifies the custom findByVendorName query returns the correct vendor

        createAndSaveVendor(); // saves vendor with name "Test Gold Traders"

        Optional<Vendor> found = vendorRepository.findByVendorName("Test Gold Traders");
        // findByVendorName is a Spring Data derived query:
        // SELECT * FROM vendors WHERE vendor_name = ?
        // returns Optional<Vendor>

        assertTrue(found.isPresent()); // confirms vendor was found by name
        assertEquals("sparsh@test.com", found.get().getContactEmail());
        // verifies the email of the found vendor matches — confirms correct record returned

        System.out.println("TEST PASSED: testFindByVendorName_Success - Vendor found by name");
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindGoldPriceByVendorId_Success() {
        // verifies the native SQL query returns the correct gold price for a vendor

        Vendor vendor = createAndSaveVendor(); // saves vendor with price 6400.00

        BigDecimal price = vendorRepository.findGoldPriceByVendorId(vendor.getVendorId());
        // findGoldPriceByVendorId uses a native SQL query:
        // SELECT current_gold_price FROM vendors WHERE vendor_id = ?
        // returns only the price as BigDecimal — not the full vendor object

        assertNotNull(price); // confirms price is not null — query returned a result
        assertEquals(new BigDecimal("6400.00"), price); // confirms the price matches what was saved

        System.out.println("TEST PASSED: testFindGoldPriceByVendorId_Success - Gold price = " + price);
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindAllVendors_Success() {
        // verifies that findAll() returns at least one vendor after saving one

        createAndSaveVendor(); // saves a test vendor to ensure list is not empty

        List<Vendor> vendors = vendorRepository.findAll();
        // findAll() is provided for free by JpaRepository
        // returns all vendors currently in the database as a List

        assertFalse(vendors.isEmpty());
        // assertFalse — checks that the condition is false
        // vendors.isEmpty() should be false — we just saved one vendor

        System.out.println("TEST PASSED: testFindAllVendors_Success - Total vendors = " + vendors.size());
    }

    // ================================================================
    //  VENDOR BRANCH TESTS
    // ================================================================

    @Test // tells JUnit to run this method as a test case
    public void testSaveVendorBranch_Success() {
        // verifies that a VendorBranch can be saved successfully

        Vendor vendor = createAndSaveVendor(); // saves a test vendor (needed as FK)
        Address address = createAndSaveAddress(); // saves a test address (needed as FK)
        VendorBranch branch = createAndSaveBranch(vendor, address); // saves branch linked to vendor and address

        assertNotNull(branch.getBranchId());
        // confirms branchId is NOT null — MySQL auto-generated it on INSERT

        System.out.println("TEST PASSED: testSaveVendorBranch_Success - Branch saved with ID = " + branch.getBranchId());
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindBranchesByVendorId_Success() {
        // verifies that all branches belonging to a vendor can be retrieved

        Vendor vendor = createAndSaveVendor(); // saves test vendor
        Address address = createAndSaveAddress(); // saves test address
        createAndSaveBranch(vendor, address); // saves one branch linked to this vendor

        List<VendorBranch> branches = vendorBranchRepository.findByVendorVendorId(vendor.getVendorId());
        // findByVendorVendorId is a Spring Data derived query:
        // SELECT * FROM vendor_branches WHERE vendor_id = ?
        // "VendorVendorId" means: navigate VendorBranch.vendor -> Vendor.vendorId

        assertFalse(branches.isEmpty()); // confirms at least one branch was returned
        assertEquals(1, branches.size()); // confirms exactly one branch exists for this vendor

        System.out.println("TEST PASSED: testFindBranchesByVendorId_Success - Branches found = " + branches.size());
    }

    @Test // tells JUnit to run this method as a test case
    public void testFindInventoryByBranchId_Success() {
        // verifies the native SQL query returns the correct gold quantity for a branch

        Vendor vendor = createAndSaveVendor(); // saves test vendor
        Address address = createAndSaveAddress(); // saves test address
        VendorBranch branch = createAndSaveBranch(vendor, address); // saves branch with quantity 500.00

        BigDecimal inventory = vendorBranchRepository.findInventoryByBranchId(branch.getBranchId());
        // findInventoryByBranchId uses a native SQL query:
        // SELECT quantity FROM vendor_branches WHERE branch_id = ?
        // returns the gold quantity at this branch as BigDecimal

        assertNotNull(inventory); // confirms inventory is not null — query returned a result
        assertEquals(new BigDecimal("500.00"), inventory); // confirms the quantity matches what was saved

        System.out.println("TEST PASSED: testFindInventoryByBranchId_Success - Inventory = " + inventory);
    }

    @Test // tells JUnit to run this method as a test case
    public void testExistsByVendorAndAddress_Success() {
        // verifies that existence check by vendor + address combination works correctly

        Vendor vendor = createAndSaveVendor(); // saves test vendor
        Address address = createAndSaveAddress(); // saves test address
        createAndSaveBranch(vendor, address); // saves a branch at this vendor + address combination

        boolean exists = vendorBranchRepository.existsByVendorVendorIdAndAddressAddressId(
                vendor.getVendorId(), address.getAddressId());
        // Spring Data derived query:
        // SELECT COUNT(*) > 0 FROM vendor_branches WHERE vendor_id = ? AND address_id = ?
        // used to prevent duplicate branches at the same vendor + address combination

        assertTrue(exists); // confirms the branch exists for this vendor + address pair

        System.out.println("TEST PASSED: testExistsByVendorAndAddress_Success - Branch exists = " + exists);
    }

    @Test // tells JUnit to run this method as a test case
    public void testDeleteVendorBranch_Success() {
        // verifies that a VendorBranch can be deleted from the database

        Vendor vendor = createAndSaveVendor(); // saves test vendor
        Address address = createAndSaveAddress(); // saves test address
        VendorBranch branch = createAndSaveBranch(vendor, address); // saves test branch

        Integer branchId = branch.getBranchId(); // stores the branch ID before deleting
        vendorBranchRepository.deleteById(branchId); // deletes the branch from DB using its ID

        Optional<VendorBranch> deleted = vendorBranchRepository.findById(branchId);
        // tries to find the branch again — should return empty after deletion

        assertFalse(deleted.isPresent());
        // assertFalse — deleted.isPresent() should be false — branch no longer exists in DB

        System.out.println("TEST PASSED: testDeleteVendorBranch_Success - Branch deleted successfully");
    }
}