package com.example.epam_ai_task_3_3.repository;

import com.example.epam_ai_task_3_3.repository.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByName(String patientName);
}

