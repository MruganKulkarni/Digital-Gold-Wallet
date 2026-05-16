package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VirtualGoldHoldingRepository
        extends JpaRepository<VirtualGoldHolding, Integer> {
}