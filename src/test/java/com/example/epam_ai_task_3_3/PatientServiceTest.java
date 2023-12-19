package com.example.epam_ai_task_3_3;

import com.example.epam_ai_task_3_3.repository.MedicationRepository;
import com.example.epam_ai_task_3_3.repository.PatientRepository;
import com.example.epam_ai_task_3_3.repository.entity.Medication;
import com.example.epam_ai_task_3_3.repository.entity.Patient;
import com.example.epam_ai_task_3_3.service.MedicationService;
import com.example.epam_ai_task_3_3.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private MedicationService medicationService; // Assuming you have a MedicationService dependency

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPatients() {
        // Arrange
        Patient patient1 = new Patient(1L, "John Doe", "123456789", "Fever", true);
        Patient patient2 = new Patient(2L, "Jane Smith", "987654321", "Headache", false);
        List<Patient> patients = Arrays.asList(patient1, patient2);

        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllPatients();

        // Assert
        assertEquals(2, result.size());
        assertEquals(patient1, result.get(0));
        assertEquals(patient2, result.get(1));
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getPatientById() {
        // Arrange
        long patientId = 1L;
        Patient patient = new Patient(patientId, "John Doe", "123456789", "Fever", true);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act
        Patient result = patientService.getPatientById(patientId);

        // Assert
        assertNotNull(result);
        assertEquals(patient, result);
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    void savePatient() {
        // Arrange
        Patient patient = new Patient(1L, "John Doe", "123456789", "Fever", true);

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act
        Patient result = patientService.savePatient(patient);

        // Assert
        assertNotNull(result);
        assertEquals(patient, result);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void deletePatient() {
        // Arrange
        long patientId = 1L;

        // Act
        patientService.deletePatient(patientId);

        // Assert
        verify(patientRepository, times(1)).deleteById(patientId);
    }

    @Test
    void prescribeMedicine() {
        // Arrange
        long patientId = 1L;
        long medicationId = 2L;

        Patient patient = new Patient(patientId, "John Doe", "123456789", "Fever", true);
        Medication medication = new Medication(medicationId, "Aspirin", "None", "Headache");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medication));

        // Act
        patientService.prescribeMedicine(patientId, medicationId);

        // Assert
        assertEquals(patient.getPrescribedMedication(), medication);
        verify(patientRepository, times(1)).save(patient);
    }
}

