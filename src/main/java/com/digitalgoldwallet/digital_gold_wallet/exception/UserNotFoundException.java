package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * Custom Exception for User Not Found
 * ============================================================
 */

public class UserNotFoundException
        extends RuntimeException {

    public UserNotFoundException(String message) {

        super(message);
    }
}