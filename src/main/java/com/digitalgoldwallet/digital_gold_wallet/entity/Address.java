package com.digitalgoldwallet.digital_gold_wallet.entity;

import jakarta.persistence.*;
import lombok.*;

/*
 * Entity mapped with addresses table
 */
@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    private String street;

    private String city;

    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    private String country;
}