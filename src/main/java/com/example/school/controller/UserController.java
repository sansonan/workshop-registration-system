package com.example.school.controller;

import com.example.school.dto.UserDTO;
import com.example.school.entity.User;
import com.example.school.service.UserService;
import com.example.school.service.util.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        user = userService.createUser(user);
        return ResponseEntity.ok("Registration submitted. Pending admin approval.");
    }

    @PutMapping("/{userId}/approve")
    public ResponseEntity<String> approveUser(@PathVariable Long userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok("User approved successfully.");
    }

    @PutMapping("/{userId}/reject")
    public ResponseEntity<String> rejectUser(@PathVariable Long userId) {
        userService.rejectUser(userId);
        return ResponseEntity.ok("User rejected successfully.");
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,  // Default to first page
            @RequestParam(defaultValue = "10") int size, // Default to 10 items per page
            @RequestParam(defaultValue = "id") String sortBy, // Default sort field
            @RequestParam(defaultValue = "asc") String sortDir // Default sort direction
    ) {
        // Create Pageable object
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Get paginated result
        Page<UserDTO> userPage = userService.findAll(pageable)
                .map(UserMapper.INSTANCE::toUserDto); // Convert entities to DTOs

        return ResponseEntity.ok(userPage);
    }



    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDto) {
        User user = UserMapper.INSTANCE.toUser(updatedUserDto);
        User userUpdate = userService.updateUser(id, user);
        return ResponseEntity.ok(UserMapper.INSTANCE.toUserDto(userUpdate));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
