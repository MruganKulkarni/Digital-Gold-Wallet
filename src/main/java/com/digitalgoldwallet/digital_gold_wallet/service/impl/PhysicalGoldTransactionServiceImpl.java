package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;
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

    @Override
    public PhysicalGoldTransactionResponseDto convertToPhysical(
            PhysicalGoldTransactionRequestDto dto
    ) {

        BigDecimal totalHoldings=
                virtualGoldHoldingRepository
                        .sumQuantityByUserIdAndBranchId(
                                dto.getUserId(),
                                dto.getBranchId()
                        );

        if(totalHoldings==null){

            totalHoldings=BigDecimal.ZERO;

        }

        if(dto.getQuantity().compareTo(totalHoldings)>0){

            throw new IllegalArgumentException(
                    "Insufficient virtual gold holdings"
            );

        }

        return createTransaction(dto);

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