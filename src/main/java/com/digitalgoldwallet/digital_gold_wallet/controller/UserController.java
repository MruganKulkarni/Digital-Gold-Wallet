package com.digitalgoldwallet.digital_gold_wallet.controller;

// importing request DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;

// importing response DTO
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

// importing service layer
import com.digitalgoldwallet.digital_gold_wallet.service.UserService;

// validation annotations
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

// enables validation on path variables
import org.springframework.validation.annotation.Validated;

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
 *
 * Validation:
 * - Request body validation using @Valid
 * - Path variable validation using @Min
 *
 * ============================================================
 */

@RestController

@Validated

@RequestMapping("/api/v1/users")

public class UserController {

    /*
     * Service layer injection
     */
    private final UserService userService;


    /*
     * Constructor injection
     */
    public UserController(
            UserService userService
    ) {

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

            UserRequestDto requestDto
    ) {

        return ResponseEntity.ok(

                userService.createUser(
                        requestDto
                )

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

            @Min(
                    value=1,
                    message="User ID must be positive"
            )

            Integer userId
    ) {

        return ResponseEntity.ok(

                userService.getUserById(
                        userId
                )

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

            @Min(
                    value=1,
                    message="User ID must be positive"
            )

            Integer userId,

            @Valid

            @RequestBody

            UserRequestDto requestDto
    ) {

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

    public ResponseEntity<?>
    deleteUser(

            @PathVariable

            @Min(
                    value=1,
                    message="User ID must be positive"
            )

            Integer userId
    ) {

        /*
         * Service returns deleted user details
         */
        UserResponseDto deletedUser =

                userService.deleteUser(
                        userId
                );

        /*
         * Return success message + deleted details
         */
        return ResponseEntity.ok(

                java.util.Map.of(

                        "message",
                        "User deleted successfully",

                        "deletedUser",
                        deletedUser
                )
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

            @Min(
                    value=1,
                    message="User ID must be positive"
            )

            Integer userId
    ) {

        return ResponseEntity.ok(

                userService.getUserBalance(
                        userId
                )

        );

    }

}