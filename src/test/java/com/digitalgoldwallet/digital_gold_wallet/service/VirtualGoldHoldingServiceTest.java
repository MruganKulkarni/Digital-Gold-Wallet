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

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Virtual Gold Holding Service Test
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
class VirtualGoldHoldingServiceTest {

    /*
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
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
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private VirtualGoldHoldingServiceImpl service;

    /*
     * ============================================================
     * COMMON ENTITIES
     * ============================================================
     */
    private User user;

    private VendorBranch branch;

    private VirtualGoldHolding holding;

    private VirtualGoldHoldingRequestDto requestDto;

    private VirtualGoldHoldingResponseDto responseDto;

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
     * BUY GOLD SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Buy Gold Success")
    void buyGold_Success() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(vendorBranchRepository.findById(10))
                .thenReturn(Optional.of(branch));

        when(mapper.toEntity(
                any(),
                any(),
                any()
        )).thenReturn(holding);

        when(holdingRepository.save(any()))
                .thenReturn(holding);

        when(mapper.toResponseDto(holding))
                .thenReturn(responseDto);

        VirtualGoldHoldingResponseDto result =
                service.buyGold(requestDto);

        assertNotNull(result);

        assertEquals(
                100,
                result.getHoldingId()
        );

        verify(holdingRepository, times(1))
                .save(any());
    }

    /*
     * ============================================================
     * USER NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test User Not Found")
    void buyGold_UserNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,

                () ->
                        service.buyGold(requestDto)
        );

        verify(holdingRepository, never())
                .save(any());
    }

    /*
     * ============================================================
     * BRANCH NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Branch Not Found")
    void buyGold_BranchNotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(vendorBranchRepository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(
                VendorBranchNotFoundException.class,

                () ->
                        service.buyGold(requestDto)
        );
    }

    /*
     * ============================================================
     * GET HOLDING SUCCESS
     * ============================================================
     */
    @Test
    @DisplayName("Test Get Holding Success")
    void getHoldingById_Success() {

        when(holdingRepository.findById(100))
                .thenReturn(Optional.of(holding));

        when(mapper.toResponseDto(holding))
                .thenReturn(responseDto);

        VirtualGoldHoldingResponseDto result =
                service.getHoldingById(100);

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
    @DisplayName("Test Get Holding Not Found")
    void getHoldingById_NotFound() {

        when(holdingRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                VirtualGoldHoldingNotFoundException.class,

                () ->
                        service.getHoldingById(1)
        );
    }

    /*
     * ============================================================
     * SELL GOLD INSUFFICIENT QUANTITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Sell Gold Insufficient Quantity")
    void sellGold_InsufficientQuantity() {

        VirtualGoldHoldingRequestDto request =
                new VirtualGoldHoldingRequestDto(
                        1,
                        1,
                        BigDecimal.valueOf(10)
                );

        VirtualGoldHolding existingHolding =
                new VirtualGoldHolding();

        existingHolding.setHoldingId(1);

        existingHolding.setUser(user);

        existingHolding.setBranch(branch);

        existingHolding.setQuantity(
                BigDecimal.valueOf(5)
        );

        lenient().when(
                holdingRepository.findByUserUserId(1)
        ).thenReturn(
                List.of(existingHolding)
        );

        assertThrows(
                RuntimeException.class,

                () ->
                        service.sellGold(request)
        );
    }

    /*
     * ============================================================
     * EMPTY USER HOLDINGS
     * ============================================================
     */
    @Test
    @DisplayName("Test Empty User Holdings")
    void getHoldingsByUser_Empty() {

        when(holdingRepository.findByUserUserId(1))
                .thenReturn(Collections.emptyList());

        List<VirtualGoldHoldingResponseDto> result =
                service.getHoldingsByUser(1);

        assertTrue(
                result.isEmpty()
        );
    }

    /*
     * ============================================================
     * EMPTY BRANCH HOLDINGS
     * ============================================================
     */
    @Test
    @DisplayName("Test Empty Branch Holdings")
    void getHoldingsByBranch_Empty() {

        when(holdingRepository.findByBranchBranchId(1))
                .thenReturn(Collections.emptyList());

        List<VirtualGoldHoldingResponseDto> result =
                service.getHoldingsByBranch(1);

        assertTrue(
                result.isEmpty()
        );
    }

    /*
     * ============================================================
     * TEST NULL USER ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Null User Id")
    void testNullUserId() {

        requestDto.setUserId(null);

        assertNull(
                requestDto.getUserId()
        );
    }

    /*
     * ============================================================
     * TEST NULL BRANCH ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Branch Id")
    void testNullBranchId() {

        requestDto.setBranchId(null);

        assertNull(
                requestDto.getBranchId()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE QUANTITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Quantity")
    void testNegativeQuantity() {

        requestDto.setQuantity(
                new BigDecimal("-5")
        );

        assertEquals(
                new BigDecimal("-5"),
                requestDto.getQuantity()
        );
    }

    /*
     * ============================================================
     * TEST ZERO QUANTITY
     * ============================================================
     */
    @Test
    @DisplayName("Test Zero Quantity")
    void testZeroQuantity() {

        requestDto.setQuantity(
                BigDecimal.ZERO
        );

        assertEquals(
                BigDecimal.ZERO,
                requestDto.getQuantity()
        );
    }

    /*
     * ============================================================
     * TEST SAVE METHOD CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Save Method Called")
    void testSaveMethodCalled() {

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(vendorBranchRepository.findById(10))
                .thenReturn(Optional.of(branch));

        when(mapper.toEntity(
                any(),
                any(),
                any()
        )).thenReturn(holding);

        when(holdingRepository.save(any()))
                .thenReturn(holding);

        when(mapper.toResponseDto(holding))
                .thenReturn(responseDto);

        service.buyGold(requestDto);

        verify(holdingRepository, times(1))
                .save(any());
    }

    /*
     * ============================================================
     * TEST FIND BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find By Id Called")
    void testFindByIdCalled() {

        when(holdingRepository.findById(100))
                .thenReturn(Optional.of(holding));

        when(mapper.toResponseDto(holding))
                .thenReturn(responseDto);

        service.getHoldingById(100);

        verify(holdingRepository, times(1))
                .findById(100);
    }

}