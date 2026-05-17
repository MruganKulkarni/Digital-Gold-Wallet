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
 * TransactionHistory Service Test
 * ============================================================
 *
 * Covers:
 *
 * - create success
 * - user not found
 * - branch not found
 * - empty user transactions
 * - empty branch transactions
 *
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
class TransactionHistoryServiceTest {

    @Mock
    private TransactionHistoryRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VendorBranchRepository branchRepository;

    @Mock
    private TransactionHistoryMapper mapper;

    @InjectMocks
    private TransactionHistoryServiceImpl service;

    private User user;

    private VendorBranch branch;

    private TransactionHistory history;

    private TransactionHistoryRequestDto dto;

    private TransactionHistoryResponseDto response;


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
    void createTransaction_Success(){

        when(
                userRepository.findById(1)
        )
                .thenReturn(
                        Optional.of(user)
                );

        when(
                branchRepository.findById(2)
        )
                .thenReturn(
                        Optional.of(branch)
                );

        when(
                mapper.toEntity(
                        dto,
                        user,
                        branch
                )
        )
                .thenReturn(
                        history
                );

        when(
                repository.save(history)
        )
                .thenReturn(
                        history
                );

        when(
                mapper.toResponseDto(history)
        )
                .thenReturn(
                        response
                );


        TransactionHistoryResponseDto result =

                service.createTransaction(
                        dto
                );


        assertNotNull(
                result
        );

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
    void createTransaction_UserNotFound(){

        when(
                userRepository.findById(1)
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                UserNotFoundException.class,

                () ->
                        service.createTransaction(
                                dto
                        )
        );

    }



    /*
     * ==================================================
     * BRANCH NOT FOUND
     * ==================================================
     */

    @Test
    void createTransaction_BranchNotFound(){

        when(
                userRepository.findById(1)
        )
                .thenReturn(
                        Optional.of(user)
                );

        when(
                branchRepository.findById(2)
        )
                .thenReturn(
                        Optional.empty()
                );


        assertThrows(
                VendorBranchNotFoundException.class,

                () ->
                        service.createTransaction(
                                dto
                        )
        );

    }



    /*
     * ==================================================
     * USER TRANSACTIONS EMPTY
     * ==================================================
     */

    @Test
    void getTransactionsByUser_Empty() {

        when(
                repository.findByUserUserId(1)
        )
                .thenReturn(
                        Collections.emptyList()
                );


        List<TransactionHistoryResponseDto>
                result =

                service.getTransactionsByUser(
                        1
                );


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
    void getTransactionsByBranch_Empty() {

        when(
                repository.findByBranchBranchId(2)
        )
                .thenReturn(
                        Collections.emptyList()
                );


        List<TransactionHistoryResponseDto>
                result =

                service.getTransactionsByBranch(
                        2
                );


        assertTrue(
                result.isEmpty()
        );

    }

}