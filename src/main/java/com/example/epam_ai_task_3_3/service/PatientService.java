package com.example.epam_ai_task_3_3.service;

import com.example.epam_ai_task_3_3.repository.MedicationRepository;
import com.example.epam_ai_task_3_3.repository.PatientRepository;
import com.example.epam_ai_task_3_3.repository.entity.Medication;
import com.example.epam_ai_task_3_3.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public PatientService(
            PatientRepository patientRepository,
            MedicationRepository medicationRepository) {
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
    }

    // CRUD operations

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    // Method for prescribing medicine

    public void prescribeMedicine(Long patientId, Long medicationId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        Medication medication = medicationRepository.findById(medicationId).orElse(null);

        if (patient != null && medication != null) {
            patient.setPrescribedMedication(medication);
            patientRepository.save(patient);
        }
    }
}

