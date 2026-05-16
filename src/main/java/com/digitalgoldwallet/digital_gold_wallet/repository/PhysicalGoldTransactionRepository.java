package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicalGoldTransactionRepository
        extends JpaRepository<PhysicalGoldTransaction, Integer> {
}