package com.digitalgoldwallet.digital_gold_wallet.repository; // package declaration

import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // importing VendorBranch entity
import org.springframework.data.jpa.repository.JpaRepository; // gives basic CRUD for free
import org.springframework.data.jpa.repository.Query; // for custom JPQL and native queries
import org.springframework.data.repository.query.Param; // binds method params to query params
import org.springframework.stereotype.Repository; // marks this as a Spring repository bean

import java.math.BigDecimal; // used for returning gold quantity values
import java.util.List; // used for returning list of branches

@Repository // tells Spring this is a repository bean
public interface VendorBranchRepository extends JpaRepository<VendorBranch, Integer> {
    // JpaRepository<VendorBranch, Integer>: entity is VendorBranch, primary key is Integer

    List<VendorBranch> findByVendorVendorId(Integer vendorId); // finds all branches of a specific vendor

    boolean existsByVendorVendorIdAndAddressAddressId(Integer vendorId, Integer addressId); // checks if branch already exists at same vendor + address combination

    @Query("SELECT vb FROM VendorBranch vb WHERE vb.vendor.vendorId = :vendorId ORDER BY vb.quantity DESC")
        // JPQL: get all branches of a vendor sorted by gold quantity highest first
    List<VendorBranch> findBranchesByVendorIdOrderByQuantity(@Param("vendorId") Integer vendorId);

    @Query(value = "SELECT quantity FROM vendor_branches WHERE branch_id = :branchId", nativeQuery = true)
        // native SQL: directly gets gold inventory quantity for a specific branch
    BigDecimal findInventoryByBranchId(@Param("branchId") Integer branchId); // returns current gold stock

    @Query(value = "SELECT vb.quantity AS available, COALESCE(SUM(vgh.quantity), 0) AS allocated " +
            "FROM vendor_branches vb " +
            "LEFT JOIN virtual_gold_holdings vgh ON vb.branch_id = vgh.branch_id " +
            "WHERE vb.branch_id = :branchId " +
            "GROUP BY vb.branch_id", nativeQuery = true)
        // native SQL: gets available gold vs allocated gold for inventory report
    Object[] getInventoryStatus(@Param("branchId") Integer branchId); // returns [available, allocated] as array
}