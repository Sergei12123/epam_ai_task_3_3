package com.example.epam_ai_task_3_3;

import com.example.epam_ai_task_3_3.controller.DoctorAppointmentController;
import com.example.epam_ai_task_3_3.repository.entity.DoctorAppointment;
import com.example.epam_ai_task_3_3.service.DoctorAppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(DoctorAppointmentController.class)
class DoctorAppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorAppointmentService appointmentService;

    @Test
    void getAllDoctorAppointments() throws Exception {
        // Arrange
        List<DoctorAppointment> appointments = Arrays.asList(
                new DoctorAppointment(), new DoctorAppointment()
        );
        when(appointmentService.getAllDoctorAppointments()).thenReturn(appointments);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(appointments)));

        // Verify
        verify(appointmentService, times(1)).getAllDoctorAppointments();
    }

    @Test
    void getDoctorAppointmentById() throws Exception {
        // Arrange
        long appointmentId = 1L;
        DoctorAppointment appointment = new DoctorAppointment();
        when(appointmentService.getDoctorAppointmentById(appointmentId)).thenReturn(appointment);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/{id}", appointmentId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(appointment)));

        // Verify
        verify(appointmentService, times(1)).getDoctorAppointmentById(appointmentId);
    }

    @Test
    void createDoctorAppointment() throws Exception {
        // Arrange
        DoctorAppointment createdAppointment = new DoctorAppointment();
        when(appointmentService.createDoctorAppointment(anyString(), any(), anyString(), anyString()))
                .thenReturn(createdAppointment);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/appointments")
                        .param("patientName", "John Doe")
                        .param("dateTime", "2023-01-01T12:00:00")
                        .param("doctorName", "Dr. Smith")
                        .param("hospitalAddress", "Hospital A")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(createdAppointment)));

        // Verify
        verify(appointmentService, times(1)).createDoctorAppointment(anyString(), any(), anyString(), anyString());
    }

    // Add similar tests for updateDoctorAppointment, deleteDoctorAppointment, and getDoctorAppointmentsByDate

    @Test
    void updateDoctorAppointment() throws Exception {
        // Arrange
        long appointmentId = 1L;
        DoctorAppointment updatedAppointment = new DoctorAppointment();
        when(appointmentService.updateDoctorAppointment(eq(appointmentId), any(), anyString(), anyString()))
                .thenReturn(updatedAppointment);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/appointments/{id}", appointmentId)
                        .param("dateTime", "2023-01-01T15:00:00")
                        .param("doctorName", "Dr. Johnson")
                        .param("hospitalAddress", "Hospital B")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(updatedAppointment)));

        // Verify
        verify(appointmentService, times(1)).updateDoctorAppointment(eq(appointmentId), any(), anyString(), anyString());
    }

    @Test
    void deleteDoctorAppointment() throws Exception {
        // Arrange
        long appointmentId = 1L;

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/appointments/{id}", appointmentId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify
        verify(appointmentService, times(1)).deleteDoctorAppointment(eq(appointmentId));
    }

    @Test
    void getDoctorAppointmentsByDate() throws Exception {
        // Arrange
        LocalDateTime date = LocalDateTime.now();
        List<DoctorAppointment> appointments = Arrays.asList(
                new DoctorAppointment(), new DoctorAppointment()
        );
        when(appointmentService.getDoctorAppointmentsByDate(any(LocalDateTime.class))).thenReturn(appointments);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/appointments/byDate")
                        .param("date", "2023-01-01T00:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(appointments)));

        // Verify
        verify(appointmentService, times(1)).getDoctorAppointmentsByDate(any(LocalDateTime.class));
    }
}


