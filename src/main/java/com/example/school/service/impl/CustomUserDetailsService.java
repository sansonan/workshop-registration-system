//package com.example.school.service.impl;
//
//import com.example.school.entity.Role;
//import com.example.school.entity.User;
//import com.example.school.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userService.getUserByEmail(username);
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword()) // Ensure passwords are encoded
//                .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
//                .build();
//    }
//}