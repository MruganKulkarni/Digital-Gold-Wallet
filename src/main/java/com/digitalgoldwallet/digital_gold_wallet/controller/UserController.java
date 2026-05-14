package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/*
 * REST Controller for User APIs
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    // Injecting service layer
    private final UserService userService;

    /*
     * Create new user
     * POST /api/v1/users
     */
    @PostMapping
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto requestDto
    ) {
        return userService.createUser(requestDto);
    }

    /*
     * Get user details
     * GET /api/v1/users/{userId}
     */
    @GetMapping("/{userId}")
    public UserResponseDto getUserById(
            @PathVariable Integer userId
    ) {
        return userService.getUserById(userId);
    }

    /*
     * Fetch all users
     * GET /api/v1/users
     */
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /*
     * Update user
     * PUT /api/v1/users/{userId}
     */
    @PutMapping("/{userId}")
    public UserResponseDto updateUser(
            @PathVariable Integer userId,
            @Valid @RequestBody UserRequestDto requestDto
    ) {
        return userService.updateUser(userId, requestDto);
    }

    /*
     * Get wallet balance
     * GET /api/v1/users/{userId}/balance
     */
    @GetMapping("/{userId}/balance")
    public BigDecimal getUserBalance(
            @PathVariable Integer userId
    ) {
        return userService.getUserBalance(userId);
    }
}