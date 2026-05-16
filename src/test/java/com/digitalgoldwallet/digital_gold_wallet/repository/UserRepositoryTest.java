package com.digitalgoldwallet.digital_gold_wallet.repository;

import com.digitalgoldwallet.digital_gold_wallet.entity.Address;
import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/*
 * JUnit Test Class
 *
 * Tests complete CRUD operations
 * for User Repository.
 */

@SpringBootTest
public class UserRepositoryTest {

    /*
     * Injecting repositories
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    /*
     * CREATE TEST
     */
    @Test
    public void testCreateUser() {

        // creating address object
        Address address = new Address();

        address.setStreet("Anna Nagar");
        address.setCity("Chennai");
        address.setState("Tamil Nadu");
        address.setPostalCode("600040");
        address.setCountry("India");

        // saving address
        Address savedAddress = addressRepository.save(address);

        // creating user object
        User user = new User();

        user.setName("Varsha");

        // unique email for every test run
        user.setEmail("varsha"
                + System.currentTimeMillis()
                + "@test.com");

        user.setBalance(new BigDecimal("5000"));

        user.setAddress(savedAddress);

        // saving user
        User savedUser = userRepository.save(user);

        // assertion
        Assertions.assertNotNull(savedUser.getUserId());

        System.out.println("CREATE TEST PASSED");
    }

    /*
     * READ TEST
     */
    @Test
    public void testReadUser() {

        // fetching first available user
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        // assertion
        Assertions.assertNotNull(user);

        System.out.println("READ TEST PASSED");
    }

    /*
     * UPDATE TEST
     */
    @Test
    public void testUpdateUser() {

        // fetching first available user
        User user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        if (user != null) {

            user.setBalance(new BigDecimal("10000"));

            // updating user
            User updatedUser = userRepository.save(user);

            // assertion
            Assertions.assertEquals(
                    new BigDecimal("10000"),
                    updatedUser.getBalance()
            );

            System.out.println("UPDATE TEST PASSED");

        } else {

            System.out.println("UPDATE TEST FAILED: User is null");
        }
    }

    /*
     * CUSTOM QUERY TEST
     */
    @Test
    public void testCustomQuery() {

        List<User> users =
                userRepository.findUsersWithBalanceGreaterThan(
                        new BigDecimal("1000"));

        // assertion
        Assertions.assertFalse(users.isEmpty());

        System.out.println("CUSTOM QUERY TEST PASSED");
    }

    /*
     * DELETE TEST
     */
    @Test
    public void testDeleteUser() {

        // create fresh address
        Address address = new Address();

        address.setStreet("Delete Street");
        address.setCity("Delete City");
        address.setState("Delete State");
        address.setPostalCode("600001");
        address.setCountry("India");

        // save address
        Address savedAddress = addressRepository.save(address);

        // create fresh user
        User user = new User();

        user.setName("Delete Test User");

        // unique email for every run
        user.setEmail("delete"
                + System.currentTimeMillis()
                + "@test.com");

        user.setBalance(new BigDecimal("3000"));

        user.setAddress(savedAddress);

        // save user
        User savedUser = userRepository.save(user);

        // get saved user id
        Integer userId = savedUser.getUserId();

        // delete user
        userRepository.deleteById(userId);

        // verify deletion
        boolean exists = userRepository.existsById(userId);

        // assertion
        Assertions.assertFalse(exists);

        System.out.println("DELETE TEST PASSED");
    }
}