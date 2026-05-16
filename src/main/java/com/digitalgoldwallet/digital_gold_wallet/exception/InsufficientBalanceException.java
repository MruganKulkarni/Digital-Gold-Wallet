package com.digitalgoldwallet.digital_gold_wallet.exception;

public class InsufficientBalanceException
        extends RuntimeException {

    public InsufficientBalanceException(
            String message
    ) {

        super(message);
    }
}