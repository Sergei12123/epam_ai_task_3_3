package com.example.epam_ai_task_3_3.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Patient extends BaseEntity {

    private String name;
    private String phone;
    private String disease;
    private boolean sick;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication prescribedMedication;

    public Patient(long patientId, String johnDoe, String number, String fever, boolean b) {
        this(johnDoe, number, fever, b, null);
        super.setId(patientId);
    }


}