package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PaymentRequestDTO;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PaymentResponseDTO;
import com.digitalgoldwallet.digital_gold_wallet.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * ============================================================
 * PAYMENT CONTROLLER
 * ============================================================
 *
 * Handles:
 * - wallet payments
 * - wallet credit/debit
 * - payment history
 * - payment lookup
 *
 * ============================================================
 */

@RestController
@RequestMapping("/api/v1")
@Tag(
        name = "Payment Controller",
        description = "APIs for wallet payments and payment history"
)
public class PaymentController {

    /*
     * Payment service dependency
     */
    private final PaymentService paymentService;

    /*
     * Constructor Injection
     */
    public PaymentController(
            PaymentService paymentService
    ) {

        this.paymentService =
                paymentService;
    }

    /*
     * ============================================================
     * INITIATE PAYMENT
     * ============================================================
     *
     * Endpoint:
     * POST /api/v1/payments
     *
     * ============================================================
     */
    @PostMapping("/payments")
    @Operation(
            summary = "Initiate payment"
    )
    public ResponseEntity<PaymentResponseDTO>
    createPayment(
            @Valid
            @RequestBody
            PaymentRequestDTO requestDto
    ) {

        PaymentResponseDTO response =
                paymentService.createPayment(
                        requestDto
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    /*
     * ============================================================
     * GET PAYMENT BY ID
     * ============================================================
     *
     * Endpoint:
     * GET /api/v1/payments/{paymentId}
     *
     * ============================================================
     */
    @GetMapping("/payments/{paymentId}")
    @Operation(
            summary = "Get payment by ID"
    )
    public ResponseEntity<PaymentResponseDTO>
    getPaymentById(
            @PathVariable
            Integer paymentId
    ) {

        PaymentResponseDTO response =
                paymentService.getPaymentById(
                        paymentId
                );

        return ResponseEntity.ok(
                response
        );
    }

    /*
     * ============================================================
     * GET USER PAYMENT HISTORY
     * ============================================================
     *
     * Endpoint:
     * GET /api/v1/users/{userId}/payments
     *
     * ============================================================
     */
    @GetMapping("/users/{userId}/payments")
    @Operation(
            summary = "Get payment history by user ID"
    )
    public ResponseEntity<List<PaymentResponseDTO>>
    getPaymentsByUserId(
            @PathVariable
            Integer userId
    ) {

        List<PaymentResponseDTO> response =
                paymentService.getPaymentsByUserId(
                        userId
                );

        return ResponseEntity.ok(
                response
        );
    }
}