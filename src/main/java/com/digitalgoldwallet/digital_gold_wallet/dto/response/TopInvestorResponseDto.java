package com.digitalgoldwallet.digital_gold_wallet.dto.response;

import java.math.BigDecimal;

/*
 * ============================================================
 * Top Investor Response DTO
 * ============================================================
 */

public class TopInvestorResponseDto {

    /*
     * User ID
     */
    private Integer userId;

    /*
     * User name
     */
    private String name;

    /*
     * Total investment amount
     */
    private BigDecimal totalInvestment;

    /*
     * Total transactions
     */
    private Long totalTransactions;

    public TopInvestorResponseDto() {
    }

    public TopInvestorResponseDto(
            Integer userId,
            String name,
            BigDecimal totalInvestment,
            Long totalTransactions
    ) {

        this.userId = userId;
        this.name = name;
        this.totalInvestment = totalInvestment;
        this.totalTransactions = totalTransactions;

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(
            Integer userId
    ) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(
            String name
    ) {
        this.name = name;
    }

    public BigDecimal getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(
            BigDecimal totalInvestment
    ) {
        this.totalInvestment = totalInvestment;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(
            Long totalTransactions
    ) {
        this.totalTransactions = totalTransactions;
    }
}