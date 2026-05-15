package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/*
 * Repository Interface for User Entity
 *
 * JpaRepository automatically provides:
 * save()
 * findById()
 * findAll()
 * deleteById()
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /*
     * Find user using email
     */
    Optional<User> findByEmail(String email);

    /*
     * Check if email already exists
     */
    boolean existsByEmail(String email);

    /*
     * Custom JPQL Query
     *
     * Fetch users whose balance is greater than given amount
     */
    @Query("SELECT u FROM User u WHERE u.balance > :amount")
    List<User> findUsersWithBalanceGreaterThan(BigDecimal amount);
}