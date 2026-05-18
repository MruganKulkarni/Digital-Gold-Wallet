package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import com.digitalgoldwallet.digital_gold_wallet.mapper.AddressMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.AddressServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Mockito-Based Address Service Testing
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    /*
     * Mock Repository
     */
    @Mock
    private AddressRepository addressRepository;

    /*
     * Mock Mapper
     */
    @Mock
    private AddressMapper addressMapper;

    /*
     * Inject mocks into service
     */
    @InjectMocks
    private AddressServiceImpl addressService;

    /*
     * ============================================================
     * TEST CREATE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address")
    public void testCreateAddress() {

        /*
         * Request DTO
         */
        AddressRequestDto requestDto =
                new AddressRequestDto();

        requestDto.setStreet("Anna Nagar");

        requestDto.setCity("Chennai");

        requestDto.setState("Tamil Nadu");

        requestDto.setPostalCode("600040");

        requestDto.setCountry("India");

        /*
         * Address Entity
         */
        Address address = new Address();

        address.setAddressId(1);

        address.setStreet("Anna Nagar");

        address.setCity("Chennai");

        address.setState("Tamil Nadu");

        address.setPostalCode("600040");

        address.setCountry("India");

        /*
         * Response DTO
         */
        AddressResponseDto responseDto =
                new AddressResponseDto(
                        1,
                        "Anna Nagar",
                        "Chennai",
                        "Tamil Nadu",
                        "600040",
                        "India"
                );

        /*
         * Mock Mapper Methods
         */
        when(addressMapper.toEntity(requestDto))
                .thenReturn(address);

        when(addressMapper.toResponseDto(address))
                .thenReturn(responseDto);

        /*
         * Mock Repository Save
         */
        when(addressRepository.save(any(Address.class)))
                .thenReturn(address);

        /*
         * Call Service
         */
        AddressResponseDto savedAddress =
                addressService.createAddress(
                        requestDto
                );

        /*
         * Assertions
         */
        assertNotNull(savedAddress);

        assertEquals(
                "Chennai",
                savedAddress.getCity()
        );

        /*
         * Verify save called once
         */
        verify(addressRepository, times(1))
                .save(any(Address.class));

        System.out.println(
                "CREATE ADDRESS TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST GET ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Address")
    public void testGetAddress() {

        /*
         * Address Entity
         */
        Address address = new Address();

        address.setAddressId(1);

        address.setStreet("Anna Nagar");

        address.setCity("Chennai");

        address.setState("Tamil Nadu");

        address.setPostalCode("600040");

        address.setCountry("India");

        /*
         * Response DTO
         */
        AddressResponseDto responseDto =
                new AddressResponseDto(
                        1,
                        "Anna Nagar",
                        "Chennai",
                        "Tamil Nadu",
                        "600040",
                        "India"
                );

        /*
         * Mock Repository
         */
        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        /*
         * Mock Mapper
         */
        when(addressMapper.toResponseDto(address))
                .thenReturn(responseDto);

        /*
         * Call Service
         */
        AddressResponseDto fetchedAddress =
                addressService.getAddressById(1);

        /*
         * Assertions
         */
        assertEquals(
                1,
                fetchedAddress.getAddressId()
        );

        System.out.println(
                "GET ADDRESS TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST ADDRESS NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Address Not Found")
    public void testAddressNotFound() {

        /*
         * Mock Empty Response
         */
        when(addressRepository.findById(999))
                .thenReturn(Optional.empty());

        /*
         * Verify Exception
         */
        Exception exception = assertThrows(
                RuntimeException.class,

                () -> addressService.getAddressById(999)
        );

        /*
         * Verify Message
         */
        assertTrue(
                exception.getMessage()
                        .contains("Address not found")
        );

        System.out.println(
                "ADDRESS NOT FOUND TEST PASSED"
        );
    }
}