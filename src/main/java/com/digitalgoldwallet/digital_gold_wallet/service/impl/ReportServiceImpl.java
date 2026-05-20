package com.digitalgoldwallet.digital_gold_wallet.service.impl;
import java.util.List;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.TopInvestorResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorPerformanceResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.exception.ReportDataNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.mapper.ReportMapper;
import com.digitalgoldwallet.digital_gold_wallet.repository.ReportRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.ReportService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.TopInvestorResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.exception.ReportException;
import java.util.Optional;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.BranchInventoryStatusResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.exception.ReportException;
import java.util.List;

/*
 * ReportServiceImpl
 *
 * Implements reporting business logic.
 */

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /*
     * Fetch vendor performance report.
     */
    @Override
    public List<VendorPerformanceResponseDto> getVendorPerformanceReport() {

        List<Object[]> reportData =
                reportRepository.getVendorPerformanceReport();

        if (reportData.isEmpty()) {
            throw new ReportDataNotFoundException(
                    "No vendor performance data found"
            );
        }

        return reportData.stream()
                .map(ReportMapper::mapToVendorPerformanceDto)
                .toList();
    }

    /*
     * ============================================================
     * Fetch branch inventory report
     * ============================================================
     */

    @Override
    public BranchInventoryStatusResponseDto
    getBranchInventoryStatus(
            Integer branchId
    ) {

        /*
         * Validate branch ID
         */
        if (branchId == null || branchId <= 0) {

            throw new ReportException(
                    "Branch ID must be greater than zero"
            );

        }

        /*
         * Fetch report data
         */
        Object[] reportData =
                (Object[]) reportRepository
                        .getBranchInventoryStatus(
                                branchId
                        )
                        .orElseThrow(() ->
                                new ReportException(
                                        "Branch with ID "
                                                + branchId
                                                + " does not exist"
                                )
                        );

        /*
         * Map and return response
         */
        return ReportMapper
                .mapToBranchInventoryDto(
                        reportData
                );

    }

    /*
     * ============================================================
     * Fetch top investors report
     * ============================================================
     */

    @Override
    public List<TopInvestorResponseDto>
    getTopInvestors() {

        /*
         * Fetch report data
         */
        List<Object[]> reportData =
                reportRepository
                        .getTopInvestors();

        /*
         * Validate report data
         */
        if (reportData.isEmpty()) {

            throw new ReportException(
                    "No investor data available"
            );

        }

        /*
         * Map and return response
         */
        return reportData
                .stream()
                .map(
                        ReportMapper
                                ::mapToTopInvestorDto
                )
                .toList();

    }

}