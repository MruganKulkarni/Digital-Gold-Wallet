package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * Custom Exception for Address Not Found
 * ============================================================
 */

public class AddressNotFoundException
        extends RuntimeException {

    public AddressNotFoundException(String message) {

        super(message);
    }
}