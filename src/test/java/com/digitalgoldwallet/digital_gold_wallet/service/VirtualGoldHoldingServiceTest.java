package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VirtualGoldHoldingRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VirtualGoldHoldingResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;
import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VirtualGoldHoldingNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.mapper.VirtualGoldHoldingMapper;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VirtualGoldHoldingRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.impl.VirtualGoldHoldingServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
 * ============================================================
 * VirtualGoldHolding Service Test
 * ============================================================
 *
 * Unit tests for VirtualGoldHoldingServiceImpl
 *
 * Uses:
 * - JUnit 5
 * - Mockito
 *
 * No DB connection required
 *
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
class VirtualGoldHoldingServiceTest {

    /*
     * Mock dependencies
     */
    @Mock
    private VirtualGoldHoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VendorBranchRepository vendorBranchRepository;

    @Mock
    private VirtualGoldHoldingMapper mapper;

    /*
     * Inject mocks into service
     */
    @InjectMocks
    private VirtualGoldHoldingServiceImpl service;

    /*
     * Test objects
     */
    private User user;

    private VendorBranch branch;

    private VirtualGoldHolding holding;

    private VirtualGoldHoldingRequestDto requestDto;

    private VirtualGoldHoldingResponseDto responseDto;

    /*
     * Setup reusable data
     */
    @BeforeEach
    void setup() {

        user = new User();
        user.setUserId(1);

        branch = new VendorBranch();
        branch.setBranchId(10);

        holding =
                new VirtualGoldHolding();

        holding.setHoldingId(100);

        holding.setUser(user);

        holding.setBranch(branch);

        holding.setQuantity(
                new BigDecimal("5.00")
        );

        requestDto =
                new VirtualGoldHoldingRequestDto();

        requestDto.setUserId(1);

        requestDto.setBranchId(10);

        requestDto.setQuantity(
                new BigDecimal("5.00")
        );

        responseDto =
                new VirtualGoldHoldingResponseDto();

        responseDto.setHoldingId(100);

        responseDto.setUserId(1);

        responseDto.setBranchId(10);

        responseDto.setQuantity(
                new BigDecimal("5.00")
        );
    }

    /*
     * ============================================================
     * CREATE SUCCESS
     * ============================================================
     */

    @Test
    void createHolding_Success() {

        when(
                userRepository.findById(1)
        ).thenReturn(
                Optional.of(user)
        );

        when(
                vendorBranchRepository.findById(10)
        ).thenReturn(
                Optional.of(branch)
        );

        when(
                mapper.toEntity(
                        any(),
                        any(),
                        any()
                )
        ).thenReturn(
                holding
        );

        when(
                holdingRepository.save(
                        any(
                                VirtualGoldHolding.class
                        )
                )
        ).thenReturn(
                holding
        );

        when(
                mapper.toResponseDto(
                        holding
                )
        ).thenReturn(
                responseDto
        );

        VirtualGoldHoldingResponseDto result =
                service.createHolding(
                        requestDto
                );

        assertNotNull(result);

        assertEquals(
                100,
                result.getHoldingId()
        );

        assertEquals(
                new BigDecimal("5.00"),
                result.getQuantity()
        );

        verify(
                holdingRepository,
                times(1)
        ).save(any(
                VirtualGoldHolding.class
        ));
    }

    /*
     * ============================================================
     * USER NOT FOUND
     * ============================================================
     */

    @Test
    void createHolding_UserNotFound() {

        when(
                userRepository.findById(1)
        ).thenReturn(
                Optional.empty()
        );

        assertThrows(
                UserNotFoundException.class,

                () ->
                        service.createHolding(
                                requestDto
                        )
        );

        verify(
                holdingRepository,
                never()
        ).save(any());
    }

    /*
     * ============================================================
     * BRANCH NOT FOUND
     * ============================================================
     */

    @Test
    void createHolding_BranchNotFound() {

        when(
                userRepository.findById(1)
        ).thenReturn(
                Optional.of(user)
        );

        when(
                vendorBranchRepository
                        .findById(10)
        ).thenReturn(
                Optional.empty()
        );

        assertThrows(
                VendorBranchNotFoundException.class,

                () ->
                        service.createHolding(
                                requestDto
                        )
        );

        verify(
                holdingRepository,
                never()
        ).save(any());
    }

    /*
     * ============================================================
     * GET HOLDING SUCCESS
     * ============================================================
     */

    @Test
    void getHoldingById_Success() {

        when(
                holdingRepository.findById(100)
        ).thenReturn(
                Optional.of(holding)
        );

        when(
                mapper.toResponseDto(
                        holding
                )
        ).thenReturn(
                responseDto
        );

        VirtualGoldHoldingResponseDto result =
                service.getHoldingById(
                        100
                );

        assertNotNull(result);

        assertEquals(
                100,
                result.getHoldingId()
        );
    }

    /*
     * ============================================================
     * GET HOLDING NOT FOUND
     * ============================================================
     */

    @Test
    void getHoldingById_NotFound() {

        when(
                holdingRepository.findById(99)
        ).thenReturn(
                Optional.empty()
        );

        assertThrows(
                VirtualGoldHoldingNotFoundException.class,

                () ->
                        service.getHoldingById(
                                99
                        )
        );
    }
}