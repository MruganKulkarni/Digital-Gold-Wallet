package com.digitalgoldwallet.digital_gold_wallet.exception; // package declaration for exception handling

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
 * Global Exception Handler
 * Catches all custom exceptions thrown across the application
 * Returns structured JSON error responses instead of stack traces
 */
@RestControllerAdvice // applies to all @RestController classes globally
public class GlobalExceptionHandler {

    /*
     * Handles VendorNotFoundException
     * Returns 404 NOT FOUND with error message
     */
    @ExceptionHandler(VendorNotFoundException.class) // catches VendorNotFoundException only
    public ResponseEntity<Map<String, Object>> handleVendorNotFoundException(
            VendorNotFoundException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.NOT_FOUND.value()); // adds 404 status code
        error.put("error", "Vendor Not Found"); // adds error type
        error.put("message", ex.getMessage()); // adds exception message

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // returns 404 response
    }

    /*
     * Handles VendorBranchNotFoundException
     * Returns 404 NOT FOUND with error message
     */
    @ExceptionHandler(VendorBranchNotFoundException.class) // catches VendorBranchNotFoundException only
    public ResponseEntity<Map<String, Object>> handleVendorBranchNotFoundException(
            VendorBranchNotFoundException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.NOT_FOUND.value()); // adds 404 status code
        error.put("error", "Vendor Branch Not Found"); // adds error type
        error.put("message", ex.getMessage()); // adds exception message

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // returns 404 response
    }

    /*
     * Handles DuplicateVendorException
     * Returns 409 CONFLICT with error message
     */
    @ExceptionHandler(DuplicateVendorException.class) // catches DuplicateVendorException only
    public ResponseEntity<Map<String, Object>> handleDuplicateVendorException(
            DuplicateVendorException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.CONFLICT.value()); // adds 409 status code
        error.put("error", "Duplicate Vendor"); // adds error type
        error.put("message", ex.getMessage()); // adds exception message

        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // returns 409 response
    }

    /*
     * Handles DuplicateVendorBranchException
     * Returns 409 CONFLICT with error message
     */
    @ExceptionHandler(DuplicateVendorBranchException.class) // catches DuplicateVendorBranchException only
    public ResponseEntity<Map<String, Object>> handleDuplicateVendorBranchException(
            DuplicateVendorBranchException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.CONFLICT.value()); // adds 409 status code
        error.put("error", "Duplicate Vendor Branch"); // adds error type
        error.put("message", ex.getMessage()); // adds exception message

        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // returns 409 response
    }

    /*
     * Handles @Valid validation failures
     * Returns 400 BAD REQUEST with field-level error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class) // catches validation failures from @Valid
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.BAD_REQUEST.value()); // adds 400 status code
        error.put("error", "Validation Failed"); // adds error type

        Map<String, String> fieldErrors = new HashMap<>(); // map to hold field-level errors

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) { // loops through all field errors
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage()); // maps field name to error message
        }

        error.put("fieldErrors", fieldErrors); // adds field errors to response

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // returns 400 response
    }

    /*
     * Handles AddressNotFoundException — added by Varsha's module
     * Returns 404 NOT FOUND with error message
     */
    @ExceptionHandler(AddressNotFoundException.class) // catches AddressNotFoundException from Varsha's module
    public ResponseEntity<Map<String, Object>> handleAddressNotFoundException(
            AddressNotFoundException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.NOT_FOUND.value()); // adds 404 status code
        error.put("error", "Address Not Found"); // adds error type
        error.put("message", ex.getMessage()); // adds exception message

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // returns 404 response
    }

    /*
     * Handles UserNotFoundException — added by Varsha's module
     * Returns 404 NOT FOUND with error message
     */
    @ExceptionHandler(UserNotFoundException.class) // catches UserNotFoundException from Varsha's module
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException ex) { // receives the thrown exception

        Map<String, Object> error = new HashMap<>(); // creates error response map
        error.put("timestamp", LocalDateTime.now()); // adds current timestamp
        error.put("status", HttpStatus.NOT_FOUND.value()); // adds 404 status code
        error.put("error", "User Not Found"); // adds error type
        error.put("message", ex.getMessage()); // adds exception message

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // returns 404 response
    }
}