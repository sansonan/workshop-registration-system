package com.example.school.controller;


import com.example.school.entity.StudentRegisterWorkshop;
import com.example.school.dto.StudentRegisterWorkshopDTO;
import com.example.school.service.StudentRegisterWorkshopService;
import com.example.school.service.util.StudentRegisterWorkshopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrations")
public class StudentRegisterWorkshopController {

    private final StudentRegisterWorkshopService service;
    private final StudentRegisterWorkshopMapper studentRegisterWorkshopMapper;

    @Autowired
    public StudentRegisterWorkshopController(StudentRegisterWorkshopService service, StudentRegisterWorkshopMapper studentRegisterWorkshopMapper) {
        this.service = service;
        this.studentRegisterWorkshopMapper = studentRegisterWorkshopMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<StudentRegisterWorkshopDTO> registerStudentForWorkshop(@RequestBody StudentRegisterWorkshopDTO registration) {
       StudentRegisterWorkshop workshop = studentRegisterWorkshopMapper.toEntity(registration);
        StudentRegisterWorkshop savedWorkshop = service.registerStudentForWorkshop(workshop);
        return  ResponseEntity.ok(studentRegisterWorkshopMapper.toDto(savedWorkshop));

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<StudentRegisterWorkshopDTO> updateStudentRegistration(@PathVariable Long id, @RequestBody StudentRegisterWorkshopDTO registration) {
       StudentRegisterWorkshop student = studentRegisterWorkshopMapper.toEntity(registration);
        StudentRegisterWorkshop update = service.updateStudentRegistration(id, student);
        return ResponseEntity.ok(studentRegisterWorkshopMapper.toDto(update));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentRegisterWorkshopDTO>> getAllRegistrations() {
        List<StudentRegisterWorkshopDTO> students = service.getAllRegistrations()
                .stream()
                .map(student -> studentRegisterWorkshopMapper.toDto(student))
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);

    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentRegisterWorkshopDTO> getRegistrationById(@PathVariable Long id) {
        StudentRegisterWorkshop registrationById = service.getRegistrationById(id);
        return ResponseEntity.ok(studentRegisterWorkshopMapper.toDto(registrationById));
    }


    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long id) {
        service.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }
}

