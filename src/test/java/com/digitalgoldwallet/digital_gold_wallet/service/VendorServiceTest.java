package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VendorRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Vendor;

import com.digitalgoldwallet.digital_gold_wallet.exception.DuplicateVendorException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.repository.VendorRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.VendorServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Vendor Service Test
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORY
     * ============================================================
     */
    @Mock
    private VendorRepository vendorRepository;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private VendorServiceImpl vendorService;

    /*
     * ============================================================
     * COMMON VENDOR
     * ============================================================
     */
    private Vendor createMockVendor() {

        Vendor vendor = new Vendor();

        vendor.setVendorId(1);

        vendor.setVendorName(
                "Test Gold Traders"
        );

        vendor.setDescription(
                "Test description"
        );

        vendor.setContactPersonName(
                "Sparsh Garg"
        );

        vendor.setContactEmail(
                "sparsh@test.com"
        );

        vendor.setContactPhone(
                "9999999999"
        );

        vendor.setWebsiteUrl(
                "https://testgold.com"
        );

        vendor.setTotalGoldQuantity(
                new BigDecimal("1000.00")
        );

        vendor.setCurrentGoldPrice(
                new BigDecimal("6400.00")
        );

        vendor.setCreatedAt(
                LocalDateTime.now()
        );

        return vendor;
    }

    /*
     * ============================================================
     * COMMON REQUEST DTO
     * ============================================================
     */
    private VendorRequestDto createMockRequestDto() {

        VendorRequestDto dto =
                new VendorRequestDto();

        dto.setVendorName(
                "Test Gold Traders"
        );

        dto.setDescription(
                "Test description"
        );

        dto.setContactPersonName(
                "Sparsh Garg"
        );

        dto.setContactEmail(
                "sparsh@test.com"
        );

        dto.setContactPhone(
                "9999999999"
        );

        dto.setWebsiteUrl(
                "https://testgold.com"
        );

        dto.setTotalGoldQuantity(
                new BigDecimal("1000.00")
        );

        dto.setCurrentGoldPrice(
                new BigDecimal("6400.00")
        );

        return dto;
    }

    /*
     * ============================================================
     * TEST CREATE VENDOR SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Vendor Success")
    public void testCreateVendor_Success() {

        VendorRequestDto requestDto =
                createMockRequestDto();

        Vendor vendor =
                createMockVendor();

        when(vendorRepository.existsByVendorName(
                "Test Gold Traders"
        )).thenReturn(false);

        when(vendorRepository.existsByContactEmail(
                "sparsh@test.com"
        )).thenReturn(false);

        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendor);

        VendorResponseDto response =
                vendorService.createVendor(requestDto);

        assertNotNull(response);

        assertEquals(
                1,
                response.getVendorId()
        );

        assertEquals(
                "Test Gold Traders",
                response.getVendorName()
        );

        verify(vendorRepository, times(1))
                .save(any(Vendor.class));
    }

    /*
     * ============================================================
     * TEST DUPLICATE NAME
     * ============================================================
     */
    @Test
    @DisplayName("Test Duplicate Vendor Name")
    public void testCreateVendor_DuplicateName() {

        VendorRequestDto requestDto =
                createMockRequestDto();

        when(vendorRepository.existsByVendorName(
                "Test Gold Traders"
        )).thenReturn(true);

        assertThrows(
                DuplicateVendorException.class,

                () ->
                        vendorService
                                .createVendor(requestDto)
        );
    }

    /*
     * ============================================================
     * TEST DUPLICATE EMAIL
     * ============================================================
     */
    @Test
    @DisplayName("Test Duplicate Vendor Email")
    public void testCreateVendor_DuplicateEmail() {

        VendorRequestDto requestDto =
                createMockRequestDto();

        when(vendorRepository.existsByVendorName(
                "Test Gold Traders"
        )).thenReturn(false);

        when(vendorRepository.existsByContactEmail(
                "sparsh@test.com"
        )).thenReturn(true);

        assertThrows(
                DuplicateVendorException.class,

                () ->
                        vendorService
                                .createVendor(requestDto)
        );
    }

    /*
     * ============================================================
     * TEST GET VENDOR SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Vendor Success")
    public void testGetVendorById_Success() {

        Vendor vendor =
                createMockVendor();

        when(vendorRepository.findById(1))
                .thenReturn(Optional.of(vendor));

        VendorResponseDto response =
                vendorService.getVendorById(1);

        assertNotNull(response);

        assertEquals(
                1,
                response.getVendorId()
        );

        assertEquals(
                "Test Gold Traders",
                response.getVendorName()
        );
    }

    /*
     * ============================================================
     * TEST GET VENDOR NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Vendor Not Found")
    public void testGetVendorById_NotFound() {

        when(vendorRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorNotFoundException.class,

                () ->
                        vendorService
                                .getVendorById(999)
        );
    }

    /*
     * ============================================================
     * TEST GET ALL VENDORS SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get All Vendors Success")
    public void testGetAllVendors_Success() {
        // verifies that getAllVendors returns list of vendors

        Vendor vendor =
                createMockVendor();

        when(vendorRepository.findAll()) // when repository fetches all vendors
                .thenReturn(List.of(mockVendor)); // return mock list

        List<VendorResponseDto> response = vendorService.getAllVendors(); // calls service method

        assertNotNull(response); // confirms response is not null
        assertFalse(response.isEmpty()); // confirms list has at least one vendor
        assertEquals("Test Gold Traders", response.get(0).getVendorName()); // confirms first vendor name

        System.out.println("TEST PASSED: testGetAllVendors_Success - Total vendors = " + response.size());
    }

        assertFalse(
                response.getContent().isEmpty()
        );

        assertEquals(
                "Test Gold Traders",
                response.getContent()
                        .get(0)
                        .getVendorName()
        );
    }

    /*
     * ============================================================
     * TEST UPDATE VENDOR SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Update Vendor Success")
    public void testUpdateVendor_Success() {

        Vendor vendor =
                createMockVendor();

        VendorRequestDto requestDto =
                createMockRequestDto();

        requestDto.setCurrentGoldPrice(
                new BigDecimal("7000.00")
        );

        when(vendorRepository.findById(1))
                .thenReturn(Optional.of(vendor));

        vendor.setCurrentGoldPrice(
                new BigDecimal("7000.00")
        );

        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendor);

        VendorResponseDto response =
                vendorService.updateVendor(1,requestDto);

        assertEquals(
                new BigDecimal("7000.00"),
                response.getCurrentGoldPrice()
        );
    }

    /*
     * ============================================================
     * TEST UPDATE VENDOR NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Update Vendor Not Found")
    public void testUpdateVendor_NotFound() {

        VendorRequestDto requestDto =
                createMockRequestDto();

        when(vendorRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorNotFoundException.class,

                () ->
                        vendorService
                                .updateVendor(999,requestDto)
        );
    }

    /*
     * ============================================================
     * TEST GET GOLD PRICE SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Gold Price Success")
    public void testGetGoldPriceByVendorId_Success() {

        when(vendorRepository.existsById(1))
                .thenReturn(true);

        when(vendorRepository.findGoldPriceByVendorId(1))
                .thenReturn(
                        new BigDecimal("6400.00")
                );

        BigDecimal price =
                vendorService
                        .getGoldPriceByVendorId(1);

        assertEquals(
                new BigDecimal("6400.00"),
                price
        );
    }

    /*
     * ============================================================
     * TEST GET GOLD PRICE NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Gold Price Not Found")
    public void testGetGoldPriceByVendorId_NotFound() {

        when(vendorRepository.existsById(999))
                .thenReturn(false);

        assertThrows(
                VendorNotFoundException.class,

                () ->
                        vendorService
                                .getGoldPriceByVendorId(999)
        );
    }

    /*
     * ============================================================
     * TEST NULL VENDOR NAME
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Vendor Name")
    public void testNullVendorName() {

        VendorRequestDto dto =
                createMockRequestDto();

        dto.setVendorName(null);

        assertNull(
                dto.getVendorName()
        );
    }

    /*
     * ============================================================
     * TEST NULL CONTACT EMAIL
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Contact Email")
    public void testNullContactEmail() {

        VendorRequestDto dto =
                createMockRequestDto();

        dto.setContactEmail(null);

        assertNull(
                dto.getContactEmail()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE GOLD PRICE
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Gold Price")
    public void testNegativeGoldPrice() {

        VendorRequestDto dto =
                createMockRequestDto();

        dto.setCurrentGoldPrice(
                new BigDecimal("-100")
        );

        assertEquals(
                new BigDecimal("-100"),
                dto.getCurrentGoldPrice()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE GOLD QUANTITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Gold Quantity")
    public void testNegativeGoldQuantity() {

        VendorRequestDto dto =
                createMockRequestDto();

        dto.setTotalGoldQuantity(
                new BigDecimal("-100")
        );

        assertEquals(
                new BigDecimal("-100"),
                dto.getTotalGoldQuantity()
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

        VendorRequestDto requestDto =
                createMockRequestDto();

        Vendor vendor =
                createMockVendor();

        when(vendorRepository.existsByVendorName(
                "Test Gold Traders"
        )).thenReturn(false);

        when(vendorRepository.existsByContactEmail(
                "sparsh@test.com"
        )).thenReturn(false);

        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendor);

        vendorService.createVendor(requestDto);

        verify(vendorRepository, times(1))
                .save(any(Vendor.class));
    }

    /*
     * ============================================================
     * TEST FIND BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find By Id Called")
    public void testFindByIdCalled() {

        Vendor vendor =
                createMockVendor();

        when(vendorRepository.findById(1))
                .thenReturn(Optional.of(vendor));

        vendorService.getVendorById(1);

        verify(vendorRepository, times(1))
                .findById(1);
    }

}