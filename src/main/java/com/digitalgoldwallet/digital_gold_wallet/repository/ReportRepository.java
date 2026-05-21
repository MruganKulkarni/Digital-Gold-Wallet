package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/*
 * ReportRepository
 *
 * Repository used for reporting
 * and analytics queries.
 */

public interface ReportRepository extends JpaRepository<TransactionHistory, Integer> {

    /*
     * Fetch vendor performance analytics.
     *
     * Includes:
     * - total transactions
     * - total gold volume
     * - total revenue
     */
    @Query("""
            SELECT
                t.branch.vendor.vendorId,
                t.branch.vendor.vendorName,
                COUNT(t),
                SUM(t.quantity),
                SUM(t.amount)
            FROM TransactionHistory t
            WHERE t.transactionStatus = 'SUCCESS'
            GROUP BY
                t.branch.vendor.vendorId,
                t.branch.vendor.vendorName
            ORDER BY SUM(t.amount) DESC
            """)
    List<Object[]> getVendorPerformanceReport();


    /*
     * ============================================================
     * Fetch branch inventory analytics
     * ============================================================
     */

    /*
     * ============================================================
     * Fetch branch inventory analytics
     * ============================================================
     */

    @Query("""
        SELECT
            vb.branchId,
            v.vendorName,
            vb.quantity
        FROM VendorBranch vb
        JOIN vb.vendor v
        WHERE vb.branchId = :branchId
        """)
    Optional<Object>
    getBranchInventoryStatus(
            Integer branchId
    );


    /*
     * ============================================================
     * Fetch top investors report
     * ============================================================
     */

    @Query("""
        SELECT
            u.userId,
            u.name,
            SUM(th.amount),
            COUNT(th.transactionId)
        FROM TransactionHistory th
        JOIN th.user u
        GROUP BY
            u.userId,
            u.name
        ORDER BY SUM(th.amount) DESC
        """)
    List<Object[]>
    getTopInvestors();
}