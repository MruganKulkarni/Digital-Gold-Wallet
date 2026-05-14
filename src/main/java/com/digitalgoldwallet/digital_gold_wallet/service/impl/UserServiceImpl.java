package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/*
 * Business logic implementation for User module
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Repository injection
    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    /*
     * Create new user
     */
    @Override
    public UserResponseDto createUser(UserRequestDto requestDto) {

        // Check duplicate email
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Fetch address
        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // Build user object
        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .address(address)
                .balance(BigDecimal.ZERO)
                .build();

        // Save user
        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    /*
     * Fetch user by ID
     */
    @Override
    public UserResponseDto getUserById(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    /*
     * Fetch all users
     */
    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /*
     * Update existing user
     */
    @Override
    public UserResponseDto updateUser(Integer userId, UserRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(requestDto.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setAddress(address);

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    /*
     * Fetch wallet balance
     */
    @Override
    public BigDecimal getUserBalance(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getBalance();
    }

    /*
     * Convert User entity into DTO
     */
    private UserResponseDto mapToResponse(User user) {

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .balance(user.getBalance())
                .build();
    }
}