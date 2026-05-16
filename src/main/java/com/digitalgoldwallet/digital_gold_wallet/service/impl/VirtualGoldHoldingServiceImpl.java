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

import java.util.List;

@Service
public class VirtualGoldHoldingServiceImpl
        implements VirtualGoldHoldingService {

    private final
    VirtualGoldHoldingRepository
            holdingRepository;

    private final
    UserRepository
            userRepository;

    private final
    VendorBranchRepository
            branchRepository;

    private final
    VirtualGoldHoldingMapper
            mapper;

    public VirtualGoldHoldingServiceImpl(

            VirtualGoldHoldingRepository
                    holdingRepository,

            UserRepository
                    userRepository,

            VendorBranchRepository
                    branchRepository,

            VirtualGoldHoldingMapper
                    mapper
    ){

        this.holdingRepository=
                holdingRepository;

        this.userRepository=
                userRepository;

        this.branchRepository=
                branchRepository;

        this.mapper=mapper;
    }

    @Override
    public VirtualGoldHoldingResponseDto
    createHolding(
            VirtualGoldHoldingRequestDto dto
    ){

        User user=
                userRepository
                        .findById(
                                dto.getUserId()
                        )
                        .orElseThrow(
                                ()->
                                        new UserNotFoundException(
                                                "User not found"
                                        )
                        );

        VendorBranch branch=
                branchRepository
                        .findById(
                                dto.getBranchId()
                        )
                        .orElseThrow(
                                ()->
                                        new VendorBranchNotFoundException(
                                                "Branch not found"
                                        )
                        );

        VirtualGoldHolding holding=
                mapper.toEntity(
                        dto,
                        user,
                        branch
                );

        VirtualGoldHolding saved=
                holdingRepository.save(
                        holding
                );

        return mapper.toResponseDto(
                saved
        );
    }

    @Override
    public VirtualGoldHoldingResponseDto
    getHoldingById(
            Integer id
    ){

        VirtualGoldHolding holding=
                holdingRepository
                        .findById(id)
                        .orElseThrow(
                                ()->
                                        new VirtualGoldHoldingNotFoundException(
                                                "Holding not found"
                                        )
                        );

        return mapper
                .toResponseDto(
                        holding
                );
    }

    @Override
    public List<VirtualGoldHoldingResponseDto>
    getHoldingsByUser(
            Integer userId
    ){

        return holdingRepository
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