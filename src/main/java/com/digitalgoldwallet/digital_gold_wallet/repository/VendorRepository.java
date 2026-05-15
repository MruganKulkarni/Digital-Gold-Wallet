package com.digitalgoldwallet.digital_gold_wallet.repository; // package declaration

import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // importing Vendor entity
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository gives basic CRUD methods
import org.springframework.data.jpa.repository.Query; // used for writing custom JPQL or native queries
import org.springframework.data.repository.query.Param; // used to bind method params to query params
import org.springframework.stereotype.Repository; // marks this as a Spring repository component

import java.util.List; // used for returning list of results
import java.util.Optional; // used for returning optional single result

@Repository // tells Spring this is a repository bean
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    // JpaRepository<Vendor, Integer> means: entity is Vendor, primary key type is Integer
    // gives us: save(), findById(), findAll(), deleteById() etc for free

    Optional<Vendor> findByVendorName(String vendorName); // finds vendor by name, returns empty if not found

    boolean existsByVendorName(String vendorName); // checks if vendor with given name already exists

    boolean existsByContactEmail(String contactEmail); // checks if email is already registered

    @Query("SELECT v FROM Vendor v WHERE v.vendorName LIKE %:keyword%") // JPQL: search vendors by keyword in name
    List<Vendor> searchByName(@Param("keyword") String keyword); // binds :keyword param to method argument

    @Query("SELECT v FROM Vendor v ORDER BY v.currentGoldPrice ASC") // JPQL: get all vendors sorted by price low to high
    List<Vendor> findAllOrderByGoldPriceAsc(); // returns vendors in ascending price order

    @Query(value = "SELECT current_gold_price FROM vendors WHERE vendor_id = :vendorId", nativeQuery = true)
        // native SQL: directly queries DB table to get gold price for a specific vendor
    java.math.BigDecimal findGoldPriceByVendorId(@Param("vendorId") Integer vendorId); // returns gold price as BigDecimal
}