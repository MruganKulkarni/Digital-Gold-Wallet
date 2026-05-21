package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorPerformanceResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.exception.ReportDataNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.repository.ReportRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.impl.ReportServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
 * ReportServiceImplTest
 *
 * Unit tests for ReportServiceImpl.
 */

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    /*
     * Creates mock JPQL query row.
     */
    private Object[] createMockRow() {

        return new Object[]{
                1,
                "Tanishq",
                10L,
                new BigDecimal("25.50"),
                new BigDecimal("150000.00")
        };
    }

    @Test
    void shouldReturnVendorPerformanceReport() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        List<VendorPerformanceResponseDto> response =
                reportService.getVendorPerformanceReport();

        assertEquals(1, response.size());
    }

    @Test
    void shouldReturnCorrectVendorId() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        Integer vendorId =
                reportService.getVendorPerformanceReport()
                        .get(0)
                        .getVendorId();

        assertEquals(1, vendorId);
    }

    @Test
    void shouldReturnCorrectVendorName() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        String vendorName =
                reportService.getVendorPerformanceReport()
                        .get(0)
                        .getVendorName();

        assertEquals("Tanishq", vendorName);
    }

    @Test
    void shouldReturnCorrectTransactionCount() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        Long count =
                reportService.getVendorPerformanceReport()
                        .get(0)
                        .getTotalTransactions();

        assertEquals(10L, count);
    }

    @Test
    void shouldReturnCorrectTotalVolume() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        BigDecimal volume =
                reportService.getVendorPerformanceReport()
                        .get(0)
                        .getTotalVolume();

        assertEquals(new BigDecimal("25.50"), volume);
    }

    @Test
    void shouldReturnCorrectRevenue() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        BigDecimal revenue =
                reportService.getVendorPerformanceReport()
                        .get(0)
                        .getTotalRevenue();

        assertEquals(new BigDecimal("150000.00"), revenue);
    }

    @Test
    void shouldThrowExceptionWhenNoDataFound() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(Collections.emptyList());

        assertThrows(
                ReportDataNotFoundException.class,
                () -> reportService.getVendorPerformanceReport()
        );
    }

    @Test
    void shouldCallRepositoryOnce() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        reportService.getVendorPerformanceReport();

        verify(reportRepository, times(1))
                .getVendorPerformanceReport();
    }

    @Test
    void shouldNotReturnNullResponse() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        assertNotNull(
                reportService.getVendorPerformanceReport()
        );
    }

    @Test
    void shouldReturnNonEmptyList() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        assertFalse(
                reportService.getVendorPerformanceReport()
                        .isEmpty()
        );
    }

    @Test
    void shouldMapObjectArrayCorrectly() {

        when(reportRepository.getVendorPerformanceReport())
                .thenReturn(List.<Object[]>of(createMockRow()));

        VendorPerformanceResponseDto dto =
                reportService.getVendorPerformanceReport()
                        .get(0);

        assertAll(
                () -> assertEquals(1, dto.getVendorId()),
                () -> assertEquals("Tanishq", dto.getVendorName()),
                () -> assertEquals(10L, dto.getTotalTransactions()),
                () -> assertEquals(
                        new BigDecimal("25.50"),
                        dto.getTotalVolume()
                ),
                () -> assertEquals(
                        new BigDecimal("150000.00"),
                        dto.getTotalRevenue()
                )
        );
    }
}