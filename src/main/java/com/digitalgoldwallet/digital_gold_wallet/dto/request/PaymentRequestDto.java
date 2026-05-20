package com.digitalgoldwallet.digital_gold_wallet.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

/*
 * DTO used for creating/updating payment
 * Contains validation rules
 */

public class PaymentRequestDto {

    /*
     * User ID linked to payment
     */

    @NotNull(
            message="User ID is required"
    )
    private Integer userId;


    /*
     * Payment amount
     */

    @NotNull(
            message="Amount is required"
    )

    @DecimalMin(
            value="1.0",
            message="Amount must be greater than 0"
    )

    private BigDecimal amount;


    /*
     * Payment method
     */

    @NotBlank(
            message="Payment method is required"
    )

    @Pattern(
            regexp=
                    "Credit Card|Bank Transfer|Google Pay|PhonePe",

            message=
                    "Payment method must be: Credit Card, Bank Transfer, Google Pay or PhonePe"
    )

    private String paymentMethod;


    /*
     * Transaction type
     */

    @NotBlank(
            message="Transaction type is required"
    )

    @Pattern(
            regexp=
                    "Credited to wallet|Debited from wallet",

            message=
                    "Transaction type must be: Credited to wallet or Debited from wallet"
    )

    private String transactionType;


    /*
     * Payment status
     */

    @NotBlank(
            message="Payment status is required"
    )

    @Pattern(
            regexp=
                    "Success|Failed",

            message=
                    "Payment status must be Success or Failed"
    )

    private String paymentStatus;


    public PaymentRequestDto(){}


    public PaymentRequestDto(

            Integer userId,

            BigDecimal amount,

            String paymentMethod,

            String transactionType,

            String paymentStatus
    ){

        this.userId=userId;
        this.amount=amount;
        this.paymentMethod=paymentMethod;
        this.transactionType=transactionType;
        this.paymentStatus=paymentStatus;

    }


    public Integer getUserId() {

        return userId;
    }

    public void setUserId(
            Integer userId
    ) {

        this.userId=userId;
    }

    public BigDecimal getAmount() {

        return amount;
    }

    public void setAmount(
            BigDecimal amount
    ) {

        this.amount=amount;
    }

    public String getPaymentMethod() {

        return paymentMethod;
    }

    public void setPaymentMethod(
            String paymentMethod
    ) {

        this.paymentMethod=paymentMethod;
    }

    public String getTransactionType() {

        return transactionType;
    }

    public void setTransactionType(
            String transactionType
    ) {

        this.transactionType=transactionType;
    }

    public String getPaymentStatus() {

        return paymentStatus;
    }

    public void setPaymentStatus(
            String paymentStatus
    ) {

        this.paymentStatus=paymentStatus;
    }

}