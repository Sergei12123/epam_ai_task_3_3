package com.example.epam_ai_task_3_3.repository;

import com.example.epam_ai_task_3_3.repository.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}

