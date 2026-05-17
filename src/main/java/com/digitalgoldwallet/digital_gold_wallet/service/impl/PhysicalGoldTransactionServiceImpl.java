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

import com.digitalgoldwallet.digital_gold_wallet.service.PhysicalGoldTransactionService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhysicalGoldTransactionServiceImpl
        implements PhysicalGoldTransactionService {

    private final
    PhysicalGoldTransactionRepository
            repository;

    private final
    UserRepository
            userRepository;

    private final
    VendorBranchRepository
            branchRepository;

    private final
    AddressRepository
            addressRepository;

    private final
    PhysicalGoldTransactionMapper
            mapper;

    public PhysicalGoldTransactionServiceImpl(

            PhysicalGoldTransactionRepository repository,

            UserRepository userRepository,

            VendorBranchRepository branchRepository,

            AddressRepository addressRepository,

            PhysicalGoldTransactionMapper mapper
    ){

        this.repository=repository;

        this.userRepository=userRepository;

        this.branchRepository=branchRepository;

        this.addressRepository=addressRepository;

        this.mapper=mapper;

    }

    @Override
    public PhysicalGoldTransactionResponseDto
    createTransaction(
            PhysicalGoldTransactionRequestDto dto
    ){

        User user=
                userRepository.findById(
                                dto.getUserId()
                        )
                        .orElseThrow(
                                ()->
                                        new UserNotFoundException(
                                                "User not found"
                                        )
                        );

        VendorBranch branch=
                branchRepository.findById(
                                dto.getBranchId()
                        )
                        .orElseThrow(
                                ()->
                                        new VendorBranchNotFoundException(
                                                "Branch not found"
                                        )
                        );

        Address address=
                addressRepository.findById(
                                dto.getAddressId()
                        )
                        .orElseThrow(
                                ()->
                                        new AddressNotFoundException(
                                                "Address not found"
                                        )
                        );

        PhysicalGoldTransaction transaction=
                mapper.toEntity(
                        dto,
                        user,
                        branch,
                        address
                );

        PhysicalGoldTransaction saved=
                repository.save(
                        transaction
                );

        return mapper.toResponseDto(
                saved
        );

    }

    @Override
    public PhysicalGoldTransactionResponseDto
    getById(
            Integer transactionId
    ){

        PhysicalGoldTransaction transaction=
                repository.findById(
                                transactionId
                        )
                        .orElseThrow(
                                ()->
                                        new PhysicalGoldTransactionNotFoundException(
                                                "Transaction not found"
                                        )
                        );

        return mapper.toResponseDto(
                transaction
        );

    }

    @Override
    public List<PhysicalGoldTransactionResponseDto>
    getByUser(
            Integer userId
    ){

        return repository
                .findByUserUserId(
                        userId
                )
                .stream()
                .map(
                        mapper::
                                toResponseDto
                )
                .toList();

    }

}