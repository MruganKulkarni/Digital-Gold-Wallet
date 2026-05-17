package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * WALLET EXCEPTION
 * ============================================================
 *
 * Custom exception for wallet operations
 *
 * ============================================================
 */
public class WalletException
        extends RuntimeException {

    /*
     * Constructor
     */
    public WalletException(
            String message
    ) {

        super(message);
    }
}