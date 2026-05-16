package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.VirtualGoldHoldingRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.VirtualGoldHoldingResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;
import com.digitalgoldwallet.digital_gold_wallet.entity.VirtualGoldHolding;

import org.springframework.stereotype.Component;

/*
 * ============================================================
 * VirtualGoldHolding Mapper
 * ============================================================
 */

@Component
public class VirtualGoldHoldingMapper {

    /*
     * DTO → Entity
     */
    public VirtualGoldHolding
    toEntity(
            VirtualGoldHoldingRequestDto dto,
            User user,
            VendorBranch branch
    ){

        VirtualGoldHolding holding=
                new VirtualGoldHolding();

        holding.setUser(user);

        holding.setBranch(branch);

        holding.setQuantity(
                dto.getQuantity()
        );

        return holding;
    }

    /*
     * Entity → DTO
     */

    public VirtualGoldHoldingResponseDto
    toResponseDto(
            VirtualGoldHolding holding
    ){

        return new VirtualGoldHoldingResponseDto(

                holding.getHoldingId(),

                holding.getUser()
                        .getUserId(),

                holding.getBranch()
                        .getBranchId(),

                holding.getQuantity(),

                holding.getCreatedAt()
        );
    }
}