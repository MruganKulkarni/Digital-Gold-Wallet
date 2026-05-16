package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.exception.AddressNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;

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

    /*
     * Constructor Injection
     */
    public UserServiceImpl(
            UserRepository userRepository,
            AddressRepository addressRepository) {

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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
         * Create User entity
         */
        User user = new User();

        user.setName(requestDto.getName());

        user.setEmail(requestDto.getEmail());

        user.setBalance(requestDto.getBalance());

        user.setAddress(address);

        /*
         * Save user
         */
        User savedUser =
                userRepository.save(user);

        /*
         * Convert Entity -> DTO
         */
        return mapToResponse(savedUser);
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

        return mapToResponse(user);
    }

    /*
     * Get All Users
     */
    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
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

        User updatedUser =
                userRepository.save(existingUser);

        return mapToResponse(updatedUser);
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

    /*
     * Entity -> DTO Mapper
     */
    private UserResponseDto mapToResponse(
            User user) {

        return new UserResponseDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getBalance()
        );
    }
}