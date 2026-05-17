package com.digitalgoldwallet.digital_gold_wallet.mapper;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import org.springframework.stereotype.Component;

/*
 * ============================================================
 * User Mapper for entity,dto and service connection.
 * ============================================================
 *
 * Handles conversion between:
 *
 * DTO <-> Entity
 * ============================================================
 */

@Component
public class UserMapper {

    /*
     * Request DTO -> Entity
     */
    public User toEntity(
            UserRequestDto requestDto,
            Address address) {

        User user = new User();

        user.setName(requestDto.getName());

        user.setEmail(requestDto.getEmail());

        user.setBalance(requestDto.getBalance());

        user.setAddress(address);

        return user;
    }

    /*
     * Entity -> Response DTO
     */
    public UserResponseDto toResponseDto(
            User user) {

        return new UserResponseDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getBalance()
        );
    }

    /*
     * Update Existing Entity
     */
    public void updateEntity(
            User existingUser,
            UserRequestDto requestDto,
            Address address) {

        existingUser.setName(
                requestDto.getName()
        );

        existingUser.setEmail(
                requestDto.getEmail()
        );

        existingUser.setBalance(
                requestDto.getBalance()
        );

        existingUser.setAddress(address);
    }
}