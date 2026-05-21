package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorBranchRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorBranchResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorBranchException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.VendorBranchServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Vendor Branch Service Test
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class VendorBranchServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
     */
    @Mock
    private VendorBranchRepository vendorBranchRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private AddressRepository addressRepository;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private VendorBranchServiceImpl vendorBranchService;

    /*
     * ============================================================
     * COMMON VENDOR
     * ============================================================
     */
    private Vendor createMockVendor() {

        Vendor vendor = new Vendor();

        vendor.setVendorId(1);

        vendor.setVendorName("Test Gold Traders");

        vendor.setTotalGoldQuantity(
                new BigDecimal("1000.00")
        );

        vendor.setCurrentGoldPrice(
                new BigDecimal("6400.00")
        );

        return vendor;
    }

    /*
     * ============================================================
     * COMMON ADDRESS
     * ============================================================
     */
    private Address createMockAddress() {

        Address address = new Address();

        address.setAddressId(1);

        address.setStreet("MG Road");

        address.setCity("Bangalore");

        address.setState("Karnataka");

        address.setPostalCode("560001");

        address.setCountry("India");

        return address;
    }

    /*
     * ============================================================
     * COMMON BRANCH
     * ============================================================
     */
    private VendorBranch createMockBranch(
            Vendor vendor,
            Address address
    ) {

        VendorBranch branch =
                new VendorBranch();

        branch.setBranchId(1);

        branch.setVendor(vendor);

        branch.setAddress(address);

        branch.setQuantity(
                new BigDecimal("500.00")
        );

        branch.setCreatedAt(
                LocalDateTime.now()
        );

        return branch;
    }

    /*
     * ============================================================
     * COMMON REQUEST DTO
     * ============================================================
     */
    private VendorBranchRequestDto buildRequestDto() {

        VendorBranchRequestDto dto =
                new VendorBranchRequestDto();

        dto.setVendorId(1);

        dto.setAddressId(1);

        dto.setQuantity(
                new BigDecimal("500.00")
        );

        return dto;
    }

    /*
     * ============================================================
     * TEST ADD BRANCH SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Add Branch Success")
    public void testAddBranch_Success() {

        Vendor vendor =
                createMockVendor();

        Address address =
                createMockAddress();

        VendorBranch branch =
                createMockBranch(
                        vendor,
                        address
                );

        VendorBranchRequestDto dto =
                buildRequestDto();

        when(vendorRepository.findById(1))
                .thenReturn(Optional.of(vendor));

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(vendorBranchRepository
                .existsByVendorVendorIdAndAddressAddressId(1,1))
                .thenReturn(false);

        when(vendorBranchRepository.save(any(VendorBranch.class)))
                .thenReturn(branch);

        VendorBranchResponseDto response =
                vendorBranchService.addBranch(1,dto);

        assertNotNull(response);

        assertEquals(
                1,
                response.getBranchId()
        );

        verify(vendorBranchRepository, times(1))
                .save(any(VendorBranch.class));
    }

    /*
     * ============================================================
     * TEST VENDOR NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Vendor Not Found")
    public void testAddBranch_VendorNotFound() {

        VendorBranchRequestDto dto =
                buildRequestDto();

        when(vendorRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorNotFoundException.class,

                () ->
                        vendorBranchService
                                .addBranch(999,dto)
        );
    }

    /*
     * ============================================================
     * TEST DUPLICATE BRANCH
     * ============================================================
     */
    @Test
    @DisplayName("Test Duplicate Branch")
    public void testAddBranch_DuplicateBranch() {

        Vendor vendor =
                createMockVendor();

        Address address =
                createMockAddress();

        VendorBranchRequestDto dto =
                buildRequestDto();

        when(vendorRepository.findById(1))
                .thenReturn(Optional.of(vendor));

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(vendorBranchRepository
                .existsByVendorVendorIdAndAddressAddressId(1,1))
                .thenReturn(true);

        assertThrows(
                DuplicateVendorBranchException.class,

                () ->
                        vendorBranchService
                                .addBranch(1,dto)
        );
    }

    /*
     * ============================================================
     * TEST GET BRANCH SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Branch Success")
    public void testGetBranchById_Success() {

        Vendor vendor =
                createMockVendor();

        Address address =
                createMockAddress();

        VendorBranch branch =
                createMockBranch(
                        vendor,
                        address
                );

        when(vendorBranchRepository.findById(1))
                .thenReturn(Optional.of(branch));

        VendorBranchResponseDto response =
                vendorBranchService.getBranchById(1);

        assertNotNull(response);

        assertEquals(
                1,
                response.getBranchId()
        );
    }

    /*
     * ============================================================
     * TEST GET BRANCH NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Branch Not Found")
    public void testGetBranchById_NotFound() {

        when(vendorBranchRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorBranchNotFoundException.class,

                () ->
                        vendorBranchService
                                .getBranchById(999)
        );
    }

    /*
     * ============================================================
     * TEST GET BRANCHES BY VENDOR SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Branches By Vendor Success")
    public void testGetBranchesByVendorId_Success() {

        Vendor vendor =
                createMockVendor();

        Address address =
                createMockAddress();

        VendorBranch branch =
                createMockBranch(
                        vendor,
                        address
                );

        when(vendorRepository.existsById(1))
                .thenReturn(true);

        when(vendorBranchRepository.findByVendorVendorId(1))
                .thenReturn(List.of(branch));

        List<VendorBranchResponseDto> response =
                vendorBranchService
                        .getBranchesByVendorId(1);

        assertFalse(
                response.isEmpty()
        );

        assertEquals(
                1,
                response.get(0).getBranchId()
        );
    }

    /*
     * ============================================================
     * TEST GET BRANCHES BY VENDOR NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Branches By Vendor Not Found")
    public void testGetBranchesByVendorId_NotFound() {

        when(vendorRepository.existsById(999))
                .thenReturn(false);

        assertThrows(
                VendorNotFoundException.class,

                () ->
                        vendorBranchService
                                .getBranchesByVendorId(999)
        );
    }

    /*
     * ============================================================
     * TEST GET INVENTORY SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Inventory Success")
    public void testGetInventoryByBranchId_Success() {

        when(vendorBranchRepository.existsById(1))
                .thenReturn(true);

        when(vendorBranchRepository.findInventoryByBranchId(1))
                .thenReturn(
                        new BigDecimal("500.00")
                );

        BigDecimal inventory =
                vendorBranchService
                        .getInventoryByBranchId(1);

        assertEquals(
                new BigDecimal("500.00"),
                inventory
        );
    }

    /*
     * ============================================================
     * TEST GET INVENTORY NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Inventory Not Found")
    public void testGetInventoryByBranchId_NotFound() {

        when(vendorBranchRepository.existsById(999))
                .thenReturn(false);

        assertThrows(
                VendorBranchNotFoundException.class,

                () ->
                        vendorBranchService
                                .getInventoryByBranchId(999)
        );
    }

    /*
     * ============================================================
     * TEST NULL VENDOR ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Vendor Id")
    public void testNullVendorId() {

        VendorBranchRequestDto dto =
                buildRequestDto();

        dto.setVendorId(null);

        assertNull(
                dto.getVendorId()
        );
    }

    /*
     * ============================================================
     * TEST NULL ADDRESS ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Address Id")
    public void testNullAddressId() {

        VendorBranchRequestDto dto =
                buildRequestDto();

        dto.setAddressId(null);

        assertNull(
                dto.getAddressId()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE QUANTITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Quantity")
    public void testNegativeQuantity() {

        VendorBranchRequestDto dto =
                buildRequestDto();

        dto.setQuantity(
                new BigDecimal("-100")
        );

        assertEquals(
                new BigDecimal("-100"),
                dto.getQuantity()
        );
    }

    /*
     * ============================================================
     * TEST ZERO QUANTITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Zero Quantity")
    public void testZeroQuantity() {

        VendorBranchRequestDto dto =
                buildRequestDto();

        dto.setQuantity(
                BigDecimal.ZERO
        );

        assertEquals(
                BigDecimal.ZERO,
                dto.getQuantity()
        );
    }

    /*
     * ============================================================
     * TEST SAVE METHOD CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Save Method Called")
    public void testSaveMethodCalled() {

        Vendor vendor =
                createMockVendor();

        Address address =
                createMockAddress();

        VendorBranch branch =
                createMockBranch(
                        vendor,
                        address
                );

        VendorBranchRequestDto dto =
                buildRequestDto();

        when(vendorRepository.findById(1))
                .thenReturn(Optional.of(vendor));

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(vendorBranchRepository
                .existsByVendorVendorIdAndAddressAddressId(1,1))
                .thenReturn(false);

        when(vendorBranchRepository.save(any(VendorBranch.class)))
                .thenReturn(branch);

        vendorBranchService.addBranch(1,dto);

        verify(vendorBranchRepository, times(1))
                .save(any(VendorBranch.class));
    }

    /*
     * ============================================================
     * TEST FIND BRANCH BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find Branch By Id Called")
    public void testFindBranchByIdCalled() {

        Vendor vendor =
                createMockVendor();

        Address address =
                createMockAddress();

        VendorBranch branch =
                createMockBranch(
                        vendor,
                        address
                );

        when(vendorBranchRepository.findById(1))
                .thenReturn(Optional.of(branch));

        vendorBranchService.getBranchById(1);

        verify(vendorBranchRepository, times(1))
                .findById(1);
    }

}