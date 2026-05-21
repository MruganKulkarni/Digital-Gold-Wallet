package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.BranchInventoryStatusResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TopInvestorResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorPerformanceResponseDto;

import java.math.BigDecimal;

/*
 * ============================================================
 * Report Mapper
 * ============================================================
 *
 * Used to map JPQL query results
 * into response DTOs.
 *
 * ============================================================
 */

public class ReportMapper {

    /*
     * ============================================================
     * Map vendor performance report
     * ============================================================
     */

    public static VendorPerformanceResponseDto
    mapToVendorPerformanceDto(
            Object[] row
    ) {

        return new VendorPerformanceResponseDto(

                (Integer) row[0],

                (String) row[1],

                (Long) row[2],

                (BigDecimal) row[3],

                (BigDecimal) row[4]
        );

    }



    /*
     * ============================================================
     * Map branch inventory report
     * ============================================================
     */

    public static BranchInventoryStatusResponseDto
    mapToBranchInventoryDto(
            Object[] row
    ) {

        return new BranchInventoryStatusResponseDto(

                (Integer) row[0],

                (String) row[1],

                (BigDecimal) row[2]
        );

    }


    /*
     * ============================================================
     * Map top investors report
     * ============================================================
     */

    public static TopInvestorResponseDto
    mapToTopInvestorDto(
            Object[] row
    ) {

        return new TopInvestorResponseDto(

                (Integer) row[0],

                (String) row[1],

                (BigDecimal) row[2],

                (Long) row[3]
        );

    }

}