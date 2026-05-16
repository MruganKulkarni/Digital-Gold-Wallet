package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * AddressRepositoryTest
 * ============================================================
 *
 * JUnit 5 Repository Layer Testing
 *
 * Tests:
 * - CREATE
 * - READ
 * - UPDATE
 * - DELETE
 * - FIND ALL
 *
 * Uses real MySQL database.
 * ============================================================
 */

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("test")
public class AddressRepositoryTest {

    /*
     * Repository Injection
     */
    @Autowired
    private AddressRepository addressRepository;

    /*
     * Test data object
     */
    private Address testAddress;

    /*
     * ============================================================
     * @BeforeEach
     *
     * Runs before every test.
     * Creates fresh Address.
     * ============================================================
     */
    @BeforeEach
    public void setUp() {

        /*
         * Create Address
         */
        testAddress = new Address();

        testAddress.setStreet("Anna Nagar");
        testAddress.setCity("Chennai");
        testAddress.setState("Tamil Nadu");
        testAddress.setPostalCode(
                "600040"
                        + System.currentTimeMillis()
        );

        testAddress.setCountry("India");

        /*
         * Save Address
         */
        testAddress =
                addressRepository.save(testAddress);
    }

    /*
     * ============================================================
     * @AfterEach
     *
     * Deletes ONLY records created by this test.
     * Safe for shared team database.
     * ============================================================
     */
    @AfterEach
    public void tearDown() {

        if (testAddress != null
                && testAddress.getAddressId() != null
                && addressRepository.existsById(
                testAddress.getAddressId())) {

            addressRepository.deleteById(
                    testAddress.getAddressId()
            );
        }
    }

    /*
     * ============================================================
     * TEST 1 — CREATE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address")
    public void testCreateAddress() {

        assertNotNull(
                testAddress.getAddressId(),
                "Address ID should not be null"
        );

        assertEquals(
                "Chennai",
                testAddress.getCity()
        );

        System.out.println(
                "CREATE ADDRESS TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 2 — READ ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Read Address")
    public void testReadAddress() {

        Optional<Address> found =
                addressRepository.findById(
                        testAddress.getAddressId()
                );

        assertTrue(found.isPresent());

        assertEquals(
                "Chennai",
                found.get().getCity()
        );

        System.out.println(
                "READ ADDRESS TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 3 — UPDATE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Update Address")
    public void testUpdateAddress() {

        /*
         * Update city
         */
        testAddress.setCity("Bangalore");

        /*
         * Save updated address
         */
        Address updated =
                addressRepository.save(testAddress);

        /*
         * Verify update
         */
        assertEquals(
                "Bangalore",
                updated.getCity()
        );

        System.out.println(
                "UPDATE ADDRESS TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 4 — DELETE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Delete Address")
    public void testDeleteAddress() {

        Integer addressId =
                testAddress.getAddressId();

        /*
         * Delete address
         */
        addressRepository.deleteById(addressId);

        /*
         * Verify deletion
         */
        boolean exists =
                addressRepository.existsById(addressId);

        assertFalse(exists);

        System.out.println(
                "DELETE ADDRESS TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 5 — FIND ALL ADDRESSES
     * ============================================================
     */
    @Test
    @DisplayName("Test Find All Addresses")
    public void testFindAllAddresses() {

        List<Address> addresses =
                addressRepository.findAll();

        assertFalse(addresses.isEmpty());

        System.out.println(
                "FIND ALL ADDRESSES TEST PASSED"
        );
    }
}