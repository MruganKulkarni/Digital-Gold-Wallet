package com.digitalgoldwallet.digital_gold_wallet.service.impl;

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
import com.digitalgoldwallet.digital_gold_wallet.service.PhysicalGoldTransactionService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
 * ============================================================
 * Physical Gold Transaction Service Implementation
 * ============================================================
 *
 * Handles:
 *
 * - Convert virtual gold to physical
 * - Create transaction
 * - Get user transactions
 * - Get transaction by ID
 *
 * ============================================================
 */

@Service
public class PhysicalGoldTransactionServiceImpl
        implements PhysicalGoldTransactionService {

    /*
     * Repository dependencies
     */
    private final PhysicalGoldTransactionRepository repository;

    private final UserRepository userRepository;

    private final VendorBranchRepository branchRepository;

    private final AddressRepository addressRepository;

    private final PhysicalGoldTransactionMapper mapper;


    /*
     * Constructor injection
     */

    public PhysicalGoldTransactionServiceImpl(

            PhysicalGoldTransactionRepository repository,

            UserRepository userRepository,

            VendorBranchRepository branchRepository,

            AddressRepository addressRepository,

            PhysicalGoldTransactionMapper mapper
    ) {

        this.repository = repository;

        this.userRepository = userRepository;

        this.branchRepository = branchRepository;

        this.addressRepository = addressRepository;

        this.mapper = mapper;
    }


    /*
     * ============================================================
     * Convert virtual to physical
     * ============================================================
     */

    @Override
    public PhysicalGoldTransactionResponseDto
    convertToPhysical(
            PhysicalGoldTransactionRequestDto dto
    ) {

        return createTransaction(dto);

    }


    /*
     * ============================================================
     * Create physical transaction
     * ============================================================
     */

    @Override
    public PhysicalGoldTransactionResponseDto
    createTransaction(
            PhysicalGoldTransactionRequestDto dto
    ) {

        /*
         * Fetch user
         */

        User user =
                userRepository.findById(
                                dto.getUserId()
                        )
                        .orElseThrow(
                                () ->
                                        new UserNotFoundException(
                                                "User not found with ID: "
                                                        + dto.getUserId()
                                        )
                        );


        /*
         * Fetch branch
         */

        VendorBranch branch =
                branchRepository.findById(
                                dto.getBranchId()
                        )
                        .orElseThrow(
                                () ->
                                        new VendorBranchNotFoundException(
                                                "Branch not found with ID: "
                                                        + dto.getBranchId()
                                        )
                        );


        /*
         * Fetch delivery address
         */

        Address address =
                addressRepository.findById(
                                dto.getAddressId()
                        )
                        .orElseThrow(
                                () ->
                                        new AddressNotFoundException(
                                                "Address not found with ID: "
                                                        + dto.getAddressId()
                                        )
                        );


        /*
         * DTO → Entity
         */

        PhysicalGoldTransaction transaction =

                mapper.toEntity(
                        dto,
                        user,
                        branch,
                        address
                );


        /*
         * Save transaction
         */

        transaction =
                repository.save(
                        transaction
                );


        /*
         * Entity → DTO
         */

        return mapper.toResponseDto(
                transaction
        );

    }


    /*
     * ============================================================
     * Get transactions by user
     * ============================================================
     */

    @Override
    public List<PhysicalGoldTransactionResponseDto>
    getByUser(
            Integer userId
    ) {

        return repository
                .findByUserUserId(
                        userId
                )
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
     * Get transaction by ID
     * ============================================================
     */

    @Override
    public PhysicalGoldTransactionResponseDto
    getById(
            Integer id
    ) {

        PhysicalGoldTransaction transaction =

                repository.findById(id)

                        .orElseThrow(
                                () ->
                                        new RuntimeException(
                                                "Physical transaction not found with ID: "
                                                        + id
                                        )
                        );


        return mapper.toResponseDto(
                transaction
        );

    }

}