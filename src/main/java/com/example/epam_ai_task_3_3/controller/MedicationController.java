package com.example.epam_ai_task_3_3.controller;

import com.example.epam_ai_task_3_3.repository.entity.Medication;
import com.example.epam_ai_task_3_3.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    public List<Medication> getAllMedications() {
        return medicationService.getAllMedications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable Long id) {
        Medication medication = medicationService.getMedicationById(id);
        return ResponseEntity.ok(medication);
    }

    @PostMapping
    public ResponseEntity<Medication> createMedication(@RequestBody Medication medication) {
        Medication createdMedication = medicationService.createMedication(
                medication.getNameOfMedication(),
                medication.getSideEffects(),
                medication.getPrescriptionDisease()
        );
        return ResponseEntity.ok(createdMedication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable Long id, @RequestBody Medication medication) {
        Medication updatedMedication = medicationService.updateMedication(
                id,
                medication.getNameOfMedication(),
                medication.getSideEffects(),
                medication.getPrescriptionDisease()
        );
        return ResponseEntity.ok(updatedMedication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }
}

