package com.digitalgoldwallet.digital_gold_wallet.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/*
 * ============================================================
 * GLOBAL EXCEPTION HANDLER
 * ============================================================
 *
 * Handles all custom exceptions globally
 * Returns structured JSON responses
 *
 * ============================================================
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * ============================================================
     * COMMON METHOD
     * Avoid duplicate code
     * ============================================================
     */

    private ResponseEntity<Map<String,Object>>
    buildError(

            HttpStatus status,

            String errorType,

            String message
    ){

        Map<String,Object> error =
                new HashMap<>();

        error.put(
                "timestamp",
                LocalDateTime.now()
        );

        error.put(
                "status",
                status.value()
        );

        error.put(
                "error",
                errorType
        );

        error.put(
                "message",
                message
        );

        return new ResponseEntity<>(
                error,
                status
        );

    }


    /*
     * ============================================================
     * Vendor Not Found
     * ============================================================
     */

    @ExceptionHandler(
            VendorNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleVendorNotFound(
            VendorNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Vendor Not Found",
                ex.getMessage()
        );

    }


    /*
     * ============================================================
     * Vendor Branch Not Found
     * ============================================================
     */

    @ExceptionHandler(
            VendorBranchNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleBranchNotFound(
            VendorBranchNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Vendor Branch Not Found",
                ex.getMessage()
        );

    }


    /*
     * ============================================================
     * Address Not Found
     * ============================================================
     */

    @ExceptionHandler(
            AddressNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleAddressNotFound(
            AddressNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Address Not Found",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * User Not Found
     * ============================================================
     */

    @ExceptionHandler(
            UserNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleUserNotFound(
            UserNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "User Not Found",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Payment Not Found
     * ============================================================
     */

    @ExceptionHandler(
            PaymentNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handlePaymentNotFound(
            PaymentNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Payment Not Found",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Transaction History Not Found
     * ============================================================
     */

    @ExceptionHandler(
            TransactionHistoryNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleTransactionNotFound(
            TransactionHistoryNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Transaction History Not Found",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Physical Transaction Not Found
     * ============================================================
     */

    @ExceptionHandler(
            PhysicalGoldTransactionNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handlePhysicalNotFound(
            PhysicalGoldTransactionNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Physical Transaction Not Found",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Virtual Holding Not Found
     * ============================================================
     */

    @ExceptionHandler(
            VirtualGoldHoldingNotFoundException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleHoldingNotFound(
            VirtualGoldHoldingNotFoundException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Virtual Holding Not Found",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Duplicate Vendor
     * ============================================================
     */

    @ExceptionHandler(
            DuplicateVendorException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleDuplicateVendor(
            DuplicateVendorException ex
    ){

        return buildError(
                HttpStatus.CONFLICT,
                "Duplicate Vendor",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Duplicate Branch
     * ============================================================
     */

    @ExceptionHandler(
            DuplicateVendorBranchException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleDuplicateBranch(
            DuplicateVendorBranchException ex
    ){

        return buildError(
                HttpStatus.CONFLICT,
                "Duplicate Vendor Branch",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Wallet Error
     * ============================================================
     */

    @ExceptionHandler(
            WalletException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleWallet(
            WalletException ex
    ){

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Wallet Error",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * Insufficient Balance
     * ============================================================
     */

    @ExceptionHandler(
            InsufficientBalanceException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleBalance(
            InsufficientBalanceException ex
    ){

        return buildError(
                HttpStatus.BAD_REQUEST,
                "Insufficient Balance",
                ex.getMessage()
        );

    }



    /*
     * ============================================================
     * DTO Validation
     * @Valid + RequestBody
     * ============================================================
     */

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleValidation(
            MethodArgumentNotValidException ex
    ){

        Map<String,Object> error =
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


        Map<String,String>
                fieldErrors =
                new HashMap<>();


        for(
                FieldError fieldError :

                ex.getBindingResult()
                        .getFieldErrors()
        ){

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
     * Path Variable Validation
     *
     * Example:
     * /users/-1
     * @Min
     *
     * ============================================================
     */

    @ExceptionHandler(
            ConstraintViolationException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleConstraintViolation(
            ConstraintViolationException ex
    ){

        Map<String,Object> error=
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


        Map<String,String>
                fieldErrors=
                new HashMap<>();


        for(
                ConstraintViolation<?> violation:

                ex.getConstraintViolations()
        ){

            fieldErrors.put(
                    violation.getPropertyPath()
                            .toString(),

                    violation.getMessage()
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
     * Safety fallback
     * catches raw Optional.orElseThrow()
     * ============================================================
     */

    @ExceptionHandler(
            NoSuchElementException.class
    )
    public ResponseEntity<Map<String,Object>>
    handleNoSuchElement(
            NoSuchElementException ex
    ){

        return buildError(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                "Requested resource does not exist"
        );

    }



    /*
     * ============================================================
     * Generic fallback
     * ============================================================
     */

    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<Map<String,Object>>
    handleGeneric(
            Exception ex
    ){

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage()
        );

    }

}