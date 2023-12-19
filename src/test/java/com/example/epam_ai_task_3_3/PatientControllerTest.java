package com.example.epam_ai_task_3_3;

import com.example.epam_ai_task_3_3.controller.PatientController;
import com.example.epam_ai_task_3_3.repository.entity.Patient;
import com.example.epam_ai_task_3_3.service.GoogleMapsService;
import com.example.epam_ai_task_3_3.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.DirectionsResult;
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

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientService patientService;

    @MockBean
    private GoogleMapsService googleMapsService;

    @Test
    void getAllPatients() throws Exception {
        // Arrange
        List<Patient> patients = Arrays.asList(
                new Patient(), new Patient()
        );
        when(patientService.getAllPatients()).thenReturn(patients);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(patients)));

        // Verify
        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void getPatientById() throws Exception {
        // Arrange
        long patientId = 1L;
        Patient patient = new Patient();
        when(patientService.getPatientById(patientId)).thenReturn(patient);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{id}", patientId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(patient)));

        // Verify
        verify(patientService, times(1)).getPatientById(patientId);
    }

    @Test
    void createPatient() throws Exception {
        // Arrange
        Patient patientToCreate = new Patient();
        Patient createdPatient = new Patient();
        when(patientService.savePatient(any())).thenReturn(createdPatient);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .content(objectMapper.writeValueAsString(patientToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(createdPatient)));

        // Verify
        verify(patientService, times(1)).savePatient(any());
    }

    @Test
    void updatePatient() throws Exception {
        // Arrange
        long patientId = 1L;
        Patient patientToUpdate = new Patient();
        Patient updatedPatient = new Patient();
        when(patientService.savePatient(any())).thenReturn(updatedPatient);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/patients/{id}", patientId)
                        .content(objectMapper.writeValueAsString(patientToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedPatient)));

        // Verify
        verify(patientService, times(1)).savePatient(any());
    }

    @Test
    void deletePatient() throws Exception {
        // Arrange
        long patientId = 1L;

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/{id}", patientId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify
        verify(patientService, times(1)).deletePatient(eq(patientId));
    }

    @Test
    void prescribeMedicine() throws Exception {
        // Arrange
        long patientId = 1L;
        long medicationId = 2L;

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/patients/{patientId}/prescribe/{medicationId}", patientId, medicationId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify
        verify(patientService, times(1)).prescribeMedicine(eq(patientId), eq(medicationId));
    }

    @Test
    void calculateDistanceToNearestHospital() throws Exception {
        // Arrange
        String userLocation = "New York, NY";
        DirectionsResult directionsResult = new DirectionsResult();
        directionsResult.routes = new com.google.maps.model.DirectionsRoute[]{new com.google.maps.model.DirectionsRoute()};
        directionsResult.routes[0].legs = new com.google.maps.model.DirectionsLeg[]{new com.google.maps.model.DirectionsLeg()};
        directionsResult.routes[0].legs[0].distance = new com.google.maps.model.Distance();
        directionsResult.routes[0].legs[0].duration = new com.google.maps.model.Duration();
        directionsResult.routes[0].legs[0].distance.humanReadable = "5 miles";
        directionsResult.routes[0].legs[0].duration.humanReadable = "15 minutes";

        when(googleMapsService.getRouteToNearestHospital(eq(userLocation))).thenReturn(directionsResult);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/patients/calculateDistance")
                        .param("userLocation", userLocation))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Distance: 5 miles, Duration: 15 minutes"));

        // Verify
        verify(googleMapsService, times(1)).getRouteToNearestHospital(eq(userLocation));
    }
}

