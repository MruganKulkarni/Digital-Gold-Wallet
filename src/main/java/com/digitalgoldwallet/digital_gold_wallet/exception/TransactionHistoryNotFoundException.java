package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * Transaction History Not Found Exception
 * ============================================================
 */

public class TransactionHistoryNotFoundException
        extends RuntimeException{

    public TransactionHistoryNotFoundException(
            String message
    ){

        super(message);

    }

}