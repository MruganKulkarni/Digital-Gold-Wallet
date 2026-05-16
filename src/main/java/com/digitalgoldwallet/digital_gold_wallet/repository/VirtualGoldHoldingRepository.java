package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Handles virtual holdings operations
 */

public interface VirtualGoldHoldingRepository
        extends JpaRepository<
        VirtualGoldHolding,
        Integer> {

    List<VirtualGoldHolding>
    findByUserUserId(Integer userId);

    List<VirtualGoldHolding>
    findByBranchBranchId(Integer branchId);

}