package com.example.epam_ai_task_3_3.service;

import com.example.epam_ai_task_3_3.repository.DoctorAppointmentRepository;
import com.example.epam_ai_task_3_3.repository.PatientRepository;
import com.example.epam_ai_task_3_3.repository.entity.DoctorAppointment;
import com.example.epam_ai_task_3_3.repository.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorAppointmentService {

    private final DoctorAppointmentRepository doctorAppointmentRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public DoctorAppointmentService(
            DoctorAppointmentRepository doctorAppointmentRepository,
            PatientRepository patientRepository
    ) {
        this.doctorAppointmentRepository = doctorAppointmentRepository;
        this.patientRepository = patientRepository;
    }

    public List<DoctorAppointment> getAllDoctorAppointments() {
        return doctorAppointmentRepository.findAll();
    }

    public DoctorAppointment getDoctorAppointmentById(Long id) {
        return doctorAppointmentRepository.findById(id).orElse(null);
    }

    public DoctorAppointment createDoctorAppointment(String patientName, LocalDateTime dateTime,
                                                     String doctorName, String hospitalAddress) {
        Patient patient = patientRepository.findByName(patientName);
        if (patient != null) {
            DoctorAppointment appointment = new DoctorAppointment();
            appointment.setPatient(patient);
            appointment.setDateTime(dateTime);
            appointment.setDoctorName(doctorName);
            appointment.setHospitalAddress(hospitalAddress);
            return doctorAppointmentRepository.save(appointment);
        }
        return null;
    }

    public DoctorAppointment updateDoctorAppointment(Long id, LocalDateTime dateTime,
                                                     String doctorName, String hospitalAddress) {
        DoctorAppointment appointment = doctorAppointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setDateTime(dateTime);
            appointment.setDoctorName(doctorName);
            appointment.setHospitalAddress(hospitalAddress);
            return doctorAppointmentRepository.save(appointment);
        }
        return null;
    }

    public void deleteDoctorAppointment(Long id) {
        doctorAppointmentRepository.deleteById(id);
    }

    public List<DoctorAppointment> getDoctorAppointmentsByDate(LocalDateTime date) {
        return doctorAppointmentRepository.findByDateTimeBetween(
                date.withHour(0).withMinute(0).withSecond(0),
                date.withHour(23).withMinute(59).withSecond(59)
        );
    }
}

