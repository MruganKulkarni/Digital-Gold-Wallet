package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.TransactionHistoryRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import com.digitalgoldwallet.digital_gold_wallet.exception.TransactionHistoryNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.VendorBranchNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.mapper.TransactionHistoryMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.TransactionHistoryRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.TransactionHistoryService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryServiceImpl
        implements TransactionHistoryService {

    private final
    TransactionHistoryRepository
            repository;

    private final
    UserRepository
            userRepository;

    private final
    VendorBranchRepository
            branchRepository;

    private final
    TransactionHistoryMapper
            mapper;

    public TransactionHistoryServiceImpl(

            TransactionHistoryRepository repository,

            UserRepository userRepository,

            VendorBranchRepository branchRepository,

            TransactionHistoryMapper mapper

    ){

        this.repository=repository;

        this.userRepository=userRepository;

        this.branchRepository=branchRepository;

        this.mapper=mapper;

    }

    @Override
    public TransactionHistoryResponseDto
    createTransaction(
            TransactionHistoryRequestDto dto
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

        TransactionHistory transaction=
                mapper.toEntity(
                        dto,
                        user,
                        branch
                );

        TransactionHistory saved=
                repository.save(
                        transaction
                );

        return mapper.toResponseDto(
                saved
        );

    }

    @Override
    public TransactionHistoryResponseDto
    getTransactionById(
            Integer transactionId
    ){

        TransactionHistory transaction=
                repository.findById(
                                transactionId
                        )
                        .orElseThrow(
                                ()->
                                        new TransactionHistoryNotFoundException(
                                                "Transaction not found"
                                        )
                        );

        return mapper.toResponseDto(
                transaction
        );

    }

    @Override
    public List<TransactionHistoryResponseDto>
    getTransactionsByUser(
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