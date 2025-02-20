package pe.estebancoder.solutions.student.enrollment.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.estebancoder.solutions.student.enrollment.system.repository.projection.EnrollmentProjection;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentProjection>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAll());
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollmentResponseDTO> enrollStudent(@RequestBody EnrollmentRequestDTO request) {
        try {
            EnrollmentResponseDTO response = enrollmentService.enrollStudent(request);
            return ResponseEntity.ok(response);
        }
        catch (Exception ex){
            EnrollmentResponseDTO response = new EnrollmentResponseDTO();
            response.setStudentId(request.getStudentId());
            response.setEnrolledSections(new ArrayList<>());
            response.setMessage(ex.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
