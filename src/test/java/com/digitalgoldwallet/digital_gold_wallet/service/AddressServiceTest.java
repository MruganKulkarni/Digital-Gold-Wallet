package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;

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
     * ============================================================
     * MOCK REPOSITORY
     * ============================================================
     */
    @Mock
    private AddressRepository addressRepository;

    /*
     * ============================================================
     * MOCK MAPPER
     * ============================================================
     */
    @Mock
    private AddressMapper addressMapper;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private AddressServiceImpl addressService;

    /*
     * ============================================================
     * COMMON ADDRESS ENTITY
     * ============================================================
     */
    private Address buildAddress() {

        Address address = new Address();

        address.setAddressId(1);

        address.setStreet("Anna Nagar");

        address.setCity("Chennai");

        address.setState("Tamil Nadu");

        address.setPostalCode("600040");

        address.setCountry("India");

        return address;
    }

    /*
     * ============================================================
     * COMMON REQUEST DTO
     * ============================================================
     */
    private AddressRequestDto buildRequestDto() {

        AddressRequestDto requestDto =
                new AddressRequestDto();

        requestDto.setStreet("Anna Nagar");

        requestDto.setCity("Chennai");

        requestDto.setState("Tamil Nadu");

        requestDto.setPostalCode("600040");

        requestDto.setCountry("India");

        return requestDto;
    }

    /*
     * ============================================================
     * COMMON RESPONSE DTO
     * ============================================================
     */
    private AddressResponseDto buildResponseDto() {

        return new AddressResponseDto(
                1,
                "Anna Nagar",
                "Chennai",
                "Tamil Nadu",
                "600040",
                "India"
        );
    }

    /*
     * ============================================================
     * TEST CREATE ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address")
    public void testCreateAddress() {

        AddressRequestDto requestDto =
                buildRequestDto();

        Address address =
                buildAddress();

        AddressResponseDto responseDto =
                buildResponseDto();

        when(addressMapper.toEntity(requestDto))
                .thenReturn(address);

        when(addressMapper.toResponseDto(address))
                .thenReturn(responseDto);

        when(addressRepository.save(any(Address.class)))
                .thenReturn(address);

        AddressResponseDto savedAddress =
                addressService.createAddress(
                        requestDto
                );

        assertNotNull(savedAddress);

        assertEquals(
                "Chennai",
                savedAddress.getCity()
        );

        verify(addressRepository, times(1))
                .save(any(Address.class));
    }

    /*
     * ============================================================
     * TEST GET ADDRESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Address")
    public void testGetAddress() {

        Address address =
                buildAddress();

        AddressResponseDto responseDto =
                buildResponseDto();

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(addressMapper.toResponseDto(address))
                .thenReturn(responseDto);

        AddressResponseDto fetchedAddress =
                addressService.getAddressById(1);

        assertEquals(
                1,
                fetchedAddress.getAddressId()
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

        when(addressRepository.findById(999))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(
                AddressNotFoundException.class,

                () -> addressService.getAddressById(999)
        );

        assertTrue(
                exception.getMessage()
                        .contains("Address not found")
        );
    }

    /*
     * ============================================================
     * TEST CREATE ADDRESS WITH NULL STREET
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address With Null Street")
    public void testCreateAddressWithNullStreet() {

        AddressRequestDto requestDto =
                buildRequestDto();

        requestDto.setStreet(null);

        assertNull(
                requestDto.getStreet()
        );
    }

    /*
     * ============================================================
     * TEST CREATE ADDRESS WITH BLANK CITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address With Blank City")
    public void testCreateAddressWithBlankCity() {

        AddressRequestDto requestDto =
                buildRequestDto();

        requestDto.setCity("");

        assertEquals(
                "",
                requestDto.getCity()
        );
    }

    /*
     * ============================================================
     * TEST CREATE ADDRESS WITH INVALID POSTAL CODE
     * ============================================================
     */
    @Test
    @DisplayName("Test Create Address With Invalid Postal Code")
    public void testCreateAddressWithInvalidPostalCode() {

        AddressRequestDto requestDto =
                buildRequestDto();

        requestDto.setPostalCode("12");

        assertEquals(
                "12",
                requestDto.getPostalCode()
        );
    }

    /*
     * ============================================================
     * TEST GET ADDRESS WITH NEGATIVE ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Address With Negative Id")
    public void testGetAddressWithNegativeId() {

        when(addressRepository.findById(-1))
                .thenReturn(Optional.empty());

        assertThrows(
                AddressNotFoundException.class,

                () ->
                        addressService
                                .getAddressById(-1)
        );
    }

    /*
     * ============================================================
     * TEST GET ADDRESS WITH ZERO ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Address With Zero Id")
    public void testGetAddressWithZeroId() {

        when(addressRepository.findById(0))
                .thenReturn(Optional.empty());

        assertThrows(
                AddressNotFoundException.class,

                () ->
                        addressService
                                .getAddressById(0)
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

        AddressRequestDto requestDto =
                buildRequestDto();

        Address address =
                buildAddress();

        AddressResponseDto responseDto =
                buildResponseDto();

        when(addressMapper.toEntity(requestDto))
                .thenReturn(address);

        when(addressRepository.save(any(Address.class)))
                .thenReturn(address);

        when(addressMapper.toResponseDto(address))
                .thenReturn(responseDto);

        addressService.createAddress(
                requestDto
        );

        verify(addressRepository, times(1))
                .save(any(Address.class));
    }

    /*
     * ============================================================
     * TEST FIND BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find By Id Called")
    public void testFindByIdCalled() {

        Address address =
                buildAddress();

        AddressResponseDto responseDto =
                buildResponseDto();

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(addressMapper.toResponseDto(address))
                .thenReturn(responseDto);

        addressService.getAddressById(1);

        verify(addressRepository, times(1))
                .findById(1);
    }

    /*
     * ============================================================
     * TEST COUNTRY VALUE
     * ============================================================
     */
    @Test
    @DisplayName("Test Country Value")
    public void testCountryValue() {

        Address address =
                buildAddress();

        assertEquals(
                "India",
                address.getCountry()
        );
    }

    /*
     * ============================================================
     * TEST STATE VALUE
     * ============================================================
     */
    @Test
    @DisplayName("Test State Value")
    public void testStateValue() {

        Address address =
                buildAddress();

        assertEquals(
                "Tamil Nadu",
                address.getState()
        );
    }

    /*
     * ============================================================
     * TEST CITY VALUE
     * ============================================================
     */
    @Test
    @DisplayName("Test City Value")
    public void testCityValue() {

        Address address =
                buildAddress();

        assertEquals(
                "Chennai",
                address.getCity()
        );
    }

    /*
     * ============================================================
     * TEST STREET VALUE
     * ============================================================
     */
    @Test
    @DisplayName("Test Street Value")
    public void testStreetValue() {

        Address address =
                buildAddress();

        assertEquals(
                "Anna Nagar",
                address.getStreet()
        );
    }

    /*
     * ============================================================
     * TEST POSTAL CODE VALUE
     * ============================================================
     */
    @Test
    @DisplayName("Test Postal Code Value")
    public void testPostalCodeValue() {

        Address address =
                buildAddress();

        assertEquals(
                "600040",
                address.getPostalCode()
        );
    }
}