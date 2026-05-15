package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository Interface for Address Entity
 */

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}