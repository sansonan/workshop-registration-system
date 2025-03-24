package com.example.school.service;

import com.example.school.entity.Role;
import com.example.school.entity.User;

import java.util.List;

public interface RoleService {
    Role createRole(Role role);
    Role findByName(String name);
    List<Role> findAll();
    void assignDefaultRole(User user);
    void assignRoleToUser(User user, String roleName);
    void removeRoleFromUser(User user, String roleName);
}
