package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.PhysicalGoldTransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * ============================================================
 * Physical Gold Controller
 * ============================================================
 */

@RestController

@RequestMapping("/api/v1")

@Tag(
        name="Physical Gold",
        description="Physical gold APIs"
)

public class PhysicalGoldTransactionController {

    private final
    PhysicalGoldTransactionService service;

    public
    PhysicalGoldTransactionController(

            PhysicalGoldTransactionService service
    ){

        this.service=service;

    }



    /*
     * ============================================================
     * Convert virtual -> physical
     * ============================================================
     */

    @Operation(
            summary="Convert virtual gold"
    )

    @PostMapping(
            "/gold/physical/convert"
    )

    public
    PhysicalGoldTransactionResponseDto
    convert(

            @Valid

            @RequestBody

            PhysicalGoldTransactionRequestDto dto
    ){

        return service
                .convertToPhysical(
                        dto
                );

    }



    /*
     * ============================================================
     * User physical orders
     * ============================================================
     */

    @Operation(
            summary="Get user physical orders"
    )

    @GetMapping(
            "/users/{userId}/gold/physical"
    )

    public
    List<PhysicalGoldTransactionResponseDto>
    getUserOrders(

            @PathVariable
            Integer userId
    ){

        return service.getByUser(
                userId
        );

    }



    /*
     * ============================================================
     * Delivery details
     * ============================================================
     */

    @Operation(
            summary="Get delivery details"
    )

    @GetMapping(
            "/physical-transactions/{transactionId}"
    )

    public
    PhysicalGoldTransactionResponseDto
    getById(

            @PathVariable
            Integer transactionId
    ){

        return service.getById(
                transactionId
        );

    }

}