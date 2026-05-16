package com.digitalgoldwallet.digital_gold_wallet.exception;

/*
 * ============================================================
 * Virtual Gold Holding Not Found
 * ============================================================
 */

public class VirtualGoldHoldingNotFoundException
        extends RuntimeException{

    public VirtualGoldHoldingNotFoundException(
            String message
    ){
        super(message);
    }

}