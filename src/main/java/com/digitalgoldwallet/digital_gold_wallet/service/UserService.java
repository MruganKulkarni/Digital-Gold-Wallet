package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import java.math.BigDecimal;
import java.util.List;

/*
 * Service interface for User module
 */
public interface UserService {

    UserResponseDto createUser(UserRequestDto requestDto);

    UserResponseDto getUserById(Integer userId);

    List<UserResponseDto> getAllUsers();

    UserResponseDto updateUser(Integer userId, UserRequestDto requestDto);

    BigDecimal getUserBalance(Integer userId);
}
