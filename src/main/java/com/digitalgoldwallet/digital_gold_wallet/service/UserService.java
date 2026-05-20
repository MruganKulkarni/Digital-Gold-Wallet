package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.util.List;

/*
 * ============================================================
 * User Service Interface
 * ============================================================
 *
 * Defines all business operations for User module.
 * ============================================================
 */

public interface UserService {

    /*
     * Create new user
     */
    UserResponseDto createUser(
            UserRequestDto requestDto
    );

    /*
     * Get user by ID
     */
    UserResponseDto getUserById(
            Integer userId
    );

    /*
     * Get all users
     */
    List<UserResponseDto> getAllUsers();

    /*
     * Update user
     */
    UserResponseDto updateUser(
            Integer userId,
            UserRequestDto requestDto
    );

    /*
     * Delete user and return deleted details
     */
    UserResponseDto deleteUser(
            Integer userId
    );

    /*
     * Get user balance
     */
    BigDecimal getUserBalance(
            Integer userId
    );
}