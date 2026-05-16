package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface
PhysicalGoldTransactionRepository
        extends JpaRepository<
        PhysicalGoldTransaction,
        Integer>{

    List<PhysicalGoldTransaction>
    findByUserUserId(Integer userId);

}