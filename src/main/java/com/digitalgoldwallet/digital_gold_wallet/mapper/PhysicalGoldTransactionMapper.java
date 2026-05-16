package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.PhysicalGoldTransactionRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.PhysicalGoldTransactionResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.PhysicalGoldTransaction;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import org.springframework.stereotype.Component;

/*
 * ============================================================
 * Physical Gold Mapper
 * ============================================================
 */

@Component
public class PhysicalGoldTransactionMapper {

    public PhysicalGoldTransaction
    toEntity(

            PhysicalGoldTransactionRequestDto dto,

            User user,

            VendorBranch branch,

            Address address
    ){

        PhysicalGoldTransaction
                transaction=
                new PhysicalGoldTransaction();

        transaction.setUser(
                user
        );

        transaction.setBranch(
                branch
        );

        transaction.setDeliveryAddress(
                address
        );

        transaction.setQuantity(
                dto.getQuantity()
        );

        return transaction;

    }

    public
    PhysicalGoldTransactionResponseDto
    toResponseDto(
            PhysicalGoldTransaction transaction
    ){

        return new
                PhysicalGoldTransactionResponseDto(

                transaction.getTransactionId(),

                transaction.getUser()
                        .getUserId(),

                transaction.getBranch()
                        .getBranchId(),

                transaction
                        .getDeliveryAddress()
                        .getAddressId(),

                transaction.getQuantity(),

                transaction.getCreatedAt()

        );

    }

}