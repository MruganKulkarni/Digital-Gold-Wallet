package com.digitalgoldwallet.digital_gold_wallet.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.ui.Model;

/*
 * ============================================================
 * GOLD VIEW CONTROLLER
 * ============================================================
 */

@Controller
public class GoldViewController {

    /*
     * ============================================================
     * GOLD MODULE PAGE
     * ============================================================
     */
    @GetMapping("/gold-module")
    public String goldModule() {

        return "gold-module";
    }

    /*
     * ============================================================
     * BUY GOLD PAGE
     * ============================================================
     */
    @GetMapping("/gold/buy")
    public String buyGoldPage() {

        return "buy-gold";
    }

    /*
     * ============================================================
     * SELL GOLD PAGE
     * ============================================================
     */
    @GetMapping("/gold/sell")
    public String sellGoldPage() {

        return "sell-gold";
    }

    /*
     * ============================================================
     * USER GOLD HOLDINGS
     * ============================================================
     */
    @GetMapping("/gold/user/{id}")
    public String userGold(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "userId",
                id
        );

        return "user-gold";
    }

    /*
     * ============================================================
     * BRANCH GOLD HOLDINGS
     * ============================================================
     */
    @GetMapping("/gold/branch/{id}")
    public String branchGold(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "branchId",
                id
        );

        return "branch-gold";
    }

    /*
     * ============================================================
     * CONVERT GOLD PAGE
     * ============================================================
     */
    @GetMapping("/gold/convert")
    public String convertGoldPage() {

        return "convert-gold";
    }

    /*
     * ============================================================
     * PHYSICAL GOLD PAGE
     * ============================================================
     */
    @GetMapping("/gold/physical/{id}")
    public String physicalGold(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "userId",
                id
        );

        return "physical-gold";
    }

    /*
     * ============================================================
     * PHYSICAL TRANSACTION PAGE
     * ============================================================
     */
    @GetMapping("/physical-transactions/{id}")
    public String physicalTransaction(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "transactionId",
                id
        );

        return "physical-transaction";
    }

    /*
     * ============================================================
     * USER TRANSACTION HISTORY
     * ============================================================
     */
    @GetMapping("/transactions/user/{id}")
    public String userTransactions(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "userId",
                id
        );

        return "user-transactions";
    }

    /*
     * ============================================================
     * BRANCH TRANSACTION HISTORY
     * ============================================================
     */
    @GetMapping("/transactions/branch/{id}")
    public String branchTransactions(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "branchId",
                id
        );

        return "branch-transactions";
    }
}