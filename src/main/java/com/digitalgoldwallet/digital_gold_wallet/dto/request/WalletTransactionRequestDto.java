package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/*
 * Wallet credit/debit request DTO
 */
@Schema(description = "Request body for wallet transactions (credit/debit)")
public class WalletTransactionRequestDto {

    @Schema(description = "Amount to transact", example = "100.50", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.1", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Schema(description = "Payment method used", example = "CREDIT_CARD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    public WalletTransactionRequestDto() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}