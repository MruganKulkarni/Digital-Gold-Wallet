package com.digitalgoldwallet.digital_gold_wallet.controller;

// importing request DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;

// importing response DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

// importing service layer
import com.digitalgoldwallet.digital_gold_wallet.service.UserService;

// validation annotation
import jakarta.validation.Valid;

// ResponseEntity used for HTTP response handling
import org.springframework.http.ResponseEntity;

// REST API annotations
import org.springframework.web.bind.annotation.*;

// BigDecimal for balance response
import java.math.BigDecimal;

// List for getAllUsers
import java.util.List;

/*
 * ============================================================
 * User REST Controller
 * ============================================================
 *
 * Handles all HTTP requests related to User module.
 *
 * Base URL:
 * /api/v1/users
 * ============================================================
 */

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    /*
     * Service layer injection
     */
    private final UserService userService;

    /*
     * Constructor injection
     */
    public UserController(UserService userService) {

        this.userService = userService;
    }

    /*
     * ============================================================
     * CREATE USER API
     * ============================================================
     *
     * POST /api/v1/users
     */
    @PostMapping
    public ResponseEntity<UserResponseDto>
    createUser(
            @Valid
            @RequestBody
            UserRequestDto requestDto) {

        return ResponseEntity.ok(
                userService.createUser(requestDto)
        );
    }

    /*
     * ============================================================
     * GET USER BY ID API
     * ============================================================
     *
     * GET /api/v1/users/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto>
    getUserById(
            @PathVariable
            Integer userId) {

        return ResponseEntity.ok(
                userService.getUserById(userId)
        );
    }

    /*
     * ============================================================
     * GET ALL USERS API
     * ============================================================
     *
     * GET /api/v1/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>>
    getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    /*
     * ============================================================
     * UPDATE USER API
     * ============================================================
     *
     * PUT /api/v1/users/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto>
    updateUser(
            @PathVariable
            Integer userId,

            @Valid
            @RequestBody
            UserRequestDto requestDto) {

        return ResponseEntity.ok(
                userService.updateUser(
                        userId,
                        requestDto
                )
        );
    }

    /*
     * ============================================================
     * DELETE USER API
     * ============================================================
     *
     * DELETE /api/v1/users/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String>
    deleteUser(
            @PathVariable
            Integer userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }

    /*
     * ============================================================
     * GET USER BALANCE API
     * ============================================================
     *
     * GET /api/v1/users/{userId}/balance
     */
    @GetMapping("/{userId}/balance")
    public ResponseEntity<BigDecimal>
    getUserBalance(
            @PathVariable
            Integer userId) {

        return ResponseEntity.ok(
                userService.getUserBalance(userId)
        );
    }
}