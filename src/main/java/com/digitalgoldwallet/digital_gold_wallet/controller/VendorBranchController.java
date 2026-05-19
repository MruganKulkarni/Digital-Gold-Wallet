package com.digitalgoldwallet.digital_gold_wallet.controller; // package declaration for controllers

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto; // request DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.service.VendorBranchService; // service interface for vendor branch operations

import io.swagger.v3.oas.annotations.Operation; // swagger annotation for describing an endpoint
import io.swagger.v3.oas.annotations.tags.Tag; // swagger annotation for grouping endpoints

import jakarta.validation.Valid; // triggers @Valid validation on request body
import jakarta.validation.constraints.Min; // validates IDs are positive

import org.springframework.beans.factory.annotation.Autowired; // used for dependency injection
import org.springframework.http.HttpStatus; // used for HTTP status codes
import org.springframework.http.ResponseEntity; // used to return HTTP response with body and status
import org.springframework.validation.annotation.Validated; // enables path variable validation

import org.springframework.web.bind.annotation.GetMapping; // maps GET requests
import org.springframework.web.bind.annotation.PathVariable; // binds URL path variable to method parameter
import org.springframework.web.bind.annotation.PostMapping; // maps POST requests
import org.springframework.web.bind.annotation.RequestBody; // binds request body to method parameter
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; // marks this class as a REST controller

import java.math.BigDecimal; // used for inventory quantity response
import java.util.List; // used for list of branch responses

/*
 * REST Controller for VendorBranch module
 * Handles all HTTP requests for vendor branch operations
 * Has two base URLs: /api/v1/vendors and /api/v1/branches
 */

@RestController // marks this as a REST controller — combines @Controller and @ResponseBody

@Validated // enables @Min validation on path variables

@Tag(
        name = "Vendor Branch Management",
        description = "APIs for managing vendor branches and inventory"
) // swagger tag groups all branch endpoints

public class VendorBranchController {

    private final VendorBranchService vendorBranchService; // service for vendor branch business logic


    @Autowired // injects VendorBranchService automatically by Spring

    public VendorBranchController(
            VendorBranchService vendorBranchService
    ) { // constructor injection

        this.vendorBranchService =
                vendorBranchService; // assigns injected service

    }


    /*
     * POST /api/v1/vendors/{vendorId}/branches
     * Adds a new branch to a vendor
     * Returns 201 CREATED with created branch data
     */

    @PostMapping("/api/v1/vendors/{vendorId}/branches") // maps POST /api/v1/vendors/{vendorId}/branches

    @Operation(
            summary = "Add vendor branch",
            description = "Adds a new branch to an existing vendor"
    ) // swagger description

    public ResponseEntity<VendorBranchResponseDto>
    addBranch(

            @PathVariable

            @Min(
                    value=1,
                    message="Vendor ID must be positive"
            )

            Integer vendorId, // validates vendor ID

            @Valid

            @RequestBody

            VendorBranchRequestDto vendorBranchRequestDto
    ) { // @Valid triggers validation — @RequestBody maps JSON to DTO

        VendorBranchResponseDto response =

                vendorBranchService.addBranch(
                        vendorId,
                        vendorBranchRequestDto
                ); // calls service to add branch

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        ); // returns 201 CREATED with branch response

    }


    /*
     * GET /api/v1/branches/{branchId}
     * Fetches a branch by ID
     * Returns 200 OK with branch data
     */

    @GetMapping("/api/v1/branches/{branchId}") // maps GET /api/v1/branches/{branchId}

    @Operation(
            summary = "Get branch by ID",
            description = "Fetches a vendor branch by its unique ID"
    ) // swagger description

    public ResponseEntity<VendorBranchResponseDto>
    getBranchById(

            @PathVariable

            @Min(
                    value=1,
                    message="Branch ID must be positive"
            )

            Integer branchId
    ) { // validates branch ID

        VendorBranchResponseDto response =

                vendorBranchService.getBranchById(
                        branchId
                ); // calls service

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        ); // returns 200 OK

    }



    /*
     * GET /api/v1/vendors/{vendorId}/branches
     * Fetches all branches of a vendor
     * Returns 200 OK with list of branches
     */

    @GetMapping("/api/v1/vendors/{vendorId}/branches") // maps GET /api/v1/vendors/{vendorId}/branches

    @Operation(
            summary = "Get all branches of a vendor",
            description = "Fetches all branches belonging to a specific vendor"
    ) // swagger description

    public ResponseEntity<List<VendorBranchResponseDto>>
    getBranchesByVendorId(

            @PathVariable

            @Min(
                    value=1,
                    message="Vendor ID must be positive"
            )

            Integer vendorId
    ) { // validates vendor ID

        List<VendorBranchResponseDto> response =

                vendorBranchService.getBranchesByVendorId(
                        vendorId
                ); // calls service

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        ); // returns 200 OK

    }



    /*
     * GET /api/v1/branches/{branchId}/inventory
     * Fetches gold inventory quantity at a branch
     * Returns 200 OK with inventory quantity
     */

    @GetMapping("/api/v1/branches/{branchId}/inventory") // maps GET /api/v1/branches/{branchId}/inventory

    @Operation(
            summary = "Get branch inventory",
            description = "Fetches the current gold inventory quantity at a specific branch"
    ) // swagger description

    public ResponseEntity<BigDecimal>
    getInventoryByBranchId(

            @PathVariable

            @Min(
                    value=1,
                    message="Branch ID must be positive"
            )

            Integer branchId
    ) { // validates branch ID

        BigDecimal inventory =

                vendorBranchService
                        .getInventoryByBranchId(
                                branchId
                        ); // calls service

        return new ResponseEntity<>(
                inventory,
                HttpStatus.OK
        ); // returns 200 OK

    }

}