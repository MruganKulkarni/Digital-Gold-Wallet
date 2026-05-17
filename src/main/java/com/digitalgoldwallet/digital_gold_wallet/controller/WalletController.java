package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.WalletService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/*
 * ============================================================
 * WALLET CONTROLLER
 * ============================================================
 *
 * Handles:
 * - wallet balance
 * - wallet credit
 * - wallet debit
 *
 * ============================================================
 */

@RestController
@RequestMapping("/api/v1/wallets")
@Tag(
        name = "Wallet Controller",
        description = "APIs for wallet operations"
)
public class WalletController {

    /*
     * Wallet service dependency
     */
    private final WalletService walletService;

    /*
     * Constructor Injection
     */
    public WalletController(
            WalletService walletService
    ) {

        this.walletService =
                walletService;
    }

    /*
     * ============================================================
     * GET WALLET BALANCE
     * ============================================================
     */
    @GetMapping("/{userId}/balance")
    @Operation(
            summary = "Get wallet balance"
    )
    public ResponseEntity<WalletBalanceResponseDto>
    getWalletBalance(
            @PathVariable
            Integer userId
    ) {

        WalletBalanceResponseDto response =
                walletService.getWalletBalance(
                        userId
                );

        return ResponseEntity.ok(
                response
        );
    }

    /*
     * ============================================================
     * CREDIT WALLET
     * ============================================================
     */
    @PostMapping("/{userId}/credit")
    @Operation(
            summary = "Add money to wallet"
    )
    public ResponseEntity<PaymentResponseDto>
    creditWallet(
            @PathVariable
            Integer userId,

            @Valid
            @RequestBody
            WalletTransactionRequestDto requestDto
    ) {

        PaymentResponseDto response =
                walletService.creditWallet(
                        userId,
                        requestDto
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    /*
     * ============================================================
     * DEBIT WALLET
     * ============================================================
     */
    @PostMapping("/{userId}/debit")
    @Operation(
            summary = "Debit money from wallet"
    )
    public ResponseEntity<PaymentResponseDto>
    debitWallet(
            @PathVariable
            Integer userId,

            @Valid
            @RequestBody
            WalletTransactionRequestDto requestDto
    ) {

        PaymentResponseDto response =
                walletService.debitWallet(
                        userId,
                        requestDto
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }
}