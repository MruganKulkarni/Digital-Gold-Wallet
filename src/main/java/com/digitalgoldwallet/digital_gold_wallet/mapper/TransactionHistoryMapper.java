package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.TransactionHistoryRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.TransactionHistoryResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.TransactionHistory;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import org.springframework.stereotype.Component;

/*
 * ============================================================
 * TransactionHistory Mapper
 * ============================================================
 */

@Component
public class TransactionHistoryMapper {

    /*
     * DTO -> Entity
     */

    public TransactionHistory toEntity(

            TransactionHistoryRequestDto dto,

            User user,

            VendorBranch branch
    ){

        TransactionHistory transaction=
                new TransactionHistory();

        transaction.setUser(user);

        transaction.setBranch(branch);

        transaction.setTransactionType(
                dto.getTransactionType()
        );

        transaction.setTransactionStatus(
                dto.getTransactionStatus()
        );

        transaction.setQuantity(
                dto.getQuantity()
        );

        transaction.setAmount(
                dto.getAmount()
        );

        return transaction;

    }

    /*
     * Entity -> DTO
     */

    public TransactionHistoryResponseDto
    toResponseDto(
            TransactionHistory transaction
    ){

        return new TransactionHistoryResponseDto(

                transaction.getTransactionId(),

                transaction.getUser()
                        .getUserId(),

                transaction.getBranch()
                        .getBranchId(),

                transaction.getTransactionType(),

                transaction.getTransactionStatus(),

                transaction.getQuantity(),

                transaction.getAmount(),

                transaction.getCreatedAt()

        );

    }

}