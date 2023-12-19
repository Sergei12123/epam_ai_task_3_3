package com.example.epam_ai_task_3_3.controller;

import com.example.epam_ai_task_3_3.repository.entity.Patient;
import com.example.epam_ai_task_3_3.service.GoogleMapsService;
import com.example.epam_ai_task_3_3.service.PatientService;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    private GoogleMapsService googleMapsService;

    @Autowired
    public PatientController(PatientService patientService, GoogleMapsService googleMapsService) {
        this.patientService = patientService;
        this.googleMapsService = googleMapsService;
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Patient updatedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{patientId}/prescribe/{medicationId}")
    public ResponseEntity<Void> prescribeMedicine(
            @PathVariable Long patientId, @PathVariable Long medicationId) {
        patientService.prescribeMedicine(patientId, medicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calculateDistance")
    public ResponseEntity<String> calculateDistanceToNearestHospital(
            @RequestParam("userLocation") String userLocation) {
        if (userLocation == null || userLocation.isEmpty()) {
            return ResponseEntity.badRequest().body("User location is required.");
        }

        // Use Google Maps service to calculate distance to the nearest hospital
        DirectionsResult directionsResult = googleMapsService.getRouteToNearestHospital(userLocation);

        if (directionsResult != null && directionsResult.routes.length > 0) {
            // Extract distance and duration information from the directions result
            DirectionsRoute route = directionsResult.routes[0];
            DirectionsLeg leg = route.legs[0];

            String distance = leg.distance.humanReadable;
            String duration = leg.duration.humanReadable;

            return ResponseEntity.ok("Distance: " + distance + ", Duration: " + duration);
        } else {
            return ResponseEntity.ok("Unable to calculate distance to the nearest hospital.");
        }
    }
}

