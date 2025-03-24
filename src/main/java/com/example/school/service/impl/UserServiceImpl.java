package com.example.school.service.impl;

import com.example.school.constants.UserStatus;
import com.example.school.entity.Role;
import com.example.school.entity.User;
import com.example.school.exception.ApiException;
import com.example.school.exception.ResourceNotFoundException;
import com.example.school.repository.RoleRepository;
import com.example.school.repository.UserRepository;
import com.example.school.security.AuthUser;
import com.example.school.security.RoleEnum;
import com.example.school.service.RoleService;
import com.example.school.service.TelegramNotificationService;
import com.example.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TelegramNotificationService tele;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Autowired
    private NotificationServiceImpl notificationService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TelegramNotificationService tele, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tele = tele;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public Optional<AuthUser> findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "User not found"));
        AuthUser authUser = AuthUser.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthorities(user.getRoles()))
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .build();
        return Optional.ofNullable(authUser);
    }
    @Override
    public User createUser(User user) {

        // Assign the default role (e.g., USER) after approval
        roleService.assignDefaultRole(user);
        // Set default status to PENDING_APPROVAL
        user.setStatus(UserStatus.PENDING_APPROVAL);
        user.setEnabled(false); // Ensure the account is disabled until approved
        try{
            passwordEncoder.encode(user.getPassword());
            passwordEncoder.encode(user.getConfirmPassword());
            // Save user to database
            User savedUser = userRepository.save(user);
            // Notify admin about the new user (optional)
            notifyAdmin(savedUser);
            // Notify the user about their pending approval
            notifyUser(savedUser);

            return savedUser;
        }catch (Exception e) {

            throw new ResourceNotFoundException("User registration failed");
        }
    }
    private void notifyAdmin(User user) {

        String message = "New user registration pending approval: " + user.getEmail();
        tele.sendNotification("User : "+ user.getStudentId()+ " : request create new account");
    }

    private void notifyUser(User user) {
        String subject = "Registration Submitted - Pending Approval";
        String message = "Dear " + user.getFirstName() + ",\n\n" +
                "Thank you for registering with us! Your account is currently pending approval. " +
                "We will notify you once it is reviewed and approved.\n\n" +
                "If you have any questions, feel free to contact us.\n\n" +
                "Best regards,\n" +
                "The Support Team";

        // Send email to the user
        notificationService.sendEmail(user.getEmail(), subject, message);
    }

    private void notifyUserAfterApproval(User user) {
        String subject = "Account Approved - Welcome!";
        String message = "Dear " + user.getFirstName() + ",\n\n" +
                "We are pleased to inform you that your account registration has been approved. " +
                "You can now log in to your account using the credentials you provided during registration.\n\n" +
                "If you have any questions or need further assistance, feel free to contact us.\n\n" +
                "Welcome to the platform!\n\n" +
                "Best regards,\n" +
                "The Support Team";

        // Send email to the user
        notificationService.sendEmail(user.getEmail(), subject, message);
    }

    private void notifyUserAfterRejection(User user) {
        String subject = "Account Registration Rejected";
        String message = "Dear " + user.getFirstName() + ",\n\n" +
                "We regret to inform you that your account registration has been rejected. " +
                "This decision was based on our review of the provided information.\n\n" +
                "If you believe this was a mistake or need further clarification, please contact us at support@example.com.\n\n" +
                "Thank you for your understanding.\n\n" +
                "Best regards,\n" +
                "The Support Team";

        // Send email to the user
        notificationService.sendEmail(user.getEmail(), subject, message);
    }



    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = findById(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setStudentId(updatedUser.getStudentId());
        user.setDateOfBirth(updatedUser.getDateOfBirth());
        user.setOccupation(updatedUser.getOccupation());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(user.getPassword());
        user.setConfirmPassword(updatedUser.getConfirmPassword());
        user.setAddress(updatedUser.getAddress());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (user.getStatus() == UserStatus.PENDING_APPROVAL) {
            user.setStatus(UserStatus.APPROVED);
            user.setEnabled(true); // Enable account after approval
            notifyUserAfterApproval(user);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User is not in a pending state.");
        }
    }

    @Override
    public User rejectUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        if (user.getStatus() == UserStatus.PENDING_APPROVAL) {
            user.setStatus(UserStatus.REJECTED);
            user.setEnabled(false);
            notifyUserAfterRejection(user);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User is not in a pending state.");
        }
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    private Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles){
        Set<SimpleGrantedAuthority> authorities1 = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .flatMap(role -> toStream(role))
                .collect(Collectors.toSet());
        authorities.addAll(authorities1);

        return authorities;
    }

    private Stream<SimpleGrantedAuthority> toStream(Role role){
        return role.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()));
    }

}
