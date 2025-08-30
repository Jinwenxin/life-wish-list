package org.jinwenxin.lifewishlist.service;

import org.jinwenxin.lifewishlist.model.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User updateUser(User user);
    void deleteUser(Long id);
}