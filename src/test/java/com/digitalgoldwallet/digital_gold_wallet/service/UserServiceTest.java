package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.mapper.UserMapper;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.impl.UserServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

/*
 * ============================================================
 * Mockito-Based User Service Testing
 * ============================================================
 */

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    /*
     * Mock Repository
     */
    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    /*
     * Mock Mapper
     */
    @Mock
    private UserMapper userMapper;

    /*
     * Inject mocks into service
     */
    @InjectMocks
    private UserServiceImpl userService;

    /*
     * ============================================================
     * TEST CREATE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Create User")
    public void testCreateUser() {

        /*
         * Address Entity
         */
        Address address = new Address();

        address.setAddressId(1);

        /*
         * Request DTO
         */
        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName("Varsha");

        requestDto.setEmail("varsha@test.com");

        requestDto.setBalance(
                new BigDecimal("5000")
        );

        requestDto.setAddressId(1);

        /*
         * User Entity
         */
        User user = new User();

        user.setUserId(1);

        user.setName("Varsha");

        user.setEmail("varsha@test.com");

        user.setBalance(
                new BigDecimal("5000")
        );

        user.setAddress(address);

        /*
         * Response DTO
         */
        UserResponseDto responseDto =
                new UserResponseDto();

        responseDto.setUserId(1);

        responseDto.setName("Varsha");

        responseDto.setEmail("varsha@test.com");

        responseDto.setBalance(
                new BigDecimal("5000")
        );

        /*
         * Mock Repository
         */
        when(userRepository.existsByEmail(
                requestDto.getEmail()
        )).thenReturn(false);

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        /*
         * Mock Mapper
         */
        when(userMapper.toEntity(
                requestDto,
                address
        ))
                .thenReturn(user);

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        /*
         * Mock Save
         */
        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        /*
         * Call Service
         */
        UserResponseDto savedUser =
                userService.createUser(requestDto);

        /*
         * Assertions
         */
        assertNotNull(savedUser);

        assertEquals(
                "Varsha",
                savedUser.getName()
        );

        /*
         * Verify save called once
         */
        verify(userRepository, times(1))
                .save(any(User.class));

        System.out.println(
                "CREATE USER TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST GET USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Get User")
    public void testGetUser() {

        /*
         * Address Entity
         */
        Address address = new Address();

        address.setAddressId(1);

        /*
         * User Entity
         */
        User user = new User();

        user.setUserId(1);

        user.setName("Varsha");

        user.setEmail("varsha@test.com");

        user.setBalance(
                new BigDecimal("5000")
        );

        user.setAddress(address);

        /*
         * Response DTO
         */
        UserResponseDto responseDto =
                new UserResponseDto();

        responseDto.setUserId(1);

        responseDto.setName("Varsha");

        responseDto.setEmail("varsha@test.com");

        responseDto.setBalance(
                new BigDecimal("5000")
        );

        /*
         * Mock Repository
         */
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        /*
         * Mock Mapper
         */
        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        /*
         * Call Service
         */
        UserResponseDto fetchedUser =
                userService.getUserById(1);

        /*
         * Assertions
         */
        assertEquals(
                1,
                fetchedUser.getUserId()
        );

        System.out.println(
                "GET USER TEST PASSED"
        );
    }

    /*
     * ============================================================
     * TEST USER NOT FOUND
     * ============================================================
     */
    @Test
    @DisplayName("Test User Not Found")
    public void testUserNotFound() {

        /*
         * Mock Empty Response
         */
        when(userRepository.findById(999))
                .thenReturn(Optional.empty());

        /*
         * Verify Exception
         */
        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.getUserById(999)
        );

        /*
         * Verify Message
         */
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
    @DisplayName("Test Duplicate Email")
    public void testDuplicateEmail() {

        /*
         * Request DTO
         */
        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName("Varsha");

        requestDto.setEmail(
                "duplicate@test.com"
        );

        requestDto.setBalance(
                new BigDecimal("5000")
        );

        requestDto.setAddressId(1);

        /*
         * Mock Duplicate Email
         */
        when(userRepository.existsByEmail(
                requestDto.getEmail()
        )).thenReturn(true);

        /*
         * Verify Exception
         */
        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.createUser(requestDto)
        );

        /*
         * Verify Message
         */
        assertTrue(
                exception.getMessage()
                        .contains("Email already exists")
        );

        System.out.println(
                "DUPLICATE EMAIL TEST PASSED"
        );
    }
}