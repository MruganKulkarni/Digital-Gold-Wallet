package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 * Entity mapped with users table
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    // Unique email
    @Column(unique = true, nullable = false)
    private String email;

    // User full name
    @Column(nullable = false)
    private String name;

    // Many users can share same address
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    // Wallet balance
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    // Account creation time
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}