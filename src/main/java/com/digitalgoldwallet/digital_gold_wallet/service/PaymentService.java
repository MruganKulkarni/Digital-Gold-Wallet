package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDTO;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

    /*
     * Create payment
     */
    PaymentResponseDTO createPayment(
            PaymentRequestDTO requestDto
    );

    /*
     * Get payment by ID
     */
    PaymentResponseDTO getPaymentById(
            Integer paymentId
    );

    /*
     * Get all payments of user
     */
    List<PaymentResponseDTO> getPaymentsByUserId(
            Integer userId
    );
}