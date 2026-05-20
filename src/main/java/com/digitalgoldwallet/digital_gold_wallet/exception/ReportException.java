package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ReportException
 *
 * Custom exception used for
 * reporting module errors.
 */

public class ReportException extends RuntimeException {

    public ReportException(String message) {
        super(message);
    }
}