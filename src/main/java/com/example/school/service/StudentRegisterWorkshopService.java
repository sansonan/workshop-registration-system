package com.example.school.service;

import com.example.school.entity.StudentRegisterWorkshop;


import java.util.List;


public interface StudentRegisterWorkshopService {
    StudentRegisterWorkshop registerStudentForWorkshop(StudentRegisterWorkshop registration);
    StudentRegisterWorkshop updateStudentRegistration(Long id, StudentRegisterWorkshop registration);
    List<StudentRegisterWorkshop> getAllRegistrations();
    StudentRegisterWorkshop getRegistrationById(Long id);
    void deleteRegistration(Long id);
}
