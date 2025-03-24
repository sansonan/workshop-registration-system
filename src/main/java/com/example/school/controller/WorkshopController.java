package com.example.school.controller;



import com.example.school.dto.WorkshopDTO;
import com.example.school.entity.Workshop;
import com.example.school.service.WorkshopService;
import com.example.school.service.util.WorkshopMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @PostMapping("/create")
    public ResponseEntity<WorkshopDTO> createWorkshop(@RequestBody WorkshopDTO workshopDTO) {
        Workshop workshop = WorkshopMapper.INSTANCE.toWorkshop(workshopDTO);
        workshop = workshopService.createWorkshop(workshop);
        return ResponseEntity.ok(WorkshopMapper.INSTANCE.toWorkshopDto(workshop));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkshopDTO> updateWorkshop(@PathVariable Long id, @RequestBody WorkshopDTO updatedWorkshop) {
        Workshop workshop = WorkshopMapper.INSTANCE.toWorkshop(updatedWorkshop);
        Workshop update = workshopService.updateWorkshop(id, workshop);
        return ResponseEntity.ok(WorkshopMapper.INSTANCE.toWorkshopDto(update));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkshopDTO> getWorkshopById(@PathVariable Long id) {
        Workshop workshop = workshopService.getWorkshopById(id);
        return ResponseEntity.ok(WorkshopMapper.INSTANCE.toWorkshopDto(workshop));
    }


    @GetMapping
    public ResponseEntity<List<WorkshopDTO>> getAllWorkshops() {
        List<WorkshopDTO> workshops = workshopService.getAllWorkshops()
                .stream()
                .map(workshop -> WorkshopMapper.INSTANCE.toWorkshopDto(workshop))
                .collect(Collectors.toList());
        return ResponseEntity.ok(workshops);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long id) {
        workshopService.deleteWorkshop(id);
        return ResponseEntity.noContent().build();
    }
}
