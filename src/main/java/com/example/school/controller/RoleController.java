//package com.example.school.controller;
//
//import com.example.school.dto.RoleDTO;
//import com.example.school.dto.UserDTO;
//import com.example.school.entity.Role;
//import com.example.school.service.RoleService;
//import com.example.school.service.util.RoleMapper;
//import com.example.school.service.util.UserMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/roles")
//public class RoleController {
//    private final RoleService roleService;
//    public RoleController(RoleService roleService) {
//        this.roleService = roleService;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO) {
//        Role role = RoleMapper.INSTANCE.toRole(roleDTO);
//        role = roleService.createRole(role);
//        return  ResponseEntity.ok(RoleMapper.INSTANCE.toRoleDto(role));
//
//    }
//    @GetMapping
//    public ResponseEntity<List<RoleDTO>> getAllRoles() {
//        List<RoleDTO> roles = roleService.findAll()
//                .stream()
//                .map(role -> RoleMapper.INSTANCE.toRoleDto(role))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(roles);
//    }
//}
