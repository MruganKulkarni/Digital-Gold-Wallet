package com.digitalgoldwallet.digital_gold_wallet.service.impl; // package declaration for service implementations

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto; // request DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto; // response DTO for vendor branch
import com.digitalgoldwallet.digital_gold_wallet.entity.Address; // Address entity — needed to link branch to address
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // Vendor entity — needed to link branch to vendor
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // VendorBranch entity
import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorBranchException; // thrown for duplicate branch
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException; // thrown when branch not found
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException; // thrown when vendor not found
import com.digitalgoldwallet.digital_gold_wallet.mapper.VendorBranchMapper; // mapper class for converting VendorBranch entity to DTO
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository; // address repository — from Varsha's module
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository; // vendor branch repository
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // vendor repository
import com.digitalgoldwallet.digital_gold_wallet.service.VendorBranchService; // service interface

import org.springframework.beans.factory.annotation.Autowired; // used for dependency injection
import org.springframework.stereotype.Service; // marks this class as a Spring service bean
import org.springframework.transaction.annotation.Transactional; // keeps Hibernate session open for all methods

import java.math.BigDecimal; // used for inventory quantity
import java.util.List; // used for list of branches
import java.util.stream.Collectors; // used for stream-based mapping

/*
 * Service implementation for VendorBranch module
 * Contains all business logic for vendor branch operations
 * Implements VendorBranchService interface
 * Uses VendorBranchMapper for all entity -> DTO conversions
 */
@Service // registers this class as a Spring service bean
@Transactional // keeps Hibernate session open for all methods — fixes LazyInitializationException on lazy-loaded Vendor and Address
public class VendorBranchServiceImpl implements VendorBranchService { // implements VendorBranchService interface

    private final VendorBranchRepository vendorBranchRepository; // repository for branch database operations

    private final VendorRepository vendorRepository; // repository for vendor lookup

    private final AddressRepository addressRepository; // repository for address lookup — from Varsha's module

    @Autowired // injects all repositories automatically by Spring
    public VendorBranchServiceImpl(VendorBranchRepository vendorBranchRepository, // branch repository
                                   VendorRepository vendorRepository, // vendor repository
                                   AddressRepository addressRepository) { // address repository
        this.vendorBranchRepository = vendorBranchRepository; // assigns branch repository
        this.vendorRepository = vendorRepository; // assigns vendor repository
        this.addressRepository = addressRepository; // assigns address repository
    }

    /*
     * Adds a new branch to a vendor
     * Validates vendor exists, address exists, and no duplicate branch at same location
     */
    @Override // overrides addBranch from VendorBranchService interface
    public VendorBranchResponseDto addBranch(Integer vendorId, VendorBranchRequestDto dto) { // accepts vendor ID and branch request data

        Vendor vendor = vendorRepository.findById(vendorId) // finds vendor by ID
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with ID: " + vendorId)); // throws exception if vendor not found

        Address address = addressRepository.findById(dto.getAddressId()) // finds address by ID from DTO
                .orElseThrow(() -> new VendorBranchNotFoundException("Address not found with ID: " + dto.getAddressId())); // throws exception if address not found

        if (vendorBranchRepository.existsByVendorVendorIdAndAddressAddressId(vendorId, dto.getAddressId())) { // checks if branch already exists at this vendor + address
            throw new DuplicateVendorBranchException("Branch already exists for vendor ID " + vendorId + " at address ID " + dto.getAddressId()); // throws duplicate exception
        }

        VendorBranch branch = new VendorBranch(); // creates new VendorBranch entity object
        branch.setVendor(vendor); // links branch to vendor
        branch.setAddress(address); // links branch to address
        branch.setQuantity(dto.getQuantity()); // sets gold quantity from DTO

        VendorBranch savedBranch = vendorBranchRepository.save(branch); // saves branch to database

        return VendorBranchMapper.toResponseDto(savedBranch); // converts saved entity to response DTO using mapper and returns
    }

    /*
     * Fetches a branch by ID
     * Throws VendorBranchNotFoundException if not found
     */
    @Override // overrides getBranchById from VendorBranchService interface
    public VendorBranchResponseDto getBranchById(Integer branchId) { // accepts branch ID

        VendorBranch branch = vendorBranchRepository.findById(branchId) // finds branch by ID
                .orElseThrow(() -> new VendorBranchNotFoundException("Branch not found with ID: " + branchId)); // throws exception if not found

        return VendorBranchMapper.toResponseDto(branch); // converts entity to response DTO using mapper and returns
    }

    /*
     * Fetches all branches belonging to a vendor
     * Throws VendorNotFoundException if vendor does not exist
     */
    @Override // overrides getBranchesByVendorId from VendorBranchService interface
    public List<VendorBranchResponseDto> getBranchesByVendorId(Integer vendorId) { // accepts vendor ID

        if (!vendorRepository.existsById(vendorId)) { // checks if vendor exists
            throw new VendorNotFoundException("Vendor not found with ID: " + vendorId); // throws exception if vendor not found
        }

        List<VendorBranch> branches = vendorBranchRepository.findByVendorVendorId(vendorId); // fetches all branches of this vendor

        return branches.stream() // converts list to stream for mapping
                .map(VendorBranchMapper::toResponseDto) // maps each VendorBranch entity to VendorBranchResponseDto using mapper
                .collect(Collectors.toList()); // collects mapped DTOs into a list
    }

    /*
     * Fetches gold inventory quantity at a branch
     * Throws VendorBranchNotFoundException if branch does not exist
     */
    @Override // overrides getInventoryByBranchId from VendorBranchService interface
    public BigDecimal getInventoryByBranchId(Integer branchId) { // accepts branch ID

        if (!vendorBranchRepository.existsById(branchId)) { // checks if branch exists
            throw new VendorBranchNotFoundException("Branch not found with ID: " + branchId); // throws exception if not found
        }

        return vendorBranchRepository.findInventoryByBranchId(branchId); // returns gold inventory using custom native query
    }
}