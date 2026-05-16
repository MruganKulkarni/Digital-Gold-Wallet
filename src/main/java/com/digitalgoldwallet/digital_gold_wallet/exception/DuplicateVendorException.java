package com.digitalgoldwallet.digital_gold_wallet.exception; // package declaration for custom exceptions

/*
 * Custom exception thrown when a Vendor with the same name or email already exists
 * Extends RuntimeException so it does not need to be declared in method signatures
 */
public class DuplicateVendorException extends RuntimeException { // extends RuntimeException — unchecked exception

    public DuplicateVendorException(String message) { // constructor accepts a custom error message
        super(message); // passes message to RuntimeException parent
    }
}