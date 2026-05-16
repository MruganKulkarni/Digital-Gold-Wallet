package com.digitalgoldwallet.digital_gold_wallet.exception; // package declaration for custom exceptions

/*
 * Custom exception thrown when a VendorBranch already exists at the same vendor + address combination
 * Extends RuntimeException so it does not need to be declared in method signatures
 */
public class DuplicateVendorBranchException extends RuntimeException { // extends RuntimeException — unchecked exception

    public DuplicateVendorBranchException(String message) { // constructor accepts a custom error message
        super(message); // passes message to RuntimeException parent
    }
}