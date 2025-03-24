package com.example.school.service.impl;

import com.example.school.entity.StudentRegisterWorkshop;
import com.example.school.repository.StudentRegisterWorkshopRepository;
import com.example.school.service.StudentRegisterWorkshopService;
import com.example.school.service.TelegramNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentRegisterWorkshopServiceImpl implements StudentRegisterWorkshopService {

    private final StudentRegisterWorkshopRepository repository;



    @Autowired
    public StudentRegisterWorkshopServiceImpl(StudentRegisterWorkshopRepository repository) {
        this.repository = repository;
    }

    @Override
    public StudentRegisterWorkshop registerStudentForWorkshop(StudentRegisterWorkshop registration) {
        return repository.save(registration);
    }

    @Override
    public StudentRegisterWorkshop updateStudentRegistration(Long id, StudentRegisterWorkshop registration) {
        StudentRegisterWorkshop existingRegistration = getRegistrationById(id);
        if (existingRegistration != null) {
            existingRegistration.setScheduleTime(registration.getScheduleTime());
            existingRegistration.setTelegramUsername(registration.getTelegramUsername());
            existingRegistration.setTelegramId(registration.getTelegramId());
            existingRegistration.setPhoneEmail(registration.getPhoneEmail());
            existingRegistration.setStudentId(registration.getStudentId());
            existingRegistration.setGender(registration.getGender());
            existingRegistration.setWorkshop(registration.getWorkshop());
            existingRegistration.setRegisterAt(registration.getRegisterAt());
            return repository.save(existingRegistration);
        }
        return null;
    }

    @Override
    public List<StudentRegisterWorkshop> getAllRegistrations() {
        return repository.findAll();
    }

    @Override
    public StudentRegisterWorkshop getRegistrationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteRegistration(Long id) {
        repository.deleteById(id);
    }
}

