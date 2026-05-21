package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;

/*
 * VendorPerformanceResponseDto
 *
 * Used to send vendor performance analytics
 * response to client.
 */

public class VendorPerformanceResponseDto {

    /*
     * Vendor ID
     */
    private Integer vendorId;

    /*
     * Vendor name
     */
    private String vendorName;

    /*
     * Total successful transactions
     */
    private Long totalTransactions;

    /*
     * Total gold volume traded
     */
    private BigDecimal totalVolume;

    /*
     * Total revenue generated
     */
    private BigDecimal totalRevenue;

    public VendorPerformanceResponseDto() {
    }

    public VendorPerformanceResponseDto(
            Integer vendorId,
            String vendorName,
            Long totalTransactions,
            BigDecimal totalVolume,
            BigDecimal totalRevenue
    ) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.totalTransactions = totalTransactions;
        this.totalVolume = totalVolume;
        this.totalRevenue = totalRevenue;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}