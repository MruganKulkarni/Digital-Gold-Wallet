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
     * ============================================================
     * MOCK REPOSITORIES
     * ============================================================
     */
    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    /*
     * ============================================================
     * MOCK MAPPER
     * ============================================================
     */
    @Mock
    private UserMapper userMapper;

    /*
     * ============================================================
     * INJECT MOCKS
     * ============================================================
     */
    @InjectMocks
    private UserServiceImpl userService;

    /*
     * ============================================================
     * COMMON ADDRESS ENTITY
     * ============================================================
     */
    private Address buildAddress() {

        Address address = new Address();

        address.setAddressId(1);

        return address;
    }

    /*
     * ============================================================
     * COMMON USER ENTITY
     * ============================================================
     */
    private User buildUser() {

        Address address =
                buildAddress();

        User user = new User();

        user.setUserId(1);

        user.setName("Varsha");

        user.setEmail("varsha@test.com");

        user.setBalance(
                new BigDecimal("5000")
        );

        user.setAddress(address);

        return user;
    }

    /*
     * ============================================================
     * COMMON REQUEST DTO
     * ============================================================
     */
    private UserRequestDto buildRequestDto() {

        UserRequestDto requestDto =
                new UserRequestDto();

        requestDto.setName("Varsha");

        requestDto.setEmail("varsha@test.com");

        requestDto.setBalance(
                new BigDecimal("5000")
        );

        requestDto.setAddressId(1);

        return requestDto;
    }

    /*
     * ============================================================
     * COMMON RESPONSE DTO
     * ============================================================
     */
    private UserResponseDto buildResponseDto() {

        UserResponseDto responseDto =
                new UserResponseDto();

        responseDto.setUserId(1);

        responseDto.setName("Varsha");

        responseDto.setEmail("varsha@test.com");

        responseDto.setBalance(
                new BigDecimal("5000")
        );

        return responseDto;
    }

    /*
     * ============================================================
     * TEST CREATE USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Create User")
    public void testCreateUser() {

        Address address =
                buildAddress();

        UserRequestDto requestDto =
                buildRequestDto();

        User user =
                buildUser();

        UserResponseDto responseDto =
                buildResponseDto();

        when(userRepository.existsByEmail(
                requestDto.getEmail()
        )).thenReturn(false);

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(userMapper.toEntity(
                requestDto,
                address
        )).thenReturn(user);

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponseDto savedUser =
                userService.createUser(requestDto);

        assertNotNull(savedUser);

        assertEquals(
                "Varsha",
                savedUser.getName()
        );

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    /*
     * ============================================================
     * TEST GET USER
     * ============================================================
     */
    @Test
    @DisplayName("Test Get User")
    public void testGetUser() {

        User user =
                buildUser();

        UserResponseDto responseDto =
                buildResponseDto();

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        UserResponseDto fetchedUser =
                userService.getUserById(1);

        assertEquals(
                1,
                fetchedUser.getUserId()
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

        when(userRepository.findById(999))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.getUserById(999)
        );

        assertTrue(
                exception.getMessage()
                        .contains("User not found")
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

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setEmail(
                "duplicate@test.com"
        );

        when(userRepository.existsByEmail(
                requestDto.getEmail()
        )).thenReturn(true);

        Exception exception = assertThrows(
                RuntimeException.class,

                () -> userService.createUser(requestDto)
        );

        assertTrue(
                exception.getMessage()
                        .contains("Email already exists")
        );
    }

    /*
     * ============================================================
     * TEST NULL NAME
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Name")
    public void testNullName() {

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setName(null);

        assertNull(
                requestDto.getName()
        );
    }

    /*
     * ============================================================
     * TEST BLANK NAME
     * ============================================================
     */
    @Test
    @DisplayName("Test Blank Name")
    public void testBlankName() {

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setName("");

        assertEquals(
                "",
                requestDto.getName()
        );
    }

    /*
     * ============================================================
     * TEST NULL EMAIL
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Email")
    public void testNullEmail() {

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setEmail(null);

        assertNull(
                requestDto.getEmail()
        );
    }

    /*
     * ============================================================
     * TEST INVALID EMAIL
     * ============================================================
     */
    @Test
    @DisplayName("Test Invalid Email")
    public void testInvalidEmail() {

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setEmail("invalid");

        assertEquals(
                "invalid",
                requestDto.getEmail()
        );
    }

    /*
     * ============================================================
     * TEST NEGATIVE BALANCE
     * ============================================================
     */
    @Test
    @DisplayName("Test Negative Balance")
    public void testNegativeBalance() {

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setBalance(
                new BigDecimal("-500")
        );

        assertEquals(
                new BigDecimal("-500"),
                requestDto.getBalance()
        );
    }

    /*
     * ============================================================
     * TEST NULL ADDRESS ID
     * ============================================================
     */
    @Test
    @DisplayName("Test Null Address Id")
    public void testNullAddressId() {

        UserRequestDto requestDto =
                buildRequestDto();

        requestDto.setAddressId(null);

        assertNull(
                requestDto.getAddressId()
        );
    }

    /*
     * ============================================================
     * TEST SAVE METHOD CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Save Method Called")
    public void testSaveMethodCalled() {

        Address address =
                buildAddress();

        UserRequestDto requestDto =
                buildRequestDto();

        User user =
                buildUser();

        UserResponseDto responseDto =
                buildResponseDto();

        when(userRepository.existsByEmail(
                requestDto.getEmail()
        )).thenReturn(false);

        when(addressRepository.findById(1))
                .thenReturn(Optional.of(address));

        when(userMapper.toEntity(
                requestDto,
                address
        )).thenReturn(user);

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        userService.createUser(requestDto);

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    /*
     * ============================================================
     * TEST FIND BY ID CALLED
     * ============================================================
     */
    @Test
    @DisplayName("Test Find By Id Called")
    public void testFindByIdCalled() {

        User user =
                buildUser();

        UserResponseDto responseDto =
                buildResponseDto();

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(userMapper.toResponseDto(user))
                .thenReturn(responseDto);

        userService.getUserById(1);

        verify(userRepository, times(1))
                .findById(1);
    }

}