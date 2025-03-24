//package com.example.school.service.impl;
//
//import com.example.school.entity.User;
//import com.example.school.repository.UserRepository;
//import com.example.school.security.AuthUser;
//import com.example.school.security.RoleEnum;
//import com.example.school.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceFakeImpl implements UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public Optional<AuthUser> findUserByUsername(String username) {
//        List<AuthUser> users = List.of(
//                new AuthUser("admin", passwordEncoder.encode("admin123"), RoleEnum.ADMIN.getAuthorities(), true, true, true, true),
//                new AuthUser("sonan",passwordEncoder.encode("sonan122"), RoleEnum.STUDENT.getAuthorities(),true,true,true,true)
//        );
//
//        return users.stream()
//                .filter(user -> user.getUsername().equals(username))
//                .findFirst();
//    }
//
//
//    @Override
//    public User createUser(User user) {
//        return null;
//    }
//
//    @Override
//    public User findById(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<User> findAll() {
//        return null;
//    }
//
//    @Override
//    public User updateUser(Long id, User updatedUser) {
//        return null;
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//
//    }
//
//    @Override
//    public User approveUser(Long userId) {
//        return null;
//    }
//
//    @Override
//    public User rejectUser(Long userId) {
//        return null;
//    }
//
//    @Override
//    public Page<User> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public User getUserByEmail(String email) {
//        return null;
//    }
//}
