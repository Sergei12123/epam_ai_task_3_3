package com.example.epam_ai_task_3_3;

import com.example.epam_ai_task_3_3.repository.MedicationRepository;
import com.example.epam_ai_task_3_3.repository.entity.Medication;
import com.example.epam_ai_task_3_3.service.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMedications() {
        // Arrange
        Medication medication1 = new Medication(1L, "Med1", "SideEffect1", "Disease1");
        Medication medication2 = new Medication(2L, "Med2", "SideEffect2", "Disease2");
        List<Medication> medications = Arrays.asList(medication1, medication2);

        when(medicationRepository.findAll()).thenReturn(medications);

        // Act
        List<Medication> result = medicationService.getAllMedications();

        // Assert
        assertEquals(2, result.size());
        assertEquals(medication1, result.get(0));
        assertEquals(medication2, result.get(1));
        verify(medicationRepository, times(1)).findAll();
    }

    @Test
    void getMedicationById() {
        // Arrange
        long medicationId = 1L;
        Medication medication = new Medication(medicationId, "Med1", "SideEffect1", "Disease1");

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(medication));

        // Act
        Medication result = medicationService.getMedicationById(medicationId);

        // Assert
        assertNotNull(result);
        assertEquals(medication, result);
        verify(medicationRepository, times(1)).findById(medicationId);
    }

    @Test
    void createMedication() {
        // Arrange
        Medication medication = new Medication(1L, "Med1", "SideEffect1", "Disease1");

        when(medicationRepository.save(any(Medication.class))).thenReturn(medication);

        // Act
        Medication result = medicationService.createMedication("Med1", "SideEffect1", "Disease1");

        // Assert
        assertNotNull(result);
        assertEquals(medication, result);
        verify(medicationRepository, times(1)).save(any(Medication.class));
    }

    @Test
    void updateMedication() {
        // Arrange
        long medicationId = 1L;
        Medication existingMedication = new Medication(medicationId, "Med1", "SideEffect1", "Disease1");
        Medication updatedMedication = new Medication(medicationId, "MedUpdated", "SideEffectUpdated", "DiseaseUpdated");

        when(medicationRepository.findById(medicationId)).thenReturn(Optional.of(existingMedication));
        when(medicationRepository.save(any(Medication.class))).thenReturn(updatedMedication);

        // Act
        Medication result = medicationService.updateMedication(medicationId, "MedUpdated", "SideEffectUpdated", "DiseaseUpdated");

        // Assert
        assertNotNull(result);
        assertEquals(updatedMedication, result);
        verify(medicationRepository, times(1)).findById(medicationId);
        verify(medicationRepository, times(1)).save(any(Medication.class));
    }

    @Test
    void deleteMedication() {
        // Arrange
        long medicationId = 1L;

        // Act
        medicationService.deleteMedication(medicationId);

        // Assert
        verify(medicationRepository, times(1)).deleteById(medicationId);
    }
}
