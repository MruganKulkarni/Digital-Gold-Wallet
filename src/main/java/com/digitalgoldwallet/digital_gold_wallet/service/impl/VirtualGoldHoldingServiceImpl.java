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
     * Buy virtual gold
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
     * Sell gold
     */
    @Override
    public VirtualGoldHoldingResponseDto sellGold(
            VirtualGoldHoldingRequestDto dto
    ) {

        return buyGold(dto);
    }


    /*
     * Existing Day-3 method
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
     * User holdings
     */
    @Override
    public List<VirtualGoldHoldingResponseDto>
    getHoldingsByUser(
            Integer userId
    ) {

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
     * Branch holdings
     */
    @Override
    public List<VirtualGoldHoldingResponseDto>
    getHoldingsByBranch(
            Integer branchId
    ) {

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