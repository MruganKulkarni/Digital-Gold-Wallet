package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository
        extends JpaRepository<
        TransactionHistory,
        Integer> {

    List<TransactionHistory>
    findByUserUserId(Integer userId);

    List<TransactionHistory>
    findByBranchBranchId(Integer branchId);

}