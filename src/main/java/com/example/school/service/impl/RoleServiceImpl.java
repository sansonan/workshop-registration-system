package com.example.school.service.impl;

import com.example.school.entity.Role;
import com.example.school.entity.User;
import com.example.school.repository.RoleRepository;
import com.example.school.repository.UserRepository;
import com.example.school.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    // Assign a role to a user
    @Transactional
    @Override
    public void assignRoleToUser(User user, String roleName) {
        // Find the role by name
        Optional<Role> roleOptional = roleRepository.findByName(roleName);

        // Check if the role exists
        Role role = roleOptional.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        // Add the role to the user's roles
        user.getRoles().add(role);

        // Save the user with the new role
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void removeRoleFromUser(User user, String roleName) {
        Optional<Role> roleOptional = roleRepository.findByName(roleName);

        // Throw an exception if role is not found
        Role role = roleOptional.orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        // Remove the role from the user's roles
        user.getRoles().remove(role);

        // Save the user with the updated roles
        userRepository.save(user);
    }



    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    // Example method to assign default role like "USER"
    @Override
    @Transactional
    public void assignDefaultRole(User user) {
        Optional<Role> roleOptional = roleRepository.findByName("STUDENT");

        // Check if default role exists
        Role role = roleOptional.orElseThrow(() -> new RuntimeException("Default role USER not found"));

        // Add default role
        user.getRoles().add(role);

        // Save the user
        userRepository.save(user);
    }


}
