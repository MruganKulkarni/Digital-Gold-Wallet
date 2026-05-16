package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;

import com.digitalgoldwallet.digital_gold_wallet.mapper.UserMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.UserService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/*
 * ============================================================
 * User Service Implementation
 * ============================================================
 */

@Service
public class UserServiceImpl implements UserService {

    /*
     * Repository Injection
     */
    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final UserMapper userMapper;

    /*
     * Constructor Injection
     */
    public UserServiceImpl(
            UserRepository userRepository,
            AddressRepository addressRepository,
            UserMapper userMapper) {

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
    }

    /*
     * Create User
     */
    @Override
    public UserResponseDto createUser(
            UserRequestDto requestDto) {

        /*
         * Check duplicate email
         */
        if (userRepository.existsByEmail(
                requestDto.getEmail())) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        /*
         * Fetch Address
         */
        Address address =
                addressRepository.findById(
                        requestDto.getAddressId()
                ).orElseThrow(() ->
                        new AddressNotFoundException(
                                "Address not found"
                        ));

        /*
         * Convert DTO -> Entity
         */
        User user = userMapper.toEntity(
                requestDto,
                address
        );

        /*
         * Save user
         */
        User savedUser =
                userRepository.save(user);

        /*
         * Convert Entity -> DTO
         */
        return userMapper.toResponseDto(savedUser);
    }

    /*
     * Get User By ID
     */
    @Override
    public UserResponseDto getUserById(
            Integer userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found with ID: "
                                                + userId
                                ));

        return userMapper.toResponseDto(user);
    }

    /*
     * Get All Users
     */
    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    /*
     * Update User
     */
    @Override
    public UserResponseDto updateUser(
            Integer userId,
            UserRequestDto requestDto) {

        User existingUser =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
                                ));

        Address address =
                addressRepository.findById(
                        requestDto.getAddressId()
                ).orElseThrow(() ->
                        new AddressNotFoundException(
                                "Address not found"
                        ));

        /*
         * Update entity using mapper
         */
        userMapper.updateEntity(
                existingUser,
                requestDto,
                address
        );

        User updatedUser =
                userRepository.save(existingUser);

        return userMapper.toResponseDto(updatedUser);
    }

    /*
     * Delete User
     */
    @Override
    public void deleteUser(Integer userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
                                ));

        userRepository.delete(user);
    }

    /*
     * Get User Balance
     */
    @Override
    public BigDecimal getUserBalance(
            Integer userId) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"
                                ));

        return user.getBalance();
    }
}