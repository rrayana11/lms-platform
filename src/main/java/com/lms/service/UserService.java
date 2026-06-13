package com.lms.service;

import com.lms.domain.entity.User;
import com.lms.dto.RegistrationDto;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    void registerUser(RegistrationDto dto);
    User getUserByUsername(String username);
}