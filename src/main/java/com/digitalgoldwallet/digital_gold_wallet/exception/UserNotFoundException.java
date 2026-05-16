package com.digitalgoldwallet.digital_gold_wallet.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {

        super(message);
    }
}