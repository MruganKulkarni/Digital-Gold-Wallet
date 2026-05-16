package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * Address Service Layer Testing
 * ============================================================
 */

@SpringBootTest
public class AddressServiceTest {

    /*
     * Service Injection
     */
    @Autowired
    private AddressService addressService;

    /*
     * Repository Injection
     */
    @Autowired
    private AddressRepository addressRepository;

    /*
     * Test Address ID
     */
    private Integer testAddressId;

    /*
     * ============================================================
     * Cleanup Test Data
     * ============================================================
     */
    @AfterEach
    public void tearDown() {

        if (testAddressId != null
                && addressRepository.existsById(
                testAddressId)) {

            addressRepository.deleteById(
                    testAddressId
            );
        }
    }

    /*
     * ============================================================
     * TEST CREATE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address Service")
    public void testCreateAddressService() {

        /*
         * Create Request DTO
         */
        AddressRequestDto requestDto =
                new AddressRequestDto();

        requestDto.setStreet("Anna Nagar");

        requestDto.setCity("Chennai");

        requestDto.setState("Tamil Nadu");

        requestDto.setPostalCode(
                "600040"
                        + System.currentTimeMillis()
        );

        requestDto.setCountry("India");

        /*
         * Call service
         */
        AddressResponseDto responseDto =
                addressService.createAddress(
                        requestDto
                );

        /*
         * Store ID for cleanup
         */
        testAddressId =
                responseDto.getAddressId();

        /*
         * Assertions
         */
        assertNotNull(
                responseDto.getAddressId()
        );

        assertEquals(
                "Chennai",
                responseDto.getCity()
        );

        System.out.println(
                "CREATE ADDRESS SERVICE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST GET ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Address Service")
    public void testGetAddressService() {

        /*
         * Create Address
         */
        AddressRequestDto requestDto =
                new AddressRequestDto();

        requestDto.setStreet("T Nagar");

        requestDto.setCity("Chennai");

        requestDto.setState("Tamil Nadu");

        requestDto.setPostalCode(
                "600017"
                        + System.currentTimeMillis()
        );

        requestDto.setCountry("India");

        AddressResponseDto createdAddress =
                addressService.createAddress(
                        requestDto
                );

        testAddressId =
                createdAddress.getAddressId();

        /*
         * Fetch Address
         */
        AddressResponseDto fetchedAddress =
                addressService.getAddressById(
                        testAddressId
                );

        /*
         * Assertions
         */
        assertEquals(
                testAddressId,
                fetchedAddress.getAddressId()
        );

        System.out.println(
                "GET ADDRESS SERVICE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST UPDATE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Update Address Service")
    public void testUpdateAddressService() {

        /*
         * Create Address
         */
        AddressRequestDto requestDto =
                new AddressRequestDto();

        requestDto.setStreet("Old Street");

        requestDto.setCity("Old City");

        requestDto.setState("Old State");

        requestDto.setPostalCode(
                "111111"
                        + System.currentTimeMillis()
        );

        requestDto.setCountry("India");

        AddressResponseDto createdAddress =
                addressService.createAddress(
                        requestDto
                );

        testAddressId =
                createdAddress.getAddressId();

        /*
         * Update DTO
         */
        AddressRequestDto updateDto =
                new AddressRequestDto();

        updateDto.setStreet("New Street");

        updateDto.setCity("Bangalore");

        updateDto.setState("Karnataka");

        updateDto.setPostalCode(
                "560001"
                        + System.currentTimeMillis()
        );

        updateDto.setCountry("India");

        /*
         * Call update
         */
        AddressResponseDto updatedAddress =
                addressService.updateAddress(
                        testAddressId,
                        updateDto
                );

        /*
         * Assertions
         */
        assertEquals(
                "Bangalore",
                updatedAddress.getCity()
        );

        System.out.println(
                "UPDATE ADDRESS SERVICE TEST PASSED"
        );
    }
}