package com.digitalgoldwallet.digital_gold_wallet.exception; // package declaration for custom exceptions

/*
 * Custom exception thrown when a Vendor is not found in the database
 * Extends RuntimeException so it does not need to be declared in method signatures
 */
public class VendorNotFoundException extends RuntimeException { // extends RuntimeException — unchecked exception

    public VendorNotFoundException(String message) { // constructor accepts a custom error message
        super(message); // passes message to RuntimeException parent
    }
}