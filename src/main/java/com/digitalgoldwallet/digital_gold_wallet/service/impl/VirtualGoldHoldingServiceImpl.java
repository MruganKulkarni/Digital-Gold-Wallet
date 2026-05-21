package com.digitalgoldwallet.digital_gold_wallet.service.impl;

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
import com.digitalgoldwallet.digital_gold_wallet.service.VirtualGoldHoldingService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirtualGoldHoldingServiceImpl
        implements VirtualGoldHoldingService {

    private final VirtualGoldHoldingRepository repository;

    private final UserRepository userRepository;

    private final VendorBranchRepository branchRepository;

    private final VirtualGoldHoldingMapper mapper;

    public VirtualGoldHoldingServiceImpl(

            VirtualGoldHoldingRepository repository,

            UserRepository userRepository,

            VendorBranchRepository branchRepository,

            VirtualGoldHoldingMapper mapper
    ) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.mapper = mapper;
    }


    /*
     * ============================================================
     * Buy virtual gold
     * ============================================================
     */
    @Override
    public VirtualGoldHoldingResponseDto buyGold(
            VirtualGoldHoldingRequestDto dto
    ) {

        User user =
                userRepository.findById(
                        dto.getUserId()
                ).orElseThrow(
                        () -> new UserNotFoundException(
                                "User not found"
                        )
                );

        VendorBranch branch =
                branchRepository.findById(
                        dto.getBranchId()
                ).orElseThrow(
                        () -> new VendorBranchNotFoundException(
                                "Branch not found"
                        )
                );

        VirtualGoldHolding holding =
                mapper.toEntity(
                        dto,
                        user,
                        branch
                );

        holding =
                repository.save(
                        holding
                );

        return mapper.toResponseDto(
                holding
        );
    }


    /*
     * ============================================================
     * Sell gold
     *
     * Deducts the requested quantity from the user's existing
     * VirtualGoldHolding rows at the given branch.
     *
     * Uses FIFO order (oldest holding rows consumed first).
     * Rows that reach 0 are deleted.
     * Throws IllegalArgumentException if holdings are insufficient.
     * ============================================================
     */
    @Override
    @Transactional
    public VirtualGoldHoldingResponseDto sellGold(
            VirtualGoldHoldingRequestDto dto
    ) {

        /*
         * Validate user exists
         */
        if (!userRepository.existsById(dto.getUserId())) {
            throw new UserNotFoundException(
                    "User not found with ID: " + dto.getUserId()
            );
        }

        /*
         * Validate branch exists
         */
        if (!branchRepository.existsById(dto.getBranchId())) {
            throw new VendorBranchNotFoundException(
                    "Branch not found with ID: " + dto.getBranchId()
            );
        }

        /*
         * Check total holdings cover the sell quantity
         */
        BigDecimal totalHoldings =
                repository.sumQuantityByUserIdAndBranchId(
                        dto.getUserId(),
                        dto.getBranchId()
                );

        if (totalHoldings == null) {
            totalHoldings = BigDecimal.ZERO;
        }

        BigDecimal quantityToSell = dto.getQuantity();

        if (quantityToSell.compareTo(totalHoldings) > 0) {
            throw new IllegalArgumentException(
                    "Insufficient virtual gold holdings. "
                            + "Requested: " + quantityToSell
                            + ", Available: " + totalHoldings
            );
        }

        /*
         * Fetch all holding rows for this user+branch (oldest first)
         * and deduct FIFO
         */
        List<VirtualGoldHolding> holdings =
                repository.findByUserUserIdAndBranchBranchId(
                        dto.getUserId(),
                        dto.getBranchId()
                );

        BigDecimal remaining = quantityToSell;
        VirtualGoldHolding lastModified = null;

        for (VirtualGoldHolding holding : holdings) {

            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal rowQty = holding.getQuantity();

            if (rowQty.compareTo(remaining) <= 0) {

                /*
                 * Entire row consumed — delete it
                 */
                remaining = remaining.subtract(rowQty);
                repository.delete(holding);

            } else {

                /*
                 * Partial deduction — update and keep row
                 */
                holding.setQuantity(rowQty.subtract(remaining));
                lastModified = repository.save(holding);
                remaining = BigDecimal.ZERO;

            }
        }

        /*
         * Build a synthetic response representing the sold amount
         */
        VirtualGoldHoldingResponseDto response =
                new VirtualGoldHoldingResponseDto();

        response.setUserId(dto.getUserId());
        response.setBranchId(dto.getBranchId());
        response.setQuantity(quantityToSell);
        response.setCreatedAt(LocalDateTime.now());

        if (lastModified != null) {
            response.setHoldingId(lastModified.getHoldingId());
        }

        return response;
    }


    /*
     * ============================================================
     * Get holding by ID
     * ============================================================
     */
    @Override
    public VirtualGoldHoldingResponseDto getHoldingById(
            Integer id
    ) {

        VirtualGoldHolding holding =
                repository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new VirtualGoldHoldingNotFoundException(
                                                "Holding not found"
                                        )
                        );

        return mapper.toResponseDto(
                holding
        );

    }


    /*
     * ============================================================
     * User holdings
     * ============================================================
     */
    @Override
    public List<VirtualGoldHoldingResponseDto>
    getHoldingsByUser(
            Integer userId
    ) {

        if(!userRepository.existsById(userId)){

            throw new UserNotFoundException(
                    "User not found with ID: "
                            + userId
            );

        }

        return repository
                .findByUserUserId(userId)
                .stream()
                .map(
                        mapper::toResponseDto
                )
                .collect(
                        Collectors.toList()
                );

    }


    /*
     * ============================================================
     * Branch holdings
     * ============================================================
     */
    @Override
    public List<VirtualGoldHoldingResponseDto>
    getHoldingsByBranch(
            Integer branchId
    ) {

        if(!branchRepository.existsById(branchId)){

            throw new VendorBranchNotFoundException(
                    "Branch not found with ID: "
                            + branchId
            );

        }

        return repository
                .findByBranchBranchId(branchId)
                .stream()
                .map(
                        mapper::toResponseDto
                )
                .collect(
                        Collectors.toList()
                );

    }

}