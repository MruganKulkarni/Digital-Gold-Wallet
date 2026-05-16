package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Repository layer testing for Payment entity
 */

@DataJpaTest

/*
 * Prevents Spring Boot from replacing MySQL
 * with embedded H2 database during testing
 */
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * TEST CASE:
     * Save Payment
     */
    @Test
    @DisplayName("Test Save Payment")
    void testSavePayment() {

        User user = userRepository.findById(1)
                .orElse(null);

        assertNotNull(user);

        Payment payment = new Payment();

        payment.setUser(user);

        payment.setAmount(new BigDecimal("5000.00"));

        payment.setPaymentMethod("Google Pay");

        payment.setTransactionType("Credited to wallet");

        payment.setPaymentStatus("Success");

        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment =
                paymentRepository.save(payment);

        assertNotNull(savedPayment);

        assertNotNull(savedPayment.getPaymentId());

        assertEquals(
                "Google Pay",
                savedPayment.getPaymentMethod()
        );
    }

    /*
     * TEST CASE:
     * Find Payment By Id
     */
    @Test
    @DisplayName("Test Find Payment By Id")
    void testFindPaymentById() {

        User user = userRepository.findById(1)
                .orElse(null);

        assertNotNull(user);

        Payment payment = new Payment();

        payment.setUser(user);

        payment.setAmount(new BigDecimal("2000.00"));

        payment.setPaymentMethod("PhonePe");

        payment.setTransactionType("Credited to wallet");

        payment.setPaymentStatus("Success");

        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment =
                paymentRepository.save(payment);

        Payment foundPayment =
                paymentRepository.findById(
                        savedPayment.getPaymentId()
                ).orElse(null);

        assertNotNull(foundPayment);

        assertEquals(
                savedPayment.getPaymentId(),
                foundPayment.getPaymentId()
        );
    }

    /*
     * TEST CASE:
     * Update Payment
     */
    @Test
    @DisplayName("Test Update Payment")
    void testUpdatePayment() {

        User user = userRepository.findById(1)
                .orElse(null);

        assertNotNull(user);

        Payment payment = new Payment();

        payment.setUser(user);

        payment.setAmount(new BigDecimal("3000.00"));

        payment.setPaymentMethod("Bank Transfer");

        payment.setTransactionType("Debited from wallet");

        payment.setPaymentStatus("Success");

        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment =
                paymentRepository.save(payment);

        savedPayment.setPaymentStatus("Success");

        Payment updatedPayment =
                paymentRepository.save(savedPayment);

        assertEquals(
                "Success",
                updatedPayment.getPaymentStatus()
        );
    }

    /*
     * TEST CASE:
     * Delete Payment
     */
    @Test
    @DisplayName("Test Delete Payment")
    void testDeletePayment() {

        User user = userRepository.findById(1)
                .orElse(null);

        assertNotNull(user);

        Payment payment = new Payment();

        payment.setUser(user);

        payment.setAmount(new BigDecimal("1000.00"));

        payment.setPaymentMethod("Credit Card");

        payment.setTransactionType("Credited to wallet");

        payment.setPaymentStatus("Success");

        payment.setCreatedAt(LocalDateTime.now());

        Payment savedPayment =
                paymentRepository.save(payment);

        Integer paymentId =
                savedPayment.getPaymentId();

        paymentRepository.deleteById(paymentId);

        boolean exists =
                paymentRepository.existsById(paymentId);

        assertFalse(exists);
    }

    /*
     * TEST CASE:
     * Find Payments By UserId
     */
    @Test
    @DisplayName("Test Find Payments By UserId")
    void testFindPaymentsByUserId() {

        List<Payment> payments =
                paymentRepository.findPaymentsByUserId(1);

        assertNotNull(payments);
    }

    /*
     * TEST CASE:
     * Find Payments By UserId And Status
     */
    @Test
    @DisplayName("Test Find By UserId And Status")
    void testFindByUserIdAndStatus() {

        List<Payment> payments =
                paymentRepository.findByUserIdAndStatus(
                        1,
                        "Success"
                );

        assertNotNull(payments);
    }
}