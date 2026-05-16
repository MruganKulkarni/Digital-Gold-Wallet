package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository
        extends JpaRepository<TransactionHistory, Integer> {
}