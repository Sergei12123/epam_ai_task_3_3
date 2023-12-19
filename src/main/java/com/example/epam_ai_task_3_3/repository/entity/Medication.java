package com.example.epam_ai_task_3_3.repository.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medication extends BaseEntity {

    private String nameOfMedication;
    private String sideEffects;
    private String prescriptionDisease;


    public Medication(long medicationId, String med1, String sideEffect1, String disease1) {
        this(med1, sideEffect1, disease1);
        super.setId(medicationId);
    }
}
