package com.example.epam_ai_task_3_3.service;

import com.example.epam_ai_task_3_3.repository.MedicationRepository;
import com.example.epam_ai_task_3_3.repository.entity.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }

    public Medication createMedication(String nameOfMedication, String sideEffects, String prescriptionDisease) {
        Medication medication = new Medication();
        medication.setNameOfMedication(nameOfMedication);
        medication.setSideEffects(sideEffects);
        medication.setPrescriptionDisease(prescriptionDisease);
        return medicationRepository.save(medication);
    }

    public Medication updateMedication(Long id, String nameOfMedication, String sideEffects, String prescriptionDisease) {
        Medication medication = medicationRepository.findById(id).orElse(null);
        if (medication != null) {
            medication.setNameOfMedication(nameOfMedication);
            medication.setSideEffects(sideEffects);
            medication.setPrescriptionDisease(prescriptionDisease);
            return medicationRepository.save(medication);
        }
        return null;
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}

