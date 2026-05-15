package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
 * Repository interface for Payment entity
 * Provides CRUD operations using JpaRepository
 */
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    /*
     * Fetch all payments of a specific user
     * Using JPQL query
     */
    @Query("SELECT p FROM Payment p WHERE p.user.userId = :userId")
    List<Payment> findPaymentsByUserId(
            @Param("userId") Integer userId
    );

    /*
     * Fetch payments using userId and payment status
     * Native SQL query as mentioned in project guide
     */
    @Query(value = """
            SELECT * FROM payments
            WHERE user_id = :userId
            AND payment_status = :status
            """, nativeQuery = true)
    List<Payment> findByUserIdAndStatus(
            @Param("userId") Integer userId,
            @Param("status") String status
    );

    /*
     * Paginated payment history of a user
     */
    @Query("SELECT p FROM Payment p WHERE p.user.userId = :userId")
    Page<Payment> findPaymentsByUserId(
            @Param("userId") Integer userId,
            Pageable pageable
    );
}