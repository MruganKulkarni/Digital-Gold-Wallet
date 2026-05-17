package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/*
 * ============================================================
 * User Service Layer Testing
 * ============================================================
 *
 * Covers:
 * - Positive Scenarios
 * - Negative Scenarios
 * - Exception Testing
 * - CRUD Operations
 * ============================================================
 */

@SpringBootTest
public class UserServiceTest {

    /*
     * Service Injection
     */
    @Autowired
    private UserService userService;

    /*
     * Repository Injection
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    /*
     * Test Data
     */
    private Address testAddress;

    private Integer testUserId;

    /*
     * ============================================================
     * Setup Test Data
     * ============================================================
     */
    @BeforeEach
    public void setUp() {

        /*
         * Create Address
         */
        testAddress = new Address();

        testAddress.setStreet("Anna Nagar");

        testAddress.setCity("Chennai");

        testAddress.setState("Tamil Nadu");

        testAddress.setPostalCode(
                "600040"
                        + System.currentTimeMillis()
        );

        testAddress.setCountry("India");

        /*
         * Save Address
         */
        testAddress =
                addressRepository.save(testAddress);
    }

    /*
     * ============================================================
     * Cleanup Test Data
     * ============================================================
     */
    @AfterEach
    public void tearDown() {

        /*
         * Delete User
         */
        if (testUserId != null
                && userRepository.existsById(testUserId)) {

            userRepository.deleteById(testUserId);
        }

        /*
         * Delete Address
         */
        if (testAddress != null
                && testAddress.getAddressId() != null
                && addressRepository.existsById(
                testAddress.getAddressId())) {

            addressRepository.deleteById(
                    testAddress.getAddressId()
            );
        }
    }

    /*
     * ============================================================
     * TEST CREATE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Create User Service")
    public void testCreateUserService() {

        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName(
                "Varsha Karthikeyan"
        );

        requestDto.setEmail(
                "service"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        requestDto.setBalance(
                new BigDecimal("5000.00")
        );

        requestDto.setAddressId(
                testAddress.getAddressId()
        );

        UserResponseDto responseDto =
                userService.createUser(requestDto);

        testUserId =
                responseDto.getUserId();

        assertNotNull(
                responseDto.getUserId()
        );

        assertEquals(
                "Varsha Karthikeyan",
                responseDto.getName()
        );

        System.out.println(
                "CREATE USER SERVICE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST GET USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Get User Service")
    public void testGetUserService() {

        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName("Test User");

        requestDto.setEmail(
                "getuser"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        requestDto.setBalance(
                new BigDecimal("3000.00")
        );

        requestDto.setAddressId(
                testAddress.getAddressId()
        );

        UserResponseDto createdUser =
                userService.createUser(requestDto);

        testUserId =
                createdUser.getUserId();

        UserResponseDto fetchedUser =
                userService.getUserById(
                        testUserId
                );

        assertEquals(
                testUserId,
                fetchedUser.getUserId()
        );

        System.out.println(
                "GET USER SERVICE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST UPDATE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Update User Service")
    public void testUpdateUserService() {

        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName("Old Name");

        requestDto.setEmail(
                "update"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        requestDto.setBalance(
                new BigDecimal("1000.00")
        );

        requestDto.setAddressId(
                testAddress.getAddressId()
        );

        UserResponseDto createdUser =
                userService.createUser(requestDto);

        testUserId =
                createdUser.getUserId();

        UserRequestDto updateDto =
                new UserRequestDto();

        updateDto.setName("Updated Name");

        updateDto.setEmail(
                createdUser.getEmail()
        );

        updateDto.setBalance(
                new BigDecimal("9000.00")
        );

        updateDto.setAddressId(
                testAddress.getAddressId()
        );

        UserResponseDto updatedUser =
                userService.updateUser(
                        testUserId,
                        updateDto
                );

        assertEquals(
                "Updated Name",
                updatedUser.getName()
        );

        assertEquals(
                new BigDecimal("9000.00"),
                updatedUser.getBalance()
        );

        System.out.println(
                "UPDATE USER SERVICE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST DELETE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Delete User Service")
    public void testDeleteUserService() {

        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName("Delete User");

        requestDto.setEmail(
                "delete"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        requestDto.setBalance(
                new BigDecimal("5000.00")
        );

        requestDto.setAddressId(
                testAddress.getAddressId()
        );

        UserResponseDto createdUser =
                userService.createUser(requestDto);

        Integer userId =
                createdUser.getUserId();

        userService.deleteUser(userId);

        boolean exists =
                userRepository.existsById(userId);

        assertFalse(exists);

        System.out.println(
                "DELETE USER SERVICE TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST USER NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test User Not Found Exception")
    public void testUserNotFound() {

        Integer invalidUserId = 999999;

        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.getUserById(
                        invalidUserId
                )
        );

        assertTrue(
                exception.getMessage()
                        .contains("User not found")
        );

        System.out.println(
                "USER NOT FOUND TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST DUPLICATE EMAIL
     * ============================================================
     */
    @Test
    @DisplayName("Test Duplicate Email Exception")
    public void testDuplicateEmail() {

        UserRequestDto firstUser =
                new UserRequestDto();

        firstUser.setName("Varsha");

        firstUser.setEmail(
                "duplicate@test.com"
        );

        firstUser.setBalance(
                new BigDecimal("5000")
        );

        firstUser.setAddressId(
                testAddress.getAddressId()
        );

        UserResponseDto createdUser =
                userService.createUser(firstUser);

        testUserId =
                createdUser.getUserId();

        UserRequestDto secondUser =
                new UserRequestDto();

        secondUser.setName("Another User");

        secondUser.setEmail(
                "duplicate@test.com"
        );

        secondUser.setBalance(
                new BigDecimal("7000")
        );

        secondUser.setAddressId(
                testAddress.getAddressId()
        );

        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.createUser(
                        secondUser
                )
        );

        assertTrue(
                exception.getMessage()
                        .contains("Email already exists")
        );

        System.out.println(
                "DUPLICATE EMAIL TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST DELETE USER NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test Delete User Not Found")
    public void testDeleteUserNotFound() {

        Integer invalidUserId = 888888;

        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.deleteUser(
                        invalidUserId
                )
        );

        assertTrue(
                exception.getMessage()
                        .contains("User not found")
        );

        System.out.println(
                "DELETE USER NOT FOUND TEST PASSED"
        );
    }
}