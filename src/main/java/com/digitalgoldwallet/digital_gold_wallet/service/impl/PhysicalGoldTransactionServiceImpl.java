package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;
import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;
import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.PhysicalGoldTransactionNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.mapper.PhysicalGoldTransactionMapper;
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.PhysicalGoldTransactionRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VirtualGoldHoldingRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.PhysicalGoldTransactionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhysicalGoldTransactionServiceImpl
        implements PhysicalGoldTransactionService {

    private final PhysicalGoldTransactionRepository repository;
    private final UserRepository userRepository;
    private final VendorBranchRepository branchRepository;
    private final AddressRepository addressRepository;
    private final PhysicalGoldTransactionMapper mapper;
    private final VirtualGoldHoldingRepository virtualGoldHoldingRepository;

    public PhysicalGoldTransactionServiceImpl(
            PhysicalGoldTransactionRepository repository,
            UserRepository userRepository,
            VendorBranchRepository branchRepository,
            AddressRepository addressRepository,
            PhysicalGoldTransactionMapper mapper,
            VirtualGoldHoldingRepository virtualGoldHoldingRepository
    ) {

        this.repository=repository;
        this.userRepository=userRepository;
        this.branchRepository=branchRepository;
        this.addressRepository=addressRepository;
        this.mapper=mapper;
        this.virtualGoldHoldingRepository=virtualGoldHoldingRepository;

    }

    /*
     * ============================================================
     * Convert virtual gold to physical
     *
     * Steps:
     * 1. Validate user has enough virtual holdings at the branch
     * 2. Create the PhysicalGoldTransaction record
     * 3. Deduct the converted quantity from VirtualGoldHolding rows
     *    (FIFO — oldest rows consumed first)
     * ============================================================
     */
    @Override
    @Transactional
    public PhysicalGoldTransactionResponseDto convertToPhysical(
            PhysicalGoldTransactionRequestDto dto
    ) {

        /*
         * Step 1 — Validate sufficient virtual holdings
         */
        BigDecimal totalHoldings =
                virtualGoldHoldingRepository
                        .sumQuantityByUserIdAndBranchId(
                                dto.getUserId(),
                                dto.getBranchId()
                        );

        if (totalHoldings == null) {
            totalHoldings = BigDecimal.ZERO;
        }

        if (dto.getQuantity().compareTo(totalHoldings) > 0) {
            throw new IllegalArgumentException(
                    "Insufficient virtual gold holdings. "
                            + "Requested: " + dto.getQuantity()
                            + ", Available: " + totalHoldings
            );
        }

        /*
         * Step 2 — Create the physical transaction record
         */
        PhysicalGoldTransactionResponseDto response = createTransaction(dto);

        /*
         * Step 3 — Deduct from VirtualGoldHolding rows (FIFO)
         */
        deductVirtualHoldings(
                dto.getUserId(),
                dto.getBranchId(),
                dto.getQuantity()
        );

        return response;

    }

    private void deductVirtualHoldings(
            Integer userId,
            Integer branchId,
            BigDecimal quantityToDeduct
    ) {

        /*
         * Fetch all holding rows for this user+branch,
         * sorted oldest-first for FIFO deduction.
         */
        List<VirtualGoldHolding> holdings =
                virtualGoldHoldingRepository
                        .findByUserUserIdAndBranchBranchId(
                                userId,
                                branchId
                        );

        holdings.sort((a, b) -> {
            if (a.getCreatedAt() == null) return -1;
            if (b.getCreatedAt() == null) return 1;
            return a.getCreatedAt().compareTo(b.getCreatedAt());
        });

        BigDecimal remainingToDeduct = quantityToDeduct;

        for (VirtualGoldHolding holding : holdings) {

            if (remainingToDeduct.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }

            BigDecimal rowQty = holding.getQuantity();

            if (rowQty.compareTo(remainingToDeduct) <= 0) {

                /*
                 * Entire row consumed — delete it
                 */
                remainingToDeduct = remainingToDeduct.subtract(rowQty);
                virtualGoldHoldingRepository.delete(holding);

            } else {

                /*
                 * Partial deduction — update and keep row
                 */
                holding.setQuantity(rowQty.subtract(remainingToDeduct));
                virtualGoldHoldingRepository.save(holding);
                remainingToDeduct = BigDecimal.ZERO;

            }
        }
    }

    @Override
    public PhysicalGoldTransactionResponseDto createTransaction(
            PhysicalGoldTransactionRequestDto dto
    ) {

        User user=
                userRepository.findById(
                        dto.getUserId()
                ).orElseThrow(
                        ()->new UserNotFoundException(
                                "User not found with ID: "
                                        + dto.getUserId()
                        )
                );

        VendorBranch branch=
                branchRepository.findById(
                        dto.getBranchId()
                ).orElseThrow(
                        ()->new VendorBranchNotFoundException(
                                "Branch not found with ID: "
                                        + dto.getBranchId()
                        )
                );

        Address address=
                addressRepository.findById(
                        dto.getAddressId()
                ).orElseThrow(
                        ()->new AddressNotFoundException(
                                "Address not found with ID: "
                                        + dto.getAddressId()
                        )
                );

        PhysicalGoldTransaction transaction=
                mapper.toEntity(
                        dto,
                        user,
                        branch,
                        address
                );

        transaction=
                repository.save(
                        transaction
                );

        return mapper.toResponseDto(
                transaction
        );

    }


    @Override
    public List<PhysicalGoldTransactionResponseDto>
    getByUser(
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


    @Override
    public PhysicalGoldTransactionResponseDto
    getById(
            Integer id
    ) {

        PhysicalGoldTransaction transaction=
                repository.findById(id)
                        .orElseThrow(
                                ()->
                                        new PhysicalGoldTransactionNotFoundException(
                                                "Physical transaction not found with ID: "
                                                        + id
                                        )
                        );

        return mapper.toResponseDto(
                transaction
        );

    }

}