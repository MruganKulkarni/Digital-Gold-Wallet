package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDTO;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDTO;
import com.digitalgoldwallet.digital_gold_wallet.entity.Payment;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

public class PaymentMapper {

    /*
     * Convert Request DTO -> Entity
     */
    public static Payment mapToEntity(
            PaymentRequestDTO requestDto,
            User user
    ) {

        Payment payment = new Payment();

        payment.setUser(user);

        payment.setAmount(
                requestDto.getAmount()
        );

        payment.setPaymentMethod(
                requestDto.getPaymentMethod()
        );

        payment.setTransactionType(
                requestDto.getTransactionType()
        );

        payment.setPaymentStatus(
                requestDto.getPaymentStatus()
        );

        return payment;
    }

    /*
     * Convert Entity -> Response DTO
     */
    public static PaymentResponseDTO mapToResponseDto(
            Payment payment
    ) {

        PaymentResponseDTO responseDto =
                new PaymentResponseDTO();

        responseDto.setPaymentId(
                payment.getPaymentId()
        );

        responseDto.setUserId(
                payment.getUser().getUserId()
        );

        responseDto.setAmount(
                payment.getAmount()
        );

        responseDto.setPaymentMethod(
                payment.getPaymentMethod()
        );

        responseDto.setTransactionType(
                payment.getTransactionType()
        );

        responseDto.setPaymentStatus(
                payment.getPaymentStatus()
        );

        responseDto.setCreatedAt(
                payment.getCreatedAt()
        );

        return responseDto;
    }
}