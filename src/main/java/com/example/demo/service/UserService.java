package com.example.demo.service;


import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    User findById(long id);
}
