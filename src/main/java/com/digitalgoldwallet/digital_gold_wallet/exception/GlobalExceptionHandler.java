package com.digitalgoldwallet.digital_gold_wallet.exception; // package declaration for exception handling

import com.digitalgoldwallet.digital_gold_wallet.exception.WalletException;
import org.springframework.http.HttpStatus; // used for HTTP status codes
import org.springframework.http.ResponseEntity; // used to return HTTP response with body and status
import org.springframework.validation.FieldError; // used to extract field-level validation errors
import org.springframework.web.bind.MethodArgumentNotValidException; // thrown when @Valid fails
import org.springframework.web.bind.annotation.ExceptionHandler; // marks method as exception handler
import org.springframework.web.bind.annotation.RestControllerAdvice; // makes this a global exception handler for REST controllers

import java.time.LocalDateTime; // used for error timestamp
import java.util.HashMap; // used to store field-level error messages
import java.util.Map; // used for key-value error map

/*
 * ============================================================
 * GLOBAL EXCEPTION HANDLER
 * ============================================================
 *
 * Handles all custom exceptions globally
 * Returns structured JSON responses
 * Prevents raw stack traces from reaching client
 *
 * Applied globally to all REST controllers
 *
 * ============================================================
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * ============================================================
     * HANDLE VendorNotFoundException
     * ============================================================
     */
    @ExceptionHandler(VendorNotFoundException.class)
    public ResponseEntity<Map<String, Object>>
    handleVendorNotFoundException(
            VendorNotFoundException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Vendor Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    /*
     * ============================================================
     * HANDLE VendorBranchNotFoundException
     * ============================================================
     */
    @ExceptionHandler(
            VendorBranchNotFoundException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleVendorBranchNotFoundException(
            VendorBranchNotFoundException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Vendor Branch Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    /*
     * ============================================================
     * HANDLE DuplicateVendorException
     * ============================================================
     */
    @ExceptionHandler(
            DuplicateVendorException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleDuplicateVendorException(
            DuplicateVendorException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.CONFLICT.value()
        );

        error.put(
                "error",
                "Duplicate Vendor"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.CONFLICT
        );
    }

    /*
     * ============================================================
     * HANDLE DuplicateVendorBranchException
     * ============================================================
     */
    @ExceptionHandler(
            DuplicateVendorBranchException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleDuplicateVendorBranchException(
            DuplicateVendorBranchException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.CONFLICT.value()
        );

        error.put(
                "error",
                "Duplicate Vendor Branch"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.CONFLICT
        );
    }

    /*
     * ============================================================
     * HANDLE AddressNotFoundException
     * ============================================================
     */
    @ExceptionHandler(
            AddressNotFoundException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleAddressNotFoundException(
            AddressNotFoundException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Address Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    /*
     * ============================================================
     * HANDLE UserNotFoundException
     * ============================================================
     */
    @ExceptionHandler(
            UserNotFoundException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleUserNotFoundException(
            UserNotFoundException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "User Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    /*
     * ============================================================
     * HANDLE PaymentNotFoundException
     * ============================================================
     */
    @ExceptionHandler(
            PaymentNotFoundException.class
    )
    public ResponseEntity<Map<String, Object>>
    handlePaymentNotFoundException(
            PaymentNotFoundException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Payment Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }

    /*
     * ============================================================
     * HANDLE InsufficientBalanceException
     * ============================================================
     */
    @ExceptionHandler(
            InsufficientBalanceException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleInsufficientBalanceException(
            InsufficientBalanceException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.BAD_REQUEST.value()
        );

        error.put(
                "error",
                "Insufficient Balance"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    /*
     * ============================================================
     * HANDLE VALIDATION ERRORS
     * ============================================================
     *
     * Handles @Valid validation failures
     *
     * Example:
     * - invalid email
     * - null amount
     * - blank name
     *
     * Returns field-wise validation errors
     *
     * ============================================================
     */
    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.BAD_REQUEST.value()
        );

        error.put(
                "error",
                "Validation Failed"
        );

        /*
         * Store field-wise validation messages
         */
        Map<String, String> fieldErrors =
                new HashMap<>();

        /*
         * Loop through all validation errors
         */
        for (FieldError fieldError :
                ex.getBindingResult()
                        .getFieldErrors()) {

            fieldErrors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        error.put(
                "fieldErrors",
                fieldErrors
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

    /*
     * ============================================================
     * HANDLE GENERIC EXCEPTION
     * ============================================================
     *
     * Fallback handler
     * Catches unhandled exceptions
     *
     * ============================================================
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    handleGenericException(
            Exception ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        error.put(
                "error",
                "Internal Server Error"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    /*
     * ============================================================
     * HANDLE TransactionHistoryNotFoundException
     * ============================================================
     */

    @ExceptionHandler(
            TransactionHistoryNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleTransactionHistoryNotFoundException(
            TransactionHistoryNotFoundException ex
    ){

        Map<String,Object> error=
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Transaction History Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );

    }



    /*
     * ============================================================
     * HANDLE PhysicalGoldTransactionNotFoundException
     * ============================================================
     */

    @ExceptionHandler(
            PhysicalGoldTransactionNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handlePhysicalTransactionException(
            PhysicalGoldTransactionNotFoundException ex
    ){

        Map<String,Object> error=
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Physical Transaction Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );

    }



    /*
     * ============================================================
     * HANDLE VirtualHoldingNotFoundException
     * ============================================================
     */

    @ExceptionHandler(
            VirtualGoldHoldingNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleHoldingException(
            VirtualGoldHoldingNotFoundException ex
    ){

        Map<String,Object> error=
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.NOT_FOUND.value()
        );

        error.put(
                "error",
                "Virtual Holding Not Found"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );

    }

    /*
     * ============================================================
     * HANDLE WalletException
     * ============================================================
     */
    @ExceptionHandler(
            WalletException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleWalletException(
            WalletException ex
    ) {

        Map<String, Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                HttpStatus.BAD_REQUEST.value()
        );

        error.put(
                "error",
                "Wallet Error"
        );

        error.put(
                "message",
                ex.getMessage()
        );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
}