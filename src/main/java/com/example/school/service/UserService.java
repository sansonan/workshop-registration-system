package com.example.school.service;

import com.example.school.entity.User;
import com.example.school.security.AuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AuthUser> findUserByUsername(String username);
    User createUser(User user);
    User findById(Long id);
    List<User> findAll();
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    User approveUser(Long userId);
    User rejectUser(Long userId);

    Page<User> findAll(Pageable pageable);

    User getUserByEmail(String email);

}

