package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * Email Already Exists Exception
 * ============================================================
 */

public class EmailAlreadyExistsException
        extends RuntimeException {

    public EmailAlreadyExistsException(
            String message
    ) {

        super(message);

    }

}