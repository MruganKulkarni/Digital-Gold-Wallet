package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * PaymentRepositoryTest
 * ============================================================
 *
 * Repository layer testing for Payment entity
 *
 * Tests:
 * - CREATE
 * - READ
 * - UPDATE
 * - DELETE
 * - CUSTOM QUERY
 *
 * Uses real MySQL database
 * ============================================================
 */

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("test")
public class PaymentRepositoryTest {

    /*
     * Repository Injection
     */
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * Test objects
     */
    private User testUser;

    private Payment testPayment;

    /*
     * ============================================================
     * Runs before every test
     * ============================================================
     */
    @BeforeEach
    public void setUp() {

        /*
         * Fetch users dynamically
         */
        List<User> users =
                userRepository.findAll();

        /*
         * Ensure DB contains at least one user
         */
        assertFalse(users.isEmpty());

        /*
         * Use first available user
         */
        testUser = users.get(0);

        /*
         * Create payment
         */
        testPayment = new Payment();

        testPayment.setUser(testUser);

        testPayment.setAmount(
                new BigDecimal("5000.00")
        );

        testPayment.setPaymentMethod(
                "Google Pay"
        );

        testPayment.setTransactionType(
                "Credited to wallet"
        );

        testPayment.setPaymentStatus(
                "Success"
        );

        testPayment.setCreatedAt(
                LocalDateTime.now()
        );

        /*
         * Save payment
         */
        testPayment =
                paymentRepository.save(testPayment);
    }

    /*
     * ============================================================
     * Cleanup after every test
     * ============================================================
     */
    @AfterEach
    public void tearDown() {

        /*
         * Delete only created payment
         */
        if (testPayment != null
                && testPayment.getPaymentId() != null
                && paymentRepository.existsById(
                testPayment.getPaymentId())) {

            paymentRepository.deleteById(
                    testPayment.getPaymentId()
            );
        }
    }

    /*
     * ============================================================
     * TEST 1 — CREATE PAYMENT
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Payment")
    public void testCreatePayment() {

        assertNotNull(
                testPayment.getPaymentId()
        );

        assertEquals(
                "Success",
                testPayment.getPaymentStatus()
        );

        System.out.println(
                "CREATE PAYMENT TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 2 — READ PAYMENT
     * ============================================================
     */
    @Test
    @DisplayName("Test Read Payment")
    public void testReadPayment() {

        Optional<Payment> found =
                paymentRepository.findById(
                        testPayment.getPaymentId()
                );

        assertTrue(found.isPresent());

        assertEquals(
                testPayment.getPaymentId(),
                found.get().getPaymentId()
        );

        System.out.println(
                "READ PAYMENT TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 3 — UPDATE PAYMENT
     * ============================================================
     */
    @Test
    @DisplayName("Test Update Payment")
    public void testUpdatePayment() {

        /*
         * Update payment amount
         */
        testPayment.setAmount(
                new BigDecimal("10000.00")
        );

        /*
         * Save updated payment
         */
        Payment updated =
                paymentRepository.save(testPayment);

        /*
         * Verify update
         */
        assertEquals(
                new BigDecimal("10000.00"),
                updated.getAmount()
        );

        System.out.println(
                "UPDATE PAYMENT TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 4 — DELETE PAYMENT
     * ============================================================
     */
    @Test
    @DisplayName("Test Delete Payment")
    public void testDeletePayment() {

        Integer paymentId =
                testPayment.getPaymentId();

        /*
         * Delete payment
         */
        paymentRepository.deleteById(paymentId);

        /*
         * Verify deletion
         */
        boolean exists =
                paymentRepository.existsById(paymentId);

        assertFalse(exists);

        System.out.println(
                "DELETE PAYMENT TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 5 — FIND PAYMENTS BY USER ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Find Payments By UserId")
    public void testFindPaymentsByUserId() {

        List<Payment> payments =
                paymentRepository.findPaymentsByUserId(
                        testUser.getUserId()
                );

        assertNotNull(payments);

        assertFalse(payments.isEmpty());

        System.out.println(
                "FIND PAYMENTS BY USER ID TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST 6 — FIND PAYMENTS BY USER ID AND STATUS
     * ============================================================
     */
    @Test
    @DisplayName("Test Find By UserId And Status")
    public void testFindByUserIdAndStatus() {

        List<Payment> payments =
                paymentRepository.findByUserIdAndStatus(
                        testUser.getUserId(),
                        "Success"
                );

        assertNotNull(payments);

        assertFalse(payments.isEmpty());

        System.out.println(
                "CUSTOM QUERY TEST PASSED"
        );
    }
}