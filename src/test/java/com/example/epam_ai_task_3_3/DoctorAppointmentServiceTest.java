package com.example.epam_ai_task_3_3;

import com.example.epam_ai_task_3_3.repository.DoctorAppointmentRepository;
import com.example.epam_ai_task_3_3.repository.PatientRepository;
import com.example.epam_ai_task_3_3.repository.entity.DoctorAppointment;
import com.example.epam_ai_task_3_3.repository.entity.Patient;
import com.example.epam_ai_task_3_3.service.DoctorAppointmentService;
import com.example.epam_ai_task_3_3.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DoctorAppointmentServiceTest {

    @Mock
    private DoctorAppointmentRepository doctorAppointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientService patientService; // Assuming you have a PatientService dependency

    @InjectMocks
    private DoctorAppointmentService doctorAppointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDoctorAppointments() {
        // Arrange
        DoctorAppointment appointment1 = new DoctorAppointment(new Patient(), LocalDateTime.now(), "Dr. Smith", "Hospital A");
        DoctorAppointment appointment2 = new DoctorAppointment(new Patient(), LocalDateTime.now(), "Dr. Johnson", "Hospital B");
        List<DoctorAppointment> appointments = Arrays.asList(appointment1, appointment2);

        when(doctorAppointmentRepository.findAll()).thenReturn(appointments);

        // Act
        List<DoctorAppointment> result = doctorAppointmentService.getAllDoctorAppointments();

        // Assert
        assertEquals(2, result.size());
        assertEquals(appointment1, result.get(0));
        assertEquals(appointment2, result.get(1));
        verify(doctorAppointmentRepository, times(1)).findAll();
    }

    @Test
    void getDoctorAppointmentById() {
        // Arrange
        long appointmentId = 1L;
        DoctorAppointment appointment = new DoctorAppointment(new Patient(), LocalDateTime.now(), "Dr. Smith", "Hospital A");

        when(doctorAppointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        // Act
        DoctorAppointment result = doctorAppointmentService.getDoctorAppointmentById(appointmentId);

        // Assert
        assertNotNull(result);
        assertEquals(appointment, result);
        verify(doctorAppointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void createDoctorAppointment() {
        // Arrange
        String patientName = "John Doe";
        LocalDateTime dateTime = LocalDateTime.now();
        String doctorName = "Dr. Smith";
        String hospitalAddress = "Hospital A";

        Patient patient = new Patient();
        DoctorAppointment appointment = new DoctorAppointment(patient, dateTime, doctorName, hospitalAddress);

        when(patientRepository.findByName(patientName)).thenReturn(patient);
        when(doctorAppointmentRepository.save(any(DoctorAppointment.class))).thenReturn(appointment);

        // Act
        DoctorAppointment result = doctorAppointmentService.createDoctorAppointment(patientName, dateTime, doctorName, hospitalAddress);

        // Assert
        assertNotNull(result);
        assertEquals(appointment, result);
        verify(patientRepository, times(1)).findByName(patientName);
        verify(doctorAppointmentRepository, times(1)).save(any(DoctorAppointment.class));
    }

    @Test
    void updateDoctorAppointment() {
        // Arrange
        long appointmentId = 1L;
        DoctorAppointment existingAppointment = new DoctorAppointment(new Patient(), LocalDateTime.now(), "Dr. Smith", "Hospital A");
        DoctorAppointment updatedAppointment = new DoctorAppointment(new Patient(), LocalDateTime.now(), "Dr. Johnson", "Hospital B");

        when(doctorAppointmentRepository.findById(appointmentId)).thenReturn(Optional.of(existingAppointment));
        when(doctorAppointmentRepository.save(any(DoctorAppointment.class))).thenReturn(updatedAppointment);

        // Act
        DoctorAppointment result = doctorAppointmentService.updateDoctorAppointment(appointmentId, LocalDateTime.now(), "Dr. Johnson", "Hospital B");

        // Assert
        assertNotNull(result);
        assertEquals(updatedAppointment, result);
        verify(doctorAppointmentRepository, times(1)).findById(appointmentId);
        verify(doctorAppointmentRepository, times(1)).save(any(DoctorAppointment.class));
    }

    @Test
    void deleteDoctorAppointment() {
        // Arrange
        long appointmentId = 1L;

        // Act
        doctorAppointmentService.deleteDoctorAppointment(appointmentId);

        // Assert
        verify(doctorAppointmentRepository, times(1)).deleteById(appointmentId);
    }

    @Test
    void getDoctorAppointmentsByDate() {
        // Arrange
        LocalDateTime date = LocalDateTime.now();
        DoctorAppointment appointment1 = new DoctorAppointment(null, date.withHour(12), "Dr. Smith", "Hospital A");
        DoctorAppointment appointment2 = new DoctorAppointment(null, date.withHour(15), "Dr. Johnson", "Hospital B");
        List<DoctorAppointment> appointments = Arrays.asList(appointment1, appointment2);

        when(doctorAppointmentRepository.findByDateTimeBetween(
                date.withHour(0).withMinute(0).withSecond(0),
                date.withHour(23).withMinute(59).withSecond(59)
        )).thenReturn(appointments);

        // Act
        List<DoctorAppointment> result = doctorAppointmentService.getDoctorAppointmentsByDate(date);

        // Assert
        assertEquals(2, result.size());
        assertEquals(appointment1, result.get(0));
        assertEquals(appointment2, result.get(1));
        verify(doctorAppointmentRepository, times(1)).findByDateTimeBetween(
                date.withHour(0).withMinute(0).withSecond(0),
                date.withHour(23).withMinute(59).withSecond(59)
        );
    }
}


