package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * VirtualGoldHoldingRepositoryTest
 * ============================================================
 */

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class VirtualGoldHoldingRepositoryTest {

    @Autowired
    private VirtualGoldHoldingRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorBranchRepository vendorBranchRepository;

    @Autowired
    private AddressRepository addressRepository;

    private User testUser;

    private Address testAddress;

    private Vendor testVendor;

    private VendorBranch testBranch;

    private VirtualGoldHolding holding;

    @BeforeEach
    public void setUp() {

        /*
         * Create Address
         */
        testAddress = new Address();

        testAddress.setStreet(
                "Street_" + System.currentTimeMillis()
        );

        testAddress.setCity(
                "Chennai"
        );

        testAddress.setState(
                "Tamil Nadu"
        );

        testAddress.setPostalCode(
                "600001"
        );

        testAddress.setCountry(
                "India"
        );

        testAddress =
                addressRepository.save(testAddress);

        /*
         * Fetch existing user
         */
        List<User> users =
                userRepository.findAll();

        assertFalse(users.isEmpty());

        testUser = users.get(0);

        /*
         * Create Vendor
         */
        testVendor = new Vendor();

        testVendor.setVendorName(
                "Vendor_" + System.currentTimeMillis()
        );

        testVendor.setDescription(
                "Gold Vendor"
        );

        testVendor.setContactPersonName(
                "Tanmay"
        );

        testVendor.setContactEmail(
                "vendor"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        testVendor.setContactPhone(
                "9999999999"
        );

        testVendor.setWebsiteUrl(
                "https://vendor.com"
        );

        testVendor.setTotalGoldQuantity(
                new BigDecimal("1000")
        );

        testVendor.setCurrentGoldPrice(
                new BigDecimal("6400")
        );

        testVendor =
                vendorRepository.save(testVendor);

        /*
         * Create Branch
         */
        testBranch = new VendorBranch();

        testBranch.setVendor(testVendor);

        testBranch.setAddress(testAddress);

        testBranch.setQuantity(
                new BigDecimal("500")
        );

        testBranch =
                vendorBranchRepository.save(testBranch);

        /*
         * Create Holding
         */
        holding = new VirtualGoldHolding();

        holding.setUser(testUser);

        holding.setBranch(testBranch);

        holding.setQuantity(
                new BigDecimal("5")
        );

        holding =
                repository.save(holding);
    }

    @AfterEach
    public void tearDown() {

        if (holding != null
                && holding.getHoldingId() != null
                && repository.existsById(
                holding.getHoldingId())) {

            repository.deleteById(
                    holding.getHoldingId()
            );
        }

        if (testBranch != null
                && testBranch.getBranchId() != null
                && vendorBranchRepository.existsById(
                testBranch.getBranchId())) {

            vendorBranchRepository.deleteById(
                    testBranch.getBranchId()
            );
        }

        if (testVendor != null
                && testVendor.getVendorId() != null
                && vendorRepository.existsById(
                testVendor.getVendorId())) {

            vendorRepository.deleteById(
                    testVendor.getVendorId()
            );
        }

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
     * TEST CREATE
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Holding")
    public void testCreateHolding() {

        assertNotNull(
                holding.getHoldingId()
        );
    }

    /*
     * ============================================================
     * TEST READ
     * ============================================================
     */
    @Test
    @DisplayName("Test Read Holding")
    public void testReadHolding() {

        Optional<VirtualGoldHolding> found =
                repository.findById(
                        holding.getHoldingId()
                );

        assertTrue(found.isPresent());

        assertEquals(
                holding.getQuantity(),
                found.get().getQuantity()
        );
    }

    /*
     * ============================================================
     * TEST UPDATE
     * ============================================================
     */
    @Test
    @DisplayName("Test Update Holding")
    public void testUpdateHolding() {

        holding.setQuantity(
                new BigDecimal("10")
        );

        VirtualGoldHolding updated =
                repository.save(holding);

        assertEquals(
                new BigDecimal("10"),
                updated.getQuantity()
        );
    }

    /*
     * ============================================================
     * TEST DELETE
     * ============================================================
     */
    @Test
    @DisplayName("Test Delete Holding")
    public void testDeleteHolding() {

        Integer id =
                holding.getHoldingId();

        repository.deleteById(id);

        Optional<VirtualGoldHolding> deleted =
                repository.findById(id);

        assertFalse(deleted.isPresent());
    }

    /*
     * ============================================================
     * TEST FIND ALL
     * ============================================================
     */
    @Test
    @DisplayName("Test Find All Holdings")
    public void testFindAllHoldings() {

        List<VirtualGoldHolding> holdings =
                repository.findAll();

        assertFalse(holdings.isEmpty());
    }
}