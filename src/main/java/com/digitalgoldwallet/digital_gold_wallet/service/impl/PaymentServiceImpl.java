package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.exception.InsufficientBalanceException;
import com.digitalgoldwallet.digital_gold_wallet.exception.PaymentNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.mapper.PaymentMapper;
import com.digitalgoldwallet.digital_gold_wallet.repository.PaymentRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.PaymentService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl
        implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            UserRepository userRepository
    ) {

        this.paymentRepository =
                paymentRepository;

        this.userRepository =
                userRepository;
    }

    /*
     * ====================================================
     * CREATE PAYMENT
     * ====================================================
     */
    @Override
    public PaymentResponseDto createPayment(
            PaymentRequestDto requestDto
    ) {

        /*
         * Fetch user
         */
        User user =
                userRepository.findById(
                        requestDto.getUserId()
                ).orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with ID: "
                                        + requestDto.getUserId()
                        )
                );

        /*
         * CREDIT TO WALLET
         */
        if (requestDto.getTransactionType()
                .equalsIgnoreCase(
                        "Credited to wallet"
                )) {

            user.setBalance(
                    user.getBalance().add(
                            requestDto.getAmount()
                    )
            );
        }

        /*
         * DEBIT FROM WALLET
         */
        else if (requestDto.getTransactionType()
                .equalsIgnoreCase(
                        "Debited from wallet"
                )) {

            /*
             * Check balance
             */
            if (user.getBalance().compareTo(
                    requestDto.getAmount()) < 0) {

                throw new InsufficientBalanceException(
                        "Insufficient wallet balance"
                );
            }

            user.setBalance(
                    user.getBalance().subtract(
                            requestDto.getAmount()
                    )
            );
        }

        /*
         * Save updated user balance
         */
        userRepository.save(user);

        /*
         * Convert DTO -> Entity
         */
        Payment payment =
                PaymentMapper.mapToEntity(
                        requestDto,
                        user
                );

        /*
         * Save payment
         */
        Payment savedPayment =
                paymentRepository.save(
                        payment
                );

        /*
         * Convert Entity -> Response DTO
         */
        return PaymentMapper.mapToResponseDto(
                savedPayment
        );
    }

    /*
     * ====================================================
     * GET PAYMENT BY ID
     * ====================================================
     */
    @Override
    public PaymentResponseDto getPaymentById(
            Integer paymentId
    ) {

        Payment payment =
                paymentRepository.findById(
                        paymentId
                ).orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with ID: "
                                        + paymentId
                        )
                );

        return PaymentMapper.mapToResponseDto(
                payment
        );
    }

    /*
     * ====================================================
     * GET PAYMENTS BY USER ID
     * ====================================================
     */
    @Override
    public List<PaymentResponseDto>
    getPaymentsByUserId(
            Integer userId
    ) {

        /*
         * Validate user exists
         */
        if (!userRepository.existsById(
                userId)) {

            throw new UserNotFoundException(
                    "User not found with ID: "
                            + userId
            );
        }

        List<Payment> payments =
                paymentRepository
                        .findPaymentsByUserId(
                                userId
                        );

        return payments.stream()
                .map(PaymentMapper::mapToResponseDto)
                .collect(Collectors.toList());
    }
}