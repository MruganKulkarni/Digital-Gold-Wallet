package com.digitalgoldwallet.digital_gold_wallet.service;

import com.digitalgoldwallet.digital_gold_wallet.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    User createUser(User user);

    User getUserById(Integer userId);

    List<User> getAllUsers();

    User updateUser(Integer userId, User user);

    void deleteUser(Integer userId);

    BigDecimal getUserBalance(Integer userId);
}