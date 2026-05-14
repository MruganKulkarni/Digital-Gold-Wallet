package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository layer for Address entity
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
}