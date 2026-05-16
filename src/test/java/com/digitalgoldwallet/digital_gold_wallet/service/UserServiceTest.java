package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import com.digitalgoldwallet.digital_gold_wallet.repository.AddressRepository;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/*
 * Service Layer Testing
 */

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * CREATE USER TEST
     */
    @Test
    public void testCreateUserService() {

        Address address = new Address();

        address.setStreet("Service Street");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setPostalCode("600001");
        address.setCountry("India");

        Address savedAddress =
                addressRepository.save(address);

        User user = new User();

        user.setName("Varsha");

        user.setEmail(
                "service"
                        + System.currentTimeMillis()
                        + "@test.com"
        );

        user.setBalance(new BigDecimal("5000"));

        user.setAddress(savedAddress);

        User savedUser = userService.createUser(user);

        Assertions.assertNotNull(savedUser.getUserId());

        System.out.println("SERVICE CREATE TEST PASSED");
    }

    /*
     * GET USER TEST
     */
    @Test
    public void testGetUserService() {

        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        Assertions.assertNotNull(user);

        User foundUser =
                userService.getUserById(user.getUserId());

        Assertions.assertEquals(
                user.getUserId(),
                foundUser.getUserId()
        );

        System.out.println("SERVICE READ TEST PASSED");
    }
}