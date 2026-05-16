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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhysicalGoldTransactionServiceTest {

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

    @InjectMocks
    private PhysicalGoldTransactionServiceImpl service;

    private User user;
    private VendorBranch branch;
    private Address address;
    private PhysicalGoldTransaction transaction;
    private PhysicalGoldTransactionRequestDto dto;
    private PhysicalGoldTransactionResponseDto response;

    @BeforeEach
    void setup() {

        user=new User();
        user.setUserId(1);

        branch=new VendorBranch();
        branch.setBranchId(2);

        address=new Address();
        address.setAddressId(3);

        transaction=new PhysicalGoldTransaction();
        transaction.setTransactionId(100);
        transaction.setUser(user);
        transaction.setBranch(branch);
        transaction.setDeliveryAddress(address);
        transaction.setQuantity(
                new BigDecimal("10")
        );

        dto=new PhysicalGoldTransactionRequestDto();
        dto.setUserId(1);
        dto.setBranchId(2);
        dto.setAddressId(3);
        dto.setQuantity(
                new BigDecimal("10")
        );

        response=
                new PhysicalGoldTransactionResponseDto();

        response.setTransactionId(100);
    }

    /*
     * ==================================================
     * SUCCESS
     * ==================================================
     */

    @Test
    void createTransaction_Success() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.of(branch));

        when(addressRepository.findById(3))
                .thenReturn(Optional.of(address));

        when(
                mapper.toEntity(
                        dto,
                        user,
                        branch,
                        address
                )
        ).thenReturn(transaction);

        when(repository.save(transaction))
                .thenReturn(transaction);

        when(mapper.toResponseDto(transaction))
                .thenReturn(response);

        PhysicalGoldTransactionResponseDto result=
                service.createTransaction(dto);

        assertNotNull(result);

        verify(repository)
                .save(transaction);
    }

    /*
     * ==================================================
     * USER NOT FOUND
     * ==================================================
     */

    @Test
    void createTransaction_UserNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                ()->service.createTransaction(dto)
        );

        verify(repository,never())
                .save(any());
    }

    /*
     * ==================================================
     * BRANCH NOT FOUND
     * ==================================================
     */

    @Test
    void createTransaction_BranchNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorBranchNotFoundException.class,
                ()->service.createTransaction(dto)
        );
    }

    /*
     * ==================================================
     * ADDRESS NOT FOUND
     * ==================================================
     */

    @Test
    void createTransaction_AddressNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(branchRepository.findById(2))
                .thenReturn(Optional.of(branch));

        when(addressRepository.findById(3))
                .thenReturn(Optional.empty());

        assertThrows(
                AddressNotFoundException.class,
                ()->service.createTransaction(dto)
        );
    }
}