package com.digitalgoldwallet.digital_gold_wallet.service.impl;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;
import com.digitalgoldwallet.digital_gold_wallet.exception.UserNotFoundException;
import com.digitalgoldwallet.digital_gold_wallet.repository.UserRepository;
import com.digitalgoldwallet.digital_gold_wallet.service.UserService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/*
 * User Service Implementation
 */

@Service
public class UserServiceImpl implements UserService {

    /*
     * Repository object
     */
    private final UserRepository userRepository;

    /*
     * Constructor Injection
     */
    public UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /*
     * CREATE USER
     */
    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }

    /*
     * GET USER BY ID
     */
    @Override
    public User getUserById(Integer userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with ID: " + userId
                        ));
    }

    /*
     * GET ALL USERS
     */
    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    /*
     * UPDATE USER
     */
    @Override
    public User updateUser(Integer userId, User updatedUser) {

        User existingUser = getUserById(userId);

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setBalance(updatedUser.getBalance());

        return userRepository.save(existingUser);
    }

    /*
     * DELETE USER
     */
    @Override
    public void deleteUser(Integer userId) {

        User user = getUserById(userId);

        userRepository.delete(user);
    }

    /*
     * GET USER BALANCE
     */
    @Override
    public BigDecimal getUserBalance(Integer userId) {

        User user = getUserById(userId);

        return user.getBalance();
    }
}