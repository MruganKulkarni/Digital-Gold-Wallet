package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorPerformanceResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.ReportService;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TopInvestorResponseDto;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;

import org.springframework.validation.annotation.Validated;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.BranchInventoryStatusResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TopInvestorResponseDto;
import java.util.List;

/*
 * ReportController
 *
 * Handles reporting APIs.
 */

@RestController
@RequestMapping("/api/v1/reports")
@Validated
@Tag(name = "Reports", description = "Reporting APIs")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /*
     * Get vendor performance report.
     */
    @Operation(summary = "Get vendor performance report")
    @GetMapping("/vendors/performance")
    public ResponseEntity<List<VendorPerformanceResponseDto>>
    getVendorPerformanceReport() {

        return ResponseEntity.ok(
                reportService.getVendorPerformanceReport()
        );
    }


    /*
     * ============================================================
     * Get branch inventory report
     * ============================================================
     */

    @Operation(
            summary =
                    "Get branch inventory status report"
    )
    @GetMapping(
            "/branches/{branchId}/inventory-status"
    )
    public ResponseEntity<
            BranchInventoryStatusResponseDto>
    getBranchInventoryStatus(

            @PathVariable

            @Positive(
                    message =
                            "Branch ID must be positive"
            )
            Integer branchId
    ) {

        return ResponseEntity.ok(

                reportService
                        .getBranchInventoryStatus(
                                branchId
                        )
        );

    }

    /*
     * ============================================================
     * Get top investors report
     * ============================================================
     */

    @Operation(
            summary =
                    "Get top investors report"
    )
    @GetMapping(
            "/top-investors"
    )
    public ResponseEntity<
            List<TopInvestorResponseDto>>
    getTopInvestors() {

        return ResponseEntity.ok(

                reportService
                        .getTopInvestors()
        );

    }
}