package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.TransactionHistoryRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.mapper.TransactionHistoryMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.TransactionHistoryRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.TransactionHistoryServiceImpl;

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

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * Transaction History Service Test
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
class TransactionHistoryServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
     */
    @Mock
    private TransactionHistoryRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VendorBranchRepository branchRepository;

    @Mock
    private TransactionHistoryMapper mapper;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private TransactionHistoryServiceImpl service;

    /*
     * ============================================================
     * COMMON ENTITIES
     * ============================================================
     */
    private User user;

    private VendorBranch branch;

    private TransactionHistory history;

    private TransactionHistoryRequestDto dto;

    private TransactionHistoryResponseDto response;

    /*
     * ============================================================
     * SETUP
     * ============================================================
     */
    @BeforeEach
    void setup(){

        user = new User();
        user.setUserId(1);

        branch = new VendorBranch();
        branch.setBranchId(2);

        history =
                new TransactionHistory();

        history.setTransactionId(100);

        history.setUser(user);

        history.setBranch(branch);

        history.setAmount(
                new BigDecimal("5000")
        );

        dto =
                new TransactionHistoryRequestDto();

        dto.setUserId(1);

        dto.setBranchId(2);

        dto.setTransactionType(
                "BUY"
        );

        dto.setTransactionStatus(
                "SUCCESS"
        );

        dto.setQuantity(
                new BigDecimal("5")
        );

        dto.setAmount(
                new BigDecimal("5000")
        );

        response =
                new TransactionHistoryResponseDto();

        response.setTransactionId(100);

        response.setUserId(1);

        response.setBranchId(2);
    }

    /*
     * ==================================================
     * CREATE SUCCESS
     * ==================================================
     */
    @Test
    @DisplayName("Test Create Transaction Success")
    void createTransaction_Success(){

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.of(branch));

        when(mapper.toEntity(
                dto,
                user,
                branch
        )).thenReturn(history);

        when(repository.save(history))
                .thenReturn(history);

        when(mapper.toResponseDto(history))
                .thenReturn(response);

        TransactionHistoryResponseDto result =
                service.createTransaction(dto);

        assertNotNull(result);

        assertEquals(
                100,
                result.getTransactionId()
        );

        verify(repository)
                .save(history);
    }

    /*
     * ==================================================
     * USER NOT FOUND
     * ==================================================
     */
    @Test
    @DisplayName("Test User Not Found")
    void createTransaction_UserNotFound(){

        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,

                () ->
                        service.createTransaction(dto)
        );
    }

    /*
     * ==================================================
     * BRANCH NOT FOUND
     * ==================================================
     */
    @Test
    @DisplayName("Test Branch Not Found")
    void createTransaction_BranchNotFound(){

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
     * USER TRANSACTIONS EMPTY
     * ==================================================
     */
    @Test
    @DisplayName("Test User Transactions Empty")
    void getTransactionsByUser_Empty() {

        when(repository.findByUserUserId(1))
                .thenReturn(Collections.emptyList());

        List<TransactionHistoryResponseDto>
                result =
                service.getTransactionsByUser(1);

        assertTrue(
                result.isEmpty()
        );
    }

    /*
     * ==================================================
     * BRANCH TRANSACTIONS EMPTY
     * ==================================================
     */
    @Test
    @DisplayName("Test Branch Transactions Empty")
    void getTransactionsByBranch_Empty() {

        when(repository.findByBranchBranchId(2))
                .thenReturn(Collections.emptyList());

        List<TransactionHistoryResponseDto>
                result =
                service.getTransactionsByBranch(2);

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
     * TEST NEGATIVE QUANTITY
     * ==================================================
     */
    @Test
    @DisplayName("Test Negative Quantity")
    void testNegativeQuantity() {

        dto.setQuantity(
                new BigDecimal("-5")
        );

        assertEquals(
                new BigDecimal("-5"),
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
     * TEST NEGATIVE AMOUNT
     * ==================================================
     */
    @Test
    @DisplayName("Test Negative Amount")
    void testNegativeAmount() {

        dto.setAmount(
                new BigDecimal("-100")
        );

        assertEquals(
                new BigDecimal("-100"),
                dto.getAmount()
        );
    }

    /*
     * ==================================================
     * TEST NULL TRANSACTION TYPE
     * ==================================================
     */
    @Test
    @DisplayName("Test Null Transaction Type")
    void testNullTransactionType() {

        dto.setTransactionType(null);

        assertNull(
                dto.getTransactionType()
        );
    }

    /*
     * ==================================================
     * TEST NULL TRANSACTION STATUS
     * ==================================================
     */
    @Test
    @DisplayName("Test Null Transaction Status")
    void testNullTransactionStatus() {

        dto.setTransactionStatus(null);

        assertNull(
                dto.getTransactionStatus()
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

        when(mapper.toEntity(
                dto,
                user,
                branch
        )).thenReturn(history);

        when(repository.save(history))
                .thenReturn(history);

        when(mapper.toResponseDto(history))
                .thenReturn(response);

        service.createTransaction(dto);

        verify(repository, times(1))
                .save(history);
    }

    /*
     * ==================================================
     * TEST FIND USER TRANSACTIONS CALLED
     * ==================================================
     */
    @Test
    @DisplayName("Test Find User Transactions Called")
    void testFindUserTransactionsCalled() {

        when(repository.findByUserUserId(1))
                .thenReturn(Collections.emptyList());

        service.getTransactionsByUser(1);

        verify(repository, times(1))
                .findByUserUserId(1);
    }

    /*
     * ==================================================
     * TEST FIND BRANCH TRANSACTIONS CALLED
     * ==================================================
     */
    @Test
    @DisplayName("Test Find Branch Transactions Called")
    void testFindBranchTransactionsCalled() {

        when(repository.findByBranchBranchId(2))
                .thenReturn(Collections.emptyList());

        service.getTransactionsByBranch(2);

        verify(repository, times(1))
                .findByBranchBranchId(2);
    }

}