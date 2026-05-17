package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.TransactionHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * ============================================================
 * Transaction History Controller
 * ============================================================
 */

@RestController

@RequestMapping("/api/v1")

@Tag(
        name="Transaction History",
        description="Transaction APIs"
)

public class TransactionHistoryController {

    private final
    TransactionHistoryService service;

    public
    TransactionHistoryController(

            TransactionHistoryService service
    ){

        this.service=service;

    }



    /*
     * ============================================================
     * User transaction history
     * ============================================================
     */

    @Operation(
            summary="Get user transactions"
    )

    @GetMapping(
            "/users/{userId}/transactions"
    )

    public
    List<TransactionHistoryResponseDto>
    getUserTransactions(

            @PathVariable
            Integer userId
    ){

        return service
                .getTransactionsByUser(
                        userId
                );

    }



    /*
     * ============================================================
     * Branch transaction log
     * ============================================================
     */

    @Operation(
            summary="Get branch transactions"
    )

    @GetMapping(
            "/branches/{branchId}/transactions"
    )

    public
    List<TransactionHistoryResponseDto>
    getBranchTransactions(

            @PathVariable
            Integer branchId
    ){

        return service
                .getTransactionsByBranch(
                        branchId
                );

    }

}