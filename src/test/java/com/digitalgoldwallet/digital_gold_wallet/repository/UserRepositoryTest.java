package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * UserRepositoryTest
 * ============================================================
 *
 * JUnit 5 Repository Layer Testing
 *
 * Tests:
 * - CREATE
 * - READ
 * - UPDATE
 * - DELETE
 * - CUSTOM QUERY
 * - EXISTS
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
public class UserRepositoryTest {

    /*
     * Repository Injection
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    /*
     * Test data objects
     */
    private Address testAddress;

    private User testUser;

    /*
     * ============================================================
     * @BeforeEach
     *
     * Runs before every test.
     * Creates fresh Address and User.
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
        testAddress.setPostalCode("600040");
        testAddress.setCountry("India");

        /*
         * Save Address
         */
        testAddress = addressRepository.save(testAddress);

        /*
         * Create User
         */
        testUser = new User();

        testUser.setName("Varsha Karthikeyan");

        /*
         * Unique email prevents duplicate constraint issues
         */
        testUser.setEmail(
                "varsha"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        testUser.setBalance(new BigDecimal("5000.00"));

        testUser.setAddress(testAddress);

        /*
         * Save User
         */
        testUser = userRepository.save(testUser);
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

        /*
         * Delete user first
         * because users table contains FK to address
         */
        if (testUser != null
                && testUser.getUserId() != null
                && userRepository.existsById(testUser.getUserId())) {

            userRepository.deleteById(testUser.getUserId());
        }

        /*
         * Delete address after deleting user
         */
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
     * TEST 1 — CREATE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Create User")
    public void testCreateUser() {

        assertNotNull(
                testUser.getUserId(),
                "User ID should not be null"
        );

        assertEquals(
                "Varsha Karthikeyan",
                testUser.getName()
        );

        System.out.println(
                "CREATE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 2 — READ USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Read User")
    public void testReadUser() {

        Optional<User> found =
                userRepository.findById(
                        testUser.getUserId()
                );

        assertTrue(found.isPresent());

        assertEquals(
                "Varsha Karthikeyan",
                found.get().getName()
        );

        System.out.println(
                "READ TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 3 — UPDATE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Update User")
    public void testUpdateUser() {

        /*
         * Update balance
         */
        testUser.setBalance(
                new BigDecimal("10000.00")
        );

        /*
         * Save updated user
         */
        User updated =
                userRepository.save(testUser);

        /*
         * Verify update
         */
        assertEquals(
                new BigDecimal("10000.00"),
                updated.getBalance()
        );

        System.out.println(
                "UPDATE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 4 — DELETE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Delete User")
    public void testDeleteUser() {

        Integer userId =
                testUser.getUserId();

        /*
         * Delete user
         */
        userRepository.deleteById(userId);

        /*
         * Verify deletion
         */
        boolean exists =
                userRepository.existsById(userId);

        assertFalse(exists);

        System.out.println(
                "DELETE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 5 — FIND BY EMAIL
     * ============================================================
     */
    @Test
    @DisplayName("Test Find By Email")
    public void testFindByEmail() {

        Optional<User> found =
                userRepository.findByEmail(
                        testUser.getEmail()
                );

        assertTrue(found.isPresent());

        assertEquals(
                testUser.getEmail(),
                found.get().getEmail()
        );

        System.out.println(
                "FIND BY EMAIL TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 6 — EXISTS BY EMAIL TRUE
     * ============================================================
     */
    @Test
    @DisplayName("Test Exists By Email True")
    public void testExistsByEmailTrue() {

        boolean exists =
                userRepository.existsByEmail(
                        testUser.getEmail()
                );

        assertTrue(exists);

        System.out.println(
                "EXISTS TRUE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 7 — EXISTS BY EMAIL FALSE
     * ============================================================
     */
    @Test
    @DisplayName("Test Exists By Email False")
    public void testExistsByEmailFalse() {

        boolean exists =
                userRepository.existsByEmail(
                        "unknown@test.com"
                );

        assertFalse(exists);

        System.out.println(
                "EXISTS FALSE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 8 — FIND ALL USERS
     * ============================================================
     */
    @Test
    @DisplayName("Test Find All Users")
    public void testFindAllUsers() {

        List<User> users =
                userRepository.findAll();

        assertFalse(users.isEmpty());

        System.out.println(
                "FIND ALL TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 9 — CUSTOM QUERY
     * ============================================================
     */
    @Test
    @DisplayName(
            "Test Find Users With Balance Greater Than"
    )
    public void testCustomQueryBalanceGreaterThan() {

        List<User> users =
                userRepository
                        .findUsersWithBalanceGreaterThan(
                                new BigDecimal("1000.00")
                        );

        assertNotNull(users);

        assertFalse(users.isEmpty());

        System.out.println(
                "CUSTOM QUERY TEST PASSED"
        );
    }
}