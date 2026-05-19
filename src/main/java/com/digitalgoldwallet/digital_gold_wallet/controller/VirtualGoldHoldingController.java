package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VirtualGoldHoldingRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VirtualGoldHoldingResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.VirtualGoldHoldingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * ============================================================
 * Virtual Gold Holding Controller
 * ============================================================
 *
 * Handles:
 *
 * Buy gold
 * Sell gold
 * User holdings
 * Branch holdings
 *
 * ============================================================
 */

@RestController

@Validated

@RequestMapping("/api/v1")

@Tag(
        name="Virtual Gold",
        description="Virtual gold APIs"
)

public class VirtualGoldHoldingController {

    private final VirtualGoldHoldingService service;

    public VirtualGoldHoldingController(
            VirtualGoldHoldingService service
    ){
        this.service=service;
    }


    /*
     * ============================================================
     * Buy virtual gold
     * ============================================================
     */

    @Operation(
            summary="Buy virtual gold"
    )

    @PostMapping(
            "/gold/virtual/buy"
    )

    public VirtualGoldHoldingResponseDto
    buyGold(

            @Valid

            @RequestBody

            VirtualGoldHoldingRequestDto dto
    ){

        return service.buyGold(dto);

    }


    /*
     * ============================================================
     * Sell virtual gold
     * ============================================================
     */

    @Operation(
            summary="Sell virtual gold"
    )

    @PostMapping(
            "/gold/virtual/sell"
    )

    public VirtualGoldHoldingResponseDto
    sellGold(

            @Valid

            @RequestBody

            VirtualGoldHoldingRequestDto dto
    ){

        return service.sellGold(dto);

    }


    /*
     * ============================================================
     * User holdings
     * ============================================================
     */

    @Operation(
            summary="Get user holdings"
    )

    @GetMapping(
            "/users/{userId}/gold/virtual"
    )

    public List<VirtualGoldHoldingResponseDto>
    getUserHoldings(

            @PathVariable

            @Min(
                    value=1,
                    message="User ID must be positive"
            )

            Integer userId
    ){

        return service
                .getHoldingsByUser(
                        userId
                );

    }



    /*
     * ============================================================
     * Branch holdings
     * ============================================================
     */

    @Operation(
            summary="Get branch holdings"
    )

    @GetMapping(
            "/branches/{branchId}/gold/virtual"
    )

    public List<VirtualGoldHoldingResponseDto>
    getBranchHoldings(

            @PathVariable

            @Min(
                    value=1,
                    message="Branch ID must be positive"
            )

            Integer branchId
    ){

        return service
                .getHoldingsByBranch(
                        branchId
                );

    }

}