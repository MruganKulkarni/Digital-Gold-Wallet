package com.digitalgoldwallet.digital_gold_wallet.exception;

public class PaymentNotFoundException
        extends RuntimeException {

    public PaymentNotFoundException(
            String message
    ) {

        super(message);
    }
}