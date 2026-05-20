package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.BranchInventoryStatusResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TopInvestorResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorPerformanceResponseDto;

import java.util.List;

/*
 * ReportService
 *
 * Handles reporting business logic.
 */

public interface ReportService {

    /*
     * Fetch vendor performance report.
     */
    List<VendorPerformanceResponseDto> getVendorPerformanceReport();
    /*
     * ============================================================
     * Fetch branch inventory report
     * ============================================================
     */

    BranchInventoryStatusResponseDto
    getBranchInventoryStatus(
            Integer branchId
    );

    /*
     * ============================================================
     * Fetch top investors report
     * ============================================================
     */

    List<TopInvestorResponseDto>
    getTopInvestors();
}