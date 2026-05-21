package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ReportDataNotFoundException
 *
 * Thrown when report data
 * is not available.
 */

public class ReportDataNotFoundException extends RuntimeException {

    public ReportDataNotFoundException(String message) {
        super(message);
    }
}