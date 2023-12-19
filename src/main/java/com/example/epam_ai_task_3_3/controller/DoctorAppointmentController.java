package com.example.epam_ai_task_3_3.controller;

import com.example.epam_ai_task_3_3.repository.entity.DoctorAppointment;
import com.example.epam_ai_task_3_3.service.DoctorAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class DoctorAppointmentController {

    private final DoctorAppointmentService appointmentService;

    @Autowired
    public DoctorAppointmentController(DoctorAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<DoctorAppointment> getAllDoctorAppointments() {
        return appointmentService.getAllDoctorAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorAppointment> getDoctorAppointmentById(@PathVariable Long id) {
        DoctorAppointment appointment = appointmentService.getDoctorAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<DoctorAppointment> createDoctorAppointment(
            @RequestParam String patientName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam String doctorName,
            @RequestParam String hospitalAddress
    ) {
        DoctorAppointment createdAppointment = appointmentService.createDoctorAppointment(
                patientName, dateTime, doctorName, hospitalAddress
        );
        return ResponseEntity.ok(createdAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorAppointment> updateDoctorAppointment(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
            @RequestParam String doctorName,
            @RequestParam String hospitalAddress
    ) {
        DoctorAppointment updatedAppointment = appointmentService.updateDoctorAppointment(
                id, dateTime, doctorName, hospitalAddress
        );
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctorAppointment(@PathVariable Long id) {
        appointmentService.deleteDoctorAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/byDate")
    public List<DoctorAppointment> getDoctorAppointmentsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date
    ) {
        return appointmentService.getDoctorAppointmentsByDate(date);
    }
}


