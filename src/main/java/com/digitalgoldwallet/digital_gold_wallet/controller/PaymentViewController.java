package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.repository.PaymentRepository;

import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
 * ============================================================
 * PAYMENT VIEW CONTROLLER
 * ============================================================
 */

@Controller
public class PaymentViewController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    /*
     * ============================================================
     * PAYMENT MODULE PAGE
     * ============================================================
     */
    @GetMapping("/payment-module")
    public String paymentModule() {

        return "payment-module";
    }

    /*
     * ============================================================
     * CREDIT WALLET PAGE
     * ============================================================
     */
    @GetMapping("/wallets/credit/{id}")
    public String creditWallet(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "userId",
                id
        );

        return "credit-wallet";
    }

    /*
     * ============================================================
     * DEBIT WALLET PAGE
     * ============================================================
     */
    @GetMapping("/wallets/debit/{id}")
    public String debitWallet(
            @PathVariable Integer id,
            Model model
    ) {

        model.addAttribute(
                "userId",
                id
        );

        return "debit-wallet";
    }

    /*
     * ============================================================
     * WALLET BALANCE
     * ============================================================
     */
    @GetMapping("/wallets/balance/{id}")
    public String walletBalance(
            @PathVariable Integer id,
            Model model
    ) {

        User user =
                userRepository
                        .findById(id)
                        .orElse(null);

        model.addAttribute(
                "userId",
                id
        );

        if (user != null) {

            model.addAttribute(
                    "balance",
                    user.getBalance()
            );
        }

        return "wallet-balance";
    }

    /*
     * ============================================================
     * CREATE PAYMENT PAGE
     * ============================================================
     */
    @GetMapping("/payments/create")
    public String createPayment() {

        return "create-payment";
    }

    /*
     * ============================================================
     * VIEW PAYMENT
     * ============================================================
     */
    @GetMapping("/payments/view/{id}")
    public String viewPayment(
            @PathVariable Integer id,
            Model model
    ) {

        Payment payment =
                paymentRepository
                        .findById(id)
                        .orElse(null);

        model.addAttribute(
                "payment",
                payment
        );

        return "view-payment";
    }

    /*
     * ============================================================
     * PAYMENT HISTORY
     * ============================================================
     */
    @GetMapping("/payments/history/{id}")
    public String paymentHistory(
            @PathVariable Integer id,
            Model model
    ) {

        List<Payment> payments =
                paymentRepository
                        .findAll();

        model.addAttribute(
                "payments",
                payments
        );

        return "payment-history";
    }
}