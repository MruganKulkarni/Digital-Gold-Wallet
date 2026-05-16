package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * Physical Transaction Not Found
 * ============================================================
 */

public class PhysicalGoldTransactionNotFoundException
        extends RuntimeException{

    public PhysicalGoldTransactionNotFoundException(
            String message
    ){

        super(message);

    }

}