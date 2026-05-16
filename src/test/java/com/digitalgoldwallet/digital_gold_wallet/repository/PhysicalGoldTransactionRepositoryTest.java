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
 * PhysicalGoldTransactionRepositoryTest
 * ============================================================
 */

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class PhysicalGoldTransactionRepositoryTest {

    @Autowired
    private PhysicalGoldTransactionRepository repository;

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

    private PhysicalGoldTransaction transaction;

    @BeforeEach
    public void setUp() {

        testAddress = new Address();

        testAddress.setStreet(
                "Street_" + System.currentTimeMillis()
        );

        testAddress.setCity("Chennai");

        testAddress.setState("Tamil Nadu");

        testAddress.setPostalCode("600001");

        testAddress.setCountry("India");

        testAddress =
                addressRepository.save(testAddress);

        List<User> users =
                userRepository.findAll();

        assertFalse(users.isEmpty());

        testUser = users.get(0);

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

        testBranch = new VendorBranch();

        testBranch.setVendor(testVendor);

        testBranch.setAddress(testAddress);

        testBranch.setQuantity(
                new BigDecimal("500")
        );

        testBranch =
                vendorBranchRepository.save(testBranch);

        transaction =
                new PhysicalGoldTransaction();

        transaction.setUser(testUser);

        transaction.setBranch(testBranch);

        transaction.setQuantity(
                new BigDecimal("1")
        );

        transaction.setDeliveryAddress(
                testAddress
        );

        transaction =
                repository.save(transaction);
    }

    @AfterEach
    public void tearDown() {

        if (transaction != null
                && transaction.getTransactionId() != null
                && repository.existsById(
                transaction.getTransactionId())) {

            repository.deleteById(
                    transaction.getTransactionId()
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

    @Test
    @DisplayName("Test Create Physical Transaction")
    public void testCreateTransaction() {

        assertNotNull(
                transaction.getTransactionId()
        );
    }

    @Test
    @DisplayName("Test Read Physical Transaction")
    public void testReadTransaction() {

        Optional<PhysicalGoldTransaction> found =
                repository.findById(
                        transaction.getTransactionId()
                );

        assertTrue(found.isPresent());
    }

    @Test
    @DisplayName("Test Update Physical Transaction")
    public void testUpdateTransaction() {

        transaction.setQuantity(
                new BigDecimal("3")
        );

        PhysicalGoldTransaction updated =
                repository.save(transaction);

        assertEquals(
                new BigDecimal("3"),
                updated.getQuantity()
        );
    }

    @Test
    @DisplayName("Test Delete Physical Transaction")
    public void testDeleteTransaction() {

        Integer id =
                transaction.getTransactionId();

        repository.deleteById(id);

        Optional<PhysicalGoldTransaction> deleted =
                repository.findById(id);

        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("Test Find All Physical Transactions")
    public void testFindAllTransactions() {

        List<PhysicalGoldTransaction> transactions =
                repository.findAll();

        assertFalse(transactions.isEmpty());
    }
}