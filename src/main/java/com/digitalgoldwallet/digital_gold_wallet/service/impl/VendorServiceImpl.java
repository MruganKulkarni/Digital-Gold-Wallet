package com.digitalgoldwallet.digital_gold_wallet.service.impl; // package declaration for service implementations

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto; // request DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO for vendor
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor; // Vendor entity
import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorException; // thrown for duplicate vendor
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException; // thrown when vendor not found
import com.digitalgoldwallet.digital_gold_wallet.mapper.VendorMapper; // mapper class for converting between Vendor entity and DTOs
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository; // vendor repository
import com.digitalgoldwallet.digital_gold_wallet.service.VendorService; // service interface

import org.springframework.beans.factory.annotation.Autowired; // used for dependency injection
import org.springframework.stereotype.Service; // marks this class as a Spring service bean
import org.springframework.transaction.annotation.Transactional; // keeps Hibernate session open for all methods

import java.math.BigDecimal; // used for gold price
import java.util.List; // used for list of vendors
import java.util.stream.Collectors; // used for stream-based mapping

/*
 * Service implementation for Vendor module
 * Contains all business logic for vendor operations
 * Implements VendorService interface
 * Uses VendorMapper for all entity <-> DTO conversions
 */
@Service // registers this class as a Spring service bean
@Transactional // keeps Hibernate session open for all methods — best practice for service layer
public class VendorServiceImpl implements VendorService { // implements VendorService interface

    private final VendorRepository vendorRepository; // repository for vendor database operations

    @Autowired // injects VendorRepository automatically by Spring
    public VendorServiceImpl(VendorRepository vendorRepository) { // constructor injection — preferred over field injection
        this.vendorRepository = vendorRepository; // assigns injected repository
    }

    /*
     * Creates a new vendor
     * Checks for duplicate vendor name and email before saving
     */
    @Override // overrides createVendor from VendorService interface
    public VendorResponseDto createVendor(VendorRequestDto vendorRequestDto) { // accepts vendor request data

        if (vendorRepository.existsByVendorName(vendorRequestDto.getVendorName())) { // checks if vendor name already exists
            throw new DuplicateVendorException("Vendor with name '" + vendorRequestDto.getVendorName() + "' already exists"); // throws duplicate exception
        }

        if (vendorRequestDto.getContactEmail() != null // checks contact email is not null
                && !vendorRequestDto.getContactEmail().isBlank() // checks contact email is not blank
                && vendorRepository.existsByContactEmail(vendorRequestDto.getContactEmail())) { // checks if email already exists
            throw new DuplicateVendorException("Vendor with email '" + vendorRequestDto.getContactEmail() + "' already exists"); // throws duplicate exception
        }

        Vendor vendor = VendorMapper.toEntity(vendorRequestDto); // converts request DTO to Vendor entity using mapper

        Vendor savedVendor = vendorRepository.save(vendor); // saves vendor to database

        return VendorMapper.toResponseDto(savedVendor); // converts saved entity to response DTO using mapper and returns
    }

    /*
     * Fetches a vendor by ID
     * Throws VendorNotFoundException if not found
     */
    @Override // overrides getVendorById from VendorService interface
    public VendorResponseDto getVendorById(Integer vendorId) { // accepts vendor ID

        Vendor vendor = vendorRepository.findById(vendorId) // finds vendor by ID
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with ID: " + vendorId)); // throws exception if not found

        return VendorMapper.toResponseDto(vendor); // converts entity to response DTO using mapper and returns
    }

    /*
     * Fetches all vendors — no pagination
     * Returns complete list of all vendors
     */
    @Override // overrides getAllVendors from VendorService interface
    public List<VendorResponseDto> getAllVendors() { // no pagination parameters needed

        return vendorRepository.findAll() // fetches all vendors from DB
                .stream() // converts to stream for mapping
                .map(VendorMapper::toResponseDto) // maps each Vendor entity to VendorResponseDto using mapper
                .collect(Collectors.toList()); // collects mapped DTOs into a list
    }

    /*
     * Updates an existing vendor by ID
     * Throws VendorNotFoundException if not found
     * Checks for duplicate name/email conflicts with other vendors
     */
    @Override // overrides updateVendor from VendorService interface
    public VendorResponseDto updateVendor(Integer vendorId, VendorRequestDto vendorRequestDto) { // accepts vendor ID and updated data

        Vendor vendor = vendorRepository.findById(vendorId) // finds existing vendor by ID
                .orElseThrow(() -> new VendorNotFoundException("Vendor not found with ID: " + vendorId)); // throws exception if not found

        if (!vendor.getVendorName().equals(vendorRequestDto.getVendorName()) // checks if name is being changed
                && vendorRepository.existsByVendorName(vendorRequestDto.getVendorName())) { // checks if new name already exists
            throw new DuplicateVendorException("Vendor with name '" + vendorRequestDto.getVendorName() + "' already exists"); // throws duplicate exception
        }

        if (vendorRequestDto.getContactEmail() != null // checks contact email is not null
                && !vendorRequestDto.getContactEmail().isBlank() // checks contact email is not blank
                && !vendorRequestDto.getContactEmail().equals(vendor.getContactEmail()) // checks if email is being changed
                && vendorRepository.existsByContactEmail(vendorRequestDto.getContactEmail())) { // checks if new email already exists
            throw new DuplicateVendorException("Vendor with email '" + vendorRequestDto.getContactEmail() + "' already exists"); // throws duplicate exception
        }

        VendorMapper.updateEntityFromDto(vendorRequestDto, vendor); // updates existing entity fields from DTO using mapper

        Vendor updatedVendor = vendorRepository.save(vendor); // saves updated vendor to database

        return VendorMapper.toResponseDto(updatedVendor); // converts updated entity to response DTO using mapper and returns
    }

    /*
     * Gets current gold price for a vendor by ID
     * Throws VendorNotFoundException if not found
     */
    @Override // overrides getGoldPriceByVendorId from VendorService interface
    public BigDecimal getGoldPriceByVendorId(Integer vendorId) { // accepts vendor ID

        if (!vendorRepository.existsById(vendorId)) { // checks if vendor exists
            throw new VendorNotFoundException("Vendor not found with ID: " + vendorId); // throws exception if not found
        }

        return vendorRepository.findGoldPriceByVendorId(vendorId); // returns gold price using custom native query
    }
}