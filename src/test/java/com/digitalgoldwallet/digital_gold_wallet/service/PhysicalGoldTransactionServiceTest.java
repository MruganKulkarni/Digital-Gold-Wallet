package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.mapper.PhysicalGoldTransactionMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.PhysicalGoldTransactionRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.PhysicalGoldTransactionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Physical Gold Transaction Service Test
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
class PhysicalGoldTransactionServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
     */
    @Mock
    private PhysicalGoldTransactionRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VendorBranchRepository branchRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PhysicalGoldTransactionMapper mapper;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private PhysicalGoldTransactionServiceImpl service;

    /*
     * ============================================================
     * COMMON ENTITIES
     * ============================================================
     */
    private User user;

    private VendorBranch branch;

    private Address address;

    private PhysicalGoldTransaction transaction;

    private PhysicalGoldTransactionRequestDto dto;

    private PhysicalGoldTransactionResponseDto response;

    /*
     * ============================================================
     * SETUP
     * ============================================================
     */
    @BeforeEach
    void setup() {

        user = new User();
        user.setUserId(1);

        branch = new VendorBranch();
        branch.setBranchId(2);

        address = new Address();
        address.setAddressId(3);

        transaction =
                new PhysicalGoldTransaction();

        transaction.setTransactionId(100);

        transaction.setUser(user);

        transaction.setBranch(branch);

        transaction.setDeliveryAddress(address);

        transaction.setQuantity(
                new BigDecimal("10")
        );

        dto =
                new PhysicalGoldTransactionRequestDto();

        dto.setUserId(1);

        dto.setBranchId(2);

        dto.setAddressId(3);

        dto.setQuantity(
                new BigDecimal("10")
        );

        response =
                new PhysicalGoldTransactionResponseDto();

        response.setTransactionId(100);

        response.setUserId(1);

        response.setBranchId(2);

        response.setAddressId(3);
    }

    /*
     * ==================================================
     * CREATE SUCCESS
     * ==================================================
     */
    @Test
    @DisplayName("Test Create Transaction Success")
    void createTransaction_Success() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.of(branch));

        when(addressRepository.findById(3))
                .thenReturn(Optional.of(address));

        when(mapper.toEntity(
                dto,
                user,
                branch,
                address
        )).thenReturn(transaction);

        when(repository.save(transaction))
                .thenReturn(transaction);

        when(mapper.toResponseDto(transaction))
                .thenReturn(response);

        PhysicalGoldTransactionResponseDto result =
                service.createTransaction(dto);

        assertNotNull(result);

        assertEquals(
                100,
                result.getTransactionId()
        );

        verify(repository)
                .save(transaction);
    }

    /*
     * ==================================================
     * USER NOT FOUND
     * ==================================================
     */
    @Test
    @DisplayName("Test User Not Found")
    void createTransaction_UserNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,

                () ->
                        service.createTransaction(dto)
        );

        verify(repository, never())
                .save(any());
    }

    /*
     * ==================================================
     * BRANCH NOT FOUND
     * ==================================================
     */
    @Test
    @DisplayName("Test Branch Not Found")
    void createTransaction_BranchNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorBranchNotFoundException.class,

                () ->
                        service.createTransaction(dto)
        );
    }

    /*
     * ==================================================
     * ADDRESS NOT FOUND
     * ==================================================
     */
    @Test
    @DisplayName("Test Address Not Found")
    void createTransaction_AddressNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.of(branch));

        when(addressRepository.findById(3))
                .thenReturn(Optional.empty());

        assertThrows(
                AddressNotFoundException.class,

                () ->
                        service.createTransaction(dto)
        );
    }

    /*
     * ==================================================
     * GET BY ID SUCCESS
     * ==================================================
     */
    @Test
    @DisplayName("Test Get By Id Success")
    void getById_Success() {

        when(repository.findById(100))
                .thenReturn(Optional.of(transaction));

        when(mapper.toResponseDto(transaction))
                .thenReturn(response);

        PhysicalGoldTransactionResponseDto result =
                service.getById(100);

        assertNotNull(result);

        assertEquals(
                100,
                result.getTransactionId()
        );
    }

    /*
     * ==================================================
     * GET BY ID NOT FOUND
     * ==================================================
     */
    @Test
    @DisplayName("Test Get By Id Not Found")
    void getById_NotFound() {

        when(repository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,

                () ->
                        service.getById(999)
        );
    }

    /*
     * ==================================================
     * EMPTY USER TRANSACTIONS
     * ==================================================
     */
    @Test
    @DisplayName("Test Empty User Transactions")
    void getByUser_Empty() {

        when(repository.findByUserUserId(1))
                .thenReturn(Collections.emptyList());

        List<PhysicalGoldTransactionResponseDto>
                result =
                service.getByUser(1);

        assertTrue(
                result.isEmpty()
        );
    }

    /*
     * ==================================================
     * TEST NULL USER ID
     * ==================================================
     */
    @Test
    @DisplayName("Test Null User Id")
    void testNullUserId() {

        dto.setUserId(null);

        assertNull(
                dto.getUserId()
        );
    }

    /*
     * ==================================================
     * TEST NULL BRANCH ID
     * ==================================================
     */
    @Test
    @DisplayName("Test Null Branch Id")
    void testNullBranchId() {

        dto.setBranchId(null);

        assertNull(
                dto.getBranchId()
        );
    }

    /*
     * ==================================================
     * TEST NULL ADDRESS ID
     * ==================================================
     */
    @Test
    @DisplayName("Test Null Address Id")
    void testNullAddressId() {

        dto.setAddressId(null);

        assertNull(
                dto.getAddressId()
        );
    }

    /*
     * ==================================================
     * TEST NEGATIVE QUANTITY
     * ==================================================
     */
    @Test
    @DisplayName("Test Negative Quantity")
    void testNegativeQuantity() {

        dto.setQuantity(
                new BigDecimal("-10")
        );

        assertEquals(
                new BigDecimal("-10"),
                dto.getQuantity()
        );
    }

    /*
     * ==================================================
     * TEST ZERO QUANTITY
     * ==================================================
     */
    @Test
    @DisplayName("Test Zero Quantity")
    void testZeroQuantity() {

        dto.setQuantity(
                BigDecimal.ZERO
        );

        assertEquals(
                BigDecimal.ZERO,
                dto.getQuantity()
        );
    }

    /*
     * ==================================================
     * TEST SAVE METHOD CALLED
     * ==================================================
     */
    @Test
    @DisplayName("Test Save Method Called")
    void testSaveMethodCalled() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.of(branch));

        when(addressRepository.findById(3))
                .thenReturn(Optional.of(address));

        when(mapper.toEntity(
                dto,
                user,
                branch,
                address
        )).thenReturn(transaction);

        when(repository.save(transaction))
                .thenReturn(transaction);

        when(mapper.toResponseDto(transaction))
                .thenReturn(response);

        service.createTransaction(dto);

        verify(repository, times(1))
                .save(transaction);
    }

    /*
     * ==================================================
     * TEST FIND BY ID CALLED
     * ==================================================
     */
    @Test
    @DisplayName("Test Find By Id Called")
    void testFindByIdCalled() {

        when(repository.findById(100))
                .thenReturn(Optional.of(transaction));

        when(mapper.toResponseDto(transaction))
                .thenReturn(response);

        service.getById(100);

        verify(repository, times(1))
                .findById(100);
    }

}