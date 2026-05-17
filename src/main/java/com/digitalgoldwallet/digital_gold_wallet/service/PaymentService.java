package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    /*
     * Create payment
     */
    PaymentResponseDto createPayment(
            PaymentRequestDto requestDto
    );

    /*
     * Get payment by ID
     */
    PaymentResponseDto getPaymentById(
            Integer paymentId
    );

    /*
     * Get all payments of user
     */
    List<PaymentResponseDto> getPaymentsByUserId(
            Integer userId
    );
}