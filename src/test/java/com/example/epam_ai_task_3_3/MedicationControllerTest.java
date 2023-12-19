package com.example.epam_ai_task_3_3;

import com.example.epam_ai_task_3_3.controller.MedicationController;
import com.example.epam_ai_task_3_3.repository.entity.Medication;
import com.example.epam_ai_task_3_3.service.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(MedicationController.class)
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MedicationService medicationService;

    @Test
    void getAllMedications() throws Exception {
        // Arrange
        List<Medication> medications = Arrays.asList(
                new Medication(), new Medication()
        );
        when(medicationService.getAllMedications()).thenReturn(medications);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/medications"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(medications)));

        // Verify
        verify(medicationService, times(1)).getAllMedications();
    }

    @Test
    void getMedicationById() throws Exception {
        // Arrange
        long medicationId = 1L;
        Medication medication = new Medication();
        when(medicationService.getMedicationById(medicationId)).thenReturn(medication);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/medications/{id}", medicationId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(medication)));

        // Verify
        verify(medicationService, times(1)).getMedicationById(medicationId);
    }

    @Test
    void createMedication() throws Exception {
        // Arrange
        Medication medicationToCreate = new Medication();
        medicationToCreate.setNameOfMedication("Aspirin");
        medicationToCreate.setSideEffects("Headache");
        medicationToCreate.setPrescriptionDisease("Pain");

        Medication createdMedication = new Medication();
        createdMedication.setId(1L);
        createdMedication.setNameOfMedication("Aspirin");
        createdMedication.setSideEffects("Headache");
        createdMedication.setPrescriptionDisease("Pain");

        when(medicationService.createMedication(eq("Aspirin"), eq("Headache"), eq("Pain")))
                .thenReturn(createdMedication);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/medications")
                        .content(objectMapper.writeValueAsString(medicationToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(createdMedication)));

        // Verify
        verify(medicationService, times(1)).createMedication(eq("Aspirin"), eq("Headache"), eq("Pain"));
    }

    @Test
    void updateMedication() throws Exception {
        // Arrange
        long medicationId = 1L;
        Medication medicationToUpdate = new Medication();
        medicationToUpdate.setNameOfMedication("Updated Aspirin");
        medicationToUpdate.setSideEffects("Updated Headache");
        medicationToUpdate.setPrescriptionDisease("Updated Pain");

        Medication updatedMedication = new Medication();
        updatedMedication.setId(1L);
        updatedMedication.setNameOfMedication("Updated Aspirin");
        updatedMedication.setSideEffects("Updated Headache");
        updatedMedication.setPrescriptionDisease("Updated Pain");

        when(medicationService.updateMedication(eq(medicationId), eq("Updated Aspirin"), eq("Updated Headache"), eq("Updated Pain")))
                .thenReturn(updatedMedication);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/medications/{id}", medicationId)
                        .content(objectMapper.writeValueAsString(medicationToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedMedication)));

        // Verify
        verify(medicationService, times(1)).updateMedication(eq(medicationId), eq("Updated Aspirin"), eq("Updated Headache"), eq("Updated Pain"));
    }

    @Test
    void deleteMedication() throws Exception {
        // Arrange
        long medicationId = 1L;

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/medications/{id}", medicationId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify
        verify(medicationService, times(1)).deleteMedication(eq(medicationId));
    }
}

