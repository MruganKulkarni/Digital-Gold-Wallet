package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.request.WalletTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.WalletBalanceResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.exception.InsufficientBalanceException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.mapper.WalletMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.PaymentService;
import com.digitalgoldwallet.digital_gold_wallet.service.WalletService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/*
 * ============================================================
 * WALLET SERVICE IMPLEMENTATION
 * ============================================================
 */
@Service
public class WalletServiceImpl
        implements WalletService {

    /*
     * Dependencies
     */
    private final UserRepository userRepository;

    private final PaymentService paymentService;

    /*
     * Constructor Injection
     */
    public WalletServiceImpl(
            UserRepository userRepository,
            PaymentService paymentService
    ) {

        this.userRepository =
                userRepository;

        this.paymentService =
                paymentService;
    }

    /*
     * ============================================================
     * GET WALLET BALANCE
     * ============================================================
     */
    @Override
    public WalletBalanceResponseDto
    getWalletBalance(
            Integer userId
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );

        return WalletMapper
                .mapToWalletBalanceResponseDto(
                        user
                );
    }

    /*
     * ============================================================
     * CREDIT WALLET
     * ============================================================
     */
    @Override
    public PaymentResponseDto
    creditWallet(
            Integer userId,
            WalletTransactionRequestDto requestDto
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );

        /*
         * Update balance
         */
        user.setBalance(
                user.getBalance().add(
                        requestDto.getAmount()
                )
        );

        userRepository.save(user);

        /*
         * Create payment request
         */
        PaymentRequestDto paymentRequest =
                new PaymentRequestDto();

        paymentRequest.setUserId(userId);

        paymentRequest.setAmount(
                requestDto.getAmount()
        );

        paymentRequest.setPaymentMethod(
                requestDto.getPaymentMethod()
        );

        paymentRequest.setTransactionType(
                "Credited to wallet"
        );

        paymentRequest.setPaymentStatus(
                "Success"
        );

        return paymentService
                .createPayment(
                        paymentRequest
                );
    }

    /*
     * ============================================================
     * DEBIT WALLET
     * ============================================================
     */
    @Override
    public PaymentResponseDto
    debitWallet(
            Integer userId,
            WalletTransactionRequestDto requestDto
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found with ID: "
                                                + userId
                                )
                        );

        /*
         * Validate balance
         */
        if (user.getBalance().compareTo(
                requestDto.getAmount()) < 0) {

            throw new InsufficientBalanceException(
                    "Insufficient wallet balance"
            );
        }

        /*
         * Update balance
         */
        user.setBalance(
                user.getBalance().subtract(
                        requestDto.getAmount()
                )
        );

        userRepository.save(user);

        /*
         * Create payment request
         */
        PaymentRequestDto paymentRequest =
                new PaymentRequestDto();

        paymentRequest.setUserId(userId);

        paymentRequest.setAmount(
                requestDto.getAmount()
        );

        paymentRequest.setPaymentMethod(
                requestDto.getPaymentMethod()
        );

        paymentRequest.setTransactionType(
                "Debited from wallet"
        );

        paymentRequest.setPaymentStatus(
                "Success"
        );

        return paymentService
                .createPayment(
                        paymentRequest
                );
    }
}