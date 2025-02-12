package pe.estebancoder.solutions.student.enrollment.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentRequestDTO;
import pe.estebancoder.solutions.student.enrollment.system.dto.EnrollmentResponseDTO;
import pe.estebancoder.solutions.student.enrollment.system.service.EnrollmentService;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
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
