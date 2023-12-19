package com.example.epam_ai_task_3_3.repository;

import com.example.epam_ai_task_3_3.repository.entity.DoctorAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, Long> {
    List<DoctorAppointment> findByDateTimeBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Object findByDateTime(LocalDateTime date);
}

